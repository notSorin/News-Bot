package main;

import agents.conversation.ConversationAgent;
import agents.informationextractor.InformationExtractorAgent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

/**
 * 
 * @author Sorin
 * Class used for starting and stopping the system.
 */
public class NewsBot
{
	private AgentContainer _agentsContainer;
	
	public NewsBot(String telegramApiToken)
	{
		initializeAgents(telegramApiToken);
	}

	private void initializeAgents(String telegramApiToken)
	{
		Runtime jadeRuntime = Runtime.instance();
		ProfileImpl jadeProfile = new ProfileImpl(false);
		
		_agentsContainer = jadeRuntime.createMainContainer(jadeProfile);
		
		try
		{
			initializeConversationAgent(telegramApiToken);
			initializeInformationExtractorAgent();
			initializeClassificationAgent();
			initializeClusteringAgent();
		}
		catch(StaleProxyException e)
		{
			e.printStackTrace();
		}
	}
	
	private void initializeClusteringAgent() throws StaleProxyException
	{
		// TODO Auto-generated method stub
	}

	private void initializeClassificationAgent() throws StaleProxyException
	{
		// TODO Auto-generated method stub
	}

	private void initializeInformationExtractorAgent() throws StaleProxyException
	{
		AgentController acInformationExtractor = _agentsContainer.createNewAgent(InformationExtractorAgent.class.getSimpleName(), InformationExtractorAgent.class.getName(), null);

		acInformationExtractor.start();
	}

	private void initializeConversationAgent(String telegramApiToken) throws StaleProxyException
	{
		Object [] args = {telegramApiToken};
		
		AgentController acConversation = _agentsContainer.createNewAgent(ConversationAgent.class.getSimpleName(), ConversationAgent.class.getName(), args);

		acConversation.start();
	}

	public void stop()
	{
		try
		{
			if(_agentsContainer != null)
				_agentsContainer.kill();
		}
		catch(StaleProxyException e)
		{
			e.printStackTrace();
		}
	}
}