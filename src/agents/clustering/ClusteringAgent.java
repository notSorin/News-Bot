package agents.clustering;

import jade.core.Agent;

/**
 * Agent used for clustering news articles.
 * */
public class ClusteringAgent extends Agent
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void setup()
	{
		addBehaviour(new ClusteringAgentBehaviour());
	}
}