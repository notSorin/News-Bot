package agents;

/**
 * Class containing some enums used by the Agents to communicate with each other.
 * @author Sorin
 *
 */
public class AgentsEnums
{
	//Enums that the agents will receive through messages:
	
	//MessageKeys are used as keys for the hash maps sent as messages.
	public enum MessageKey
	{
		INTENT, INTENT_DATA
	}
	
	//MessageValues are used as values for the hash maps sent as messages.
	public enum MessageValue
	{
		CLUSTER_ARTICLE, TEST, RESPONSE_CLUSTER_ARTICLE
	}
}