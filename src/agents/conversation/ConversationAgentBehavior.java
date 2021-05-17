package agents.conversation;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

class ConversationAgentBehavior extends CyclicBehaviour
{
	private static final long serialVersionUID = 1L;

	@Override
	public void action()
	{
		ACLMessage message = getAgent().receive();
		
		if(message != null)
		{
			//TODO
			//We probably need to cast getAgent() to a ConversationAgent and call sendMessage on the Telegram bot.
		}
	}
}