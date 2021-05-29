package agents.informationextractor;

import agents.NewsBotAgent;

/**
 * Agent used for retrieving relevant information from a news article.
 * */
public class InformationExtractorAgent extends NewsBotAgent
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void setup()
	{
		addBehaviour(new InformationExtractorBehavior());
	}
}