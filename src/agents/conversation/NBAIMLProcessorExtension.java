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
	private final Set<String> NEW_TAGS = Utilities.stringSet("docluster", "doextract");
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
            case "docluster":
            	sendIntent(ClusteringAgent.class.getSimpleName(), MessageValue.CLUSTER_ARTICLE, ps.input);
            	break;
            case "doextract":
            	sendIntent(InformationExtractorAgent.class.getSimpleName(), MessageValue.EXTRACT_FROM_ARTICLE, ps.input);
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