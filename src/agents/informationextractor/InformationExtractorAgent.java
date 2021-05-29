package agents.informationextractor;

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

	/**
	 * Sends a response message to the conversation agent, containing the result from extracting information from an article.
	 * @param extractionResult The result of performing the information extraction.
	 */
	public void respondToExtractRequest(String extractionResult)
	{
		//TODO
	}
}