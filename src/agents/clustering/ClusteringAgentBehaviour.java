package agents.clustering;

import java.util.HashMap;
import agents.clustering.ClusteringAgent.MessageKey;
import agents.clustering.ClusteringAgent.MessageValue;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

class ClusteringAgentBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public void action()
	{
		ACLMessage message = getAgent().receive();
		
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
					case CLUSTER_ARTICLE:
					{
						String article = (String)messageMap.get(MessageKey.ARTICLE_STRING);
						String clusteringResult = performClustering(article);
						
						((ClusteringAgent)getAgent()).respondToClusterRequest(clusteringResult);
						break;
					}
					}
				}
			}
			catch(UnreadableException e)
			{
				//TODO Send an error back to the conversation agent.
				e.printStackTrace();
			}
		}
	}

	private String performClustering(String article)
	{
		// TODO Auto-generated method stub
		return null;
	}
}