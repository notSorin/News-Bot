package agents.clustering;

import java.util.HashMap;
import agents.AgentsEnums.MessageKey;
import agents.AgentsEnums.MessageValue;
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
				MessageValue intent = (MessageValue)messageMap.get(MessageKey.INTENT);
				
				if(intent != null)
				{
					switch(intent)
					{
					case CLUSTER_ARTICLE:
					{
						String article = (String)messageMap.get(MessageKey.INTENT_DATA);
						String clusteringResult = performClustering(article);
						
						agent.respondToClusterRequest(clusteringResult);
						break;
					}
					case TEST:
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