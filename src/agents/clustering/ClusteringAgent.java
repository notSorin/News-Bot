package agents.clustering;

import agents.NewsBotAgent;

/**
 * Agent used for clustering news articles.
 * */
public class ClusteringAgent extends NewsBotAgent
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void setup()
	{
		addBehaviour(new ClusteringAgentBehaviour());
	}
}