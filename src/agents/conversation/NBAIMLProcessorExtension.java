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
import jade.core.AID;
import jade.lang.acl.ACLMessage;

/**
 * @author Sorin
 * An AIML Processor Extension used for processing new AIML tags.
 */
class NBAIMLProcessorExtension extends PCAIMLProcessorExtension
{
	private final Set<String> NEW_TAGS = Utilities.stringSet("docluster");
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
            String nodeName = node.getNodeName();
            
            if(nodeName.equals("docluster"))
            {
            	sendClusterIntent(ps.input);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
		
		return ret;
	}

	/**
	 * Sends an intent to the Clustering Agent.
	 * @param article The string of the article to perform clustering on.
	 */
	private void sendClusterIntent(String article)
	{
		HashMap<MessageKey, Object> aclContent = new HashMap<MessageKey, Object>();

		aclContent.put(MessageKey.INTENT, MessageValue.CLUSTER_ARTICLE);
		aclContent.put(MessageKey.INTENT_DATA, article);
		
		ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);

		aclMessage.addReceiver(new AID(ClusteringAgent.class.getSimpleName(), AID.ISLOCALNAME));
		
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