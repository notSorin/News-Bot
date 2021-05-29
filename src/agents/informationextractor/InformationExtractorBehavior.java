package agents.informationextractor;

import java.util.HashMap;
import agents.AgentsEnums.MessageKey;
import agents.AgentsEnums.MessageValue;
import gate.AnnotationSet;
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
	private static final String EXTRACTION_FAILURE = "I could not extract any information from the article.";
	private static final String EXTRACTION_SUCCESS = "Information extraction completed.\nWhat information would you like to know about it?";
	
	private final GATEHandle GATE_HANDLE;
	
	public InformationExtractorBehavior()
	{
		GATE_HANDLE = new GATEHandle();
	}

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
						
						agent.respondToRequest(MessageValue.RESPONSE_EXTRACT_FROM_ARTICLE, extractionResult);
						break;
					}
					default:
						//TODO
						break;
					}
				}
			}
			catch(Exception e)
			{
				agent.respondToRequest(MessageValue.RESPONSE_EXTRACT_FROM_ARTICLE, EXTRACT_EXCEPTION);
			}
		}
	}

	/**
	 * Obtains relevant information from a news article and stores it in memory.
	 * @param article A news article's text.
	 * @return A string with the result of performing the extraction: Either indicating success, or failure.
	 */
	private String performExtraction(String article)
	{
		String ret = EXTRACTION_FAILURE;
		AnnotationSet annotations = GATE_HANDLE.getAnnotations(article);
		
		if(annotations != null)
		{
			ret = EXTRACTION_SUCCESS;
		}
		
		return ret;
	}
}