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
		//Actions.
		CLUSTER_ARTICLE, EXTRACT_FROM_ARTICLE,
		
		//Responses.
		RESPONSE_EXTRACT_FROM_ARTICLE, RESPONSE_CLUSTER_ARTICLE,
		RESPONSE_GET,
		
		//Gets for information extraction.
		GET_EXTRACTED_PERSON, GET_EXTRACTED_DATE,GET_EXTRACTED_LOCATION,
		GET_EXTRACTED_ORGANIZATION, GET_EXTRACTED_MONEY, GET_EXTRACTED_PERCENT
	}
}