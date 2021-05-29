package agents.clustering;

import java.util.HashMap;
import agents.AgentsEnums.MessageKey;
import agents.AgentsEnums.MessageValue;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * The behavior class for the Clustering Agent. It receives ACL messages and reacts accordingly.
 * @author Sorin
 *
 */
class ClusteringAgentBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = 1L;
	private static final String CLUSTER_EXCEPTION = "It seems that I was not able to perform this action.";
	private static final String CLUSTER_ERROR = "I could not perform clustering on the given article...";
	
	private final TopicModel _topicModel;
	
	ClusteringAgentBehaviour()
	{
		_topicModel = new TopicModel();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void action()
	{
		final ACLMessage message = getAgent().receive();
		final ClusteringAgent agent = ((ClusteringAgent)getAgent());
		
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
					case CLUSTER_ARTICLE:
					{
						String article = (String)messageMap.get(MessageKey.INTENT_DATA);
						String clusteringResult = performClustering(article);
						
						agent.respondToRequest(MessageValue.RESPONSE_CLUSTER_ARTICLE, clusteringResult);
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
				agent.respondToRequest(MessageValue.RESPONSE_CLUSTER_ARTICLE, CLUSTER_EXCEPTION);
			}
		}
	}

	/**
	 * Obtains inferred topics from the Topic Model.
	 * @param article A news article's text.
	 * @return A string with the inferred topics, or an error message on error.
	 */
	private String performClustering(String article)
	{
		String ret = CLUSTER_ERROR;
		final String topicResult = _topicModel.inferTopics(article);
		
		if(topicResult != null)
			ret = topicResult;
		
		return ret;
	}
}