package agents;

import java.io.IOException;
import java.util.HashMap;
import agents.AgentsEnums.MessageKey;
import agents.AgentsEnums.MessageValue;
import agents.conversation.ConversationAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public abstract class NewsBotAgent extends Agent
{
	private static final long serialVersionUID = 1L;

	public void respondToRequest(MessageValue mv, Object response)
	{
		HashMap<MessageKey, Object> aclContent = new HashMap<MessageKey, Object>();

		aclContent.put(MessageKey.INTENT, mv);
		aclContent.put(MessageKey.INTENT_DATA, response);

		ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);

		aclMessage.addReceiver(new AID(ConversationAgent.class.getSimpleName(), AID.ISLOCALNAME));
		
		try
		{
			aclMessage.setContentObject(aclContent);
			send(aclMessage);
		}
		catch(IOException e)
		{
			//If the message cannot be sent to the conversation agent, there's not much that can
			//be done at this point; just print the trace.
			e.printStackTrace();
		}
	}
}