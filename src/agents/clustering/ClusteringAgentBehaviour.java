package agents.clustering;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

class ClusteringAgentBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = 1L;

	@Override
	public void action()
	{
		ACLMessage message = getAgent().receive();
		
		if(message != null)
		{
			//TODO
		}
	}
}