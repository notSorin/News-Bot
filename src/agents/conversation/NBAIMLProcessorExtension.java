package agents.conversation;

import java.util.HashMap;
import java.util.Set;
import org.alicebot.ab.PCAIMLProcessorExtension;
import org.alicebot.ab.ParseState;
import org.alicebot.ab.Utilities;
import org.w3c.dom.Node;
import agents.AgentsEnums.MessageKey;
import agents.AgentsEnums.MessageValue;
import agents.clustering.ClusteringAgent;
import agents.informationextractor.InformationExtractorAgent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

/**
 * @author Sorin
 * An AIML Processor Extension used for processing new AIML tags.
 */
class NBAIMLProcessorExtension extends PCAIMLProcessorExtension
{
	//The minimum length a string must have to be considered an article.
	private static final int MIN_ARTICLE_LENGTH = 100;
	
	//A message error to show when an article does not have a minimum length.
	private static final String ARTICLE_ERROR = "If you were trying to send me an article, make sure it's at least %d characters long; otherwise I didn't understand what you meant.";
	
	private static final String CLUSTER_PETITION_RECEIVED = "I got the article. Hold on a moment while I figure out some keywords about it.";
	private static final String EXTRACT_PETITION_RECEIVED = "I got the article. Hold on a moment while I extract information from it.";
	
	//Constants for each action the user can ask the program to perform.
	private static final String DO_CLUSTER = "docluster", DO_EXTRACT = "doextract", GET_PEOPLE = "getpeople",
			GET_DATES = "getdates", GET_LOCATIONS = "getlocations", GET_ORGANIZATIONS = "getorganizations",
			GET_MONEY = "getmoney", GET_PERCENTAGES = "getpercentages";
	
	//The set with the new AIML tags.
	private final Set<String> NEW_TAGS = Utilities.stringSet(DO_CLUSTER, DO_EXTRACT, GET_PEOPLE, GET_DATES,
			GET_LOCATIONS, GET_ORGANIZATIONS, GET_MONEY, GET_PERCENTAGES);
	
	private ConversationAgent _agent;
	
	public NBAIMLProcessorExtension(ConversationAgent agent)
	{
		_agent = agent;
	}
	
    @Override
	public Set<String> extensionTagSet()
    {
        return NEW_TAGS;
    }
	
	@Override
	public String recursEval(Node node, ParseState ps)
	{
		String ret = null;
		
		try
        {
            final String nodeName = node.getNodeName();
            
            switch(nodeName)
            {
            case DO_CLUSTER:
            	if(ps.input.length() >= MIN_ARTICLE_LENGTH)
            	{
            		sendIntent(ClusteringAgent.class.getSimpleName(), MessageValue.CLUSTER_ARTICLE, ps.input);
            		
            		ret = CLUSTER_PETITION_RECEIVED;
            	}
            	else
            	{
            		ret = String.format(ARTICLE_ERROR, MIN_ARTICLE_LENGTH);
            	}
            	break;
            case DO_EXTRACT:
            	if(ps.input.length() >= MIN_ARTICLE_LENGTH)
            	{
            		sendIntent(InformationExtractorAgent.class.getSimpleName(), MessageValue.EXTRACT_FROM_ARTICLE, ps.input);
            	
            		ret = EXTRACT_PETITION_RECEIVED;
            	}
            	else
            	{
            		ret = String.format(ARTICLE_ERROR, MIN_ARTICLE_LENGTH);
            	}
            	break;
            case GET_PEOPLE:
            	sendIntent(InformationExtractorAgent.class.getSimpleName(), MessageValue.GET_EXTRACTED_PERSON, null);
            	break;
            case GET_DATES:
            	sendIntent(InformationExtractorAgent.class.getSimpleName(), MessageValue.GET_EXTRACTED_DATE, null);
            	break;
            case GET_LOCATIONS:
            	sendIntent(InformationExtractorAgent.class.getSimpleName(), MessageValue.GET_EXTRACTED_LOCATION, null);
            	break;
            case GET_ORGANIZATIONS:
            	sendIntent(InformationExtractorAgent.class.getSimpleName(), MessageValue.GET_EXTRACTED_ORGANIZATION, null);
            	break;
            case GET_MONEY:
            	sendIntent(InformationExtractorAgent.class.getSimpleName(), MessageValue.GET_EXTRACTED_MONEY, null);
            	break;
            case GET_PERCENTAGES:
            	sendIntent(InformationExtractorAgent.class.getSimpleName(), MessageValue.GET_EXTRACTED_PERCENT, null);
            	break;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
		
		return ret;
	}

	/**
	 * Sends an intent message to an Agent.
	 * @param recepientAgentName The name of the agent to whom the intent message is sent. 
	 * @param intent The intended action of the intent.
	 * @param intentData The data to send along with the intent.
	 */
	private void sendIntent(String recepientAgentName, MessageValue intent, Object intentData)
	{
		HashMap<MessageKey, Object> aclContent = new HashMap<MessageKey, Object>();

		aclContent.put(MessageKey.INTENT, intent);
		aclContent.put(MessageKey.INTENT_DATA, intentData);
		
		ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);

		aclMessage.addReceiver(new AID(recepientAgentName, AID.ISLOCALNAME));
		
		try
		{
			aclMessage.setContentObject(aclContent);
			_agent.send(aclMessage);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}