package agents;

import agents.behaviors.ClassificationAgentBehavior;
import jade.core.Agent;

/**
 * Agent used for classifying news articles to a specific category.
 * */
public class ClassificationAgent extends Agent
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void setup()
	{
		addBehaviour(new ClassificationAgentBehavior());
	}
}