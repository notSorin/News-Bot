package agents.clustering;

import jade.core.Agent;

/**
 * Agent used for clustering news articles.
 * */
public class ClusteringAgent extends Agent
{
	private static final long serialVersionUID = 1L;
	
	//Enums that the agent will receive through messages:
	
	//MessageKeys are used as keys for the hash maps sent as messages.
	public enum MessageKey
	{
		ACTION, ARTICLE_STRING
	}
	
	public enum MessageValue
	{
		CLUSTER_ARTICLE
	}

	@Override
	protected void setup()
	{
		addBehaviour(new ClusteringAgentBehaviour());
	}
	
	void respondToClusterRequest(String clusterResult)
	{
		//TODO
	}
}