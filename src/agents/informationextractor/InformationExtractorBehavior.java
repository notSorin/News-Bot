package agents.informationextractor;

import java.util.HashMap;
import agents.AgentsEnums.MessageKey;
import agents.AgentsEnums.MessageValue;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * The behavior class for the Information Extraction Agent. It receives ACL messages and reacts accordingly.
 * @author Sorin
 *
 */
class InformationExtractorBehavior extends CyclicBehaviour
{
	private static final long serialVersionUID = 1L;
	private static final String EXTRACT_EXCEPTION = "It seems that I was not able to perform this action.";

	@SuppressWarnings("unchecked")
	@Override
	public void action()
	{
		final ACLMessage message = getAgent().receive();
		final InformationExtractorAgent agent = ((InformationExtractorAgent)getAgent());
		
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
					case EXTRACT_FROM_ARTICLE:
					{
						String article = (String)messageMap.get(MessageKey.INTENT_DATA);
						String extractionResult = performExtraction(article);
						
						agent.respondToExtractRequest(extractionResult);
						break;
					}
					default:
						break;
					}
				}
			}
			catch(Exception e)
			{
				agent.respondToExtractRequest(EXTRACT_EXCEPTION);
			}
		}
	}

	private String performExtraction(String article)
	{
		//TODO
		return null;
	}
}