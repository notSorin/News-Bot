package agents.conversation;

import java.util.HashMap;
import agents.AgentsEnums.MessageKey;
import agents.AgentsEnums.MessageValue;
import agents.clustering.ClusteringAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

class ConversationAgentBehavior extends CyclicBehaviour
{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public void action()
	{
		final ACLMessage message = getAgent().receive();
		final ConversationAgent agent = ((ConversationAgent)getAgent());
		
		if(message != null)
		{
			try
			{
				HashMap<MessageKey, Object> messageMap = (HashMap<MessageKey, Object>)message.getContentObject();
				MessageValue intent = (MessageValue)messageMap.get(MessageKey.INTENT);
				
				if(intent != null)
				{
					switch(intent)
					{
					case RESPONSE_CLUSTER_ARTICLE:
					{
						final String data = (String)messageMap.get(MessageKey.INTENT_DATA);
						final String chatId = agent.getLastUpdate().getMessage().getChatId().toString();
						
						agent.getTelegramChatbot().sendMessage(data, chatId);
						break;
					}
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}