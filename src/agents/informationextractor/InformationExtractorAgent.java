package agents.informationextractor;

import java.io.IOException;
import java.util.HashMap;
import agents.AgentsEnums.MessageKey;
import agents.AgentsEnums.MessageValue;
import agents.conversation.ConversationAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

/**
 * Agent used for retrieving relevant information from a news article.
 * */
public class InformationExtractorAgent extends Agent
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void setup()
	{
		addBehaviour(new InformationExtractorBehavior());
	}

	/**
	 * Sends a response message to the conversation agent, containing the result from extracting information from an article.
	 * @param extractionResult The result of performing the information extraction.
	 */
	public void respondToExtractRequest(String extractionResult)
	{
		HashMap<MessageKey, Object> aclContent = new HashMap<MessageKey, Object>();

		aclContent.put(MessageKey.INTENT, MessageValue.RESPONSE_EXTRACT_FROM_ARTICLE);
		aclContent.put(MessageKey.INTENT_DATA, extractionResult);

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