package agents.clustering;

import java.util.HashMap;
import agents.clustering.ClusteringAgent.MessageKey;
import agents.clustering.ClusteringAgent.MessageValue;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

class ClusteringAgentBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = 1L;
	private static final String CLUSTER_ERROR = "It seems that I was not able to perform this action.";

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
				MessageValue action = (MessageValue)messageMap.get(MessageKey.ACTION);
				
				if(action != null)
				{
					switch(action)
					{
					case ACTION_CLUSTER_ARTICLE:
					{
						String article = (String)messageMap.get(MessageKey.ARTICLE_STRING);
						String clusteringResult = performClustering(article);
						
						agent.respondToClusterRequest(clusteringResult);
						break;
					}
					case ACTION_TEST:
					{
						agent.respondToClusterRequest("Hello from the clustering agent :)");
					}
					}
				}
			}
			catch(Exception e)
			{
				agent.respondToClusterRequest(CLUSTER_ERROR);
			}
		}
	}

	private String performClustering(String article)
	{
		// TODO Auto-generated method stub
		return null;
	}
}