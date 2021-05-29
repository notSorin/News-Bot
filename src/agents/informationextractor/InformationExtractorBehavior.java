package agents.informationextractor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import agents.AgentsEnums.MessageKey;
import agents.AgentsEnums.MessageValue;
import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.Factory;
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
	
	//Strings with the names of the relevant annotations.
	private static final String ANNOTATION_PERSON = "Person", ANNOTATION_DATE = "Date", ANNOTATION_LOCATION = "Location",
			ANNOTATION_ORGANIZATION = "Organization", ANNOTATION_MONEY = "Money", ANNOTATION_PERCENT = "Percent";
	
	//A list with the names of the relevant annotations.
	private static final List<String> ALLOWED_ANNOTATIONS = Arrays.asList(ANNOTATION_PERSON, ANNOTATION_DATE, ANNOTATION_LOCATION,
			ANNOTATION_ORGANIZATION, ANNOTATION_MONEY, ANNOTATION_PERCENT);
	
	//A hash map holding all the values of each relevant annotation.
	private HashMap<String, HashSet<String>> _annotations;
	
	//Handle to GATE used for extracting information from articles.
	private final GATEHandle GATE_HANDLE;
	
	public InformationExtractorBehavior()
	{
		GATE_HANDLE = new GATEHandle();
		
		initializeMap();
	}

	private void initializeMap()
	{
		_annotations = new HashMap<>();
		
		_annotations.put(ANNOTATION_PERSON, new HashSet<String>());
		_annotations.put(ANNOTATION_DATE, new HashSet<String>());
		_annotations.put(ANNOTATION_LOCATION, new HashSet<String>());
		_annotations.put(ANNOTATION_ORGANIZATION, new HashSet<String>());
		_annotations.put(ANNOTATION_MONEY, new HashSet<String>());
		_annotations.put(ANNOTATION_PERCENT, new HashSet<String>());
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
		
		try
		{
			if(annotations != null)
			{
				ret = EXTRACTION_SUCCESS;

				Document doc = Factory.newDocument(article);
				
				//Loop through each annotation in the document and add its value to the correct set.
				for(Annotation annotation : annotations)
				{
					final String type = annotation.getType();
					
					//Only focus on relevant annotations.
					if(ALLOWED_ANNOTATIONS.contains(type))
					{
						final String annotationValue = gate.Utils.stringFor(doc, annotation); 
						
						_annotations.get(type).add(annotationValue);
					}
				}
			}
		}
		catch(Exception e)
		{
			ret = EXTRACTION_FAILURE;
		}
		
		return ret;
	}
}