package agents;

import jade.core.Agent;

/**
 * Agent used for retrieving relevant information from a news article.
 * */
public class InformationExtractorAgent extends Agent
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void setup()
	{
		addBehaviour(new InformationExtractorBehavior());
	}
}