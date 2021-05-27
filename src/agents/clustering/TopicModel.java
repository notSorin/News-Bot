package agents.clustering;

import java.io.File;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;

class TopicModel
{
	private ParallelTopicModel _topicModel;
	private InstanceList _sportInstance;
	
	TopicModel()
	{
		try
		{
			//Load the model from file.
			_topicModel = ParallelTopicModel.read(new File("./resources/mallet/model"));
			
			//Load the sport instance list from file.
			_sportInstance = InstanceList.load(new File("./resources/mallet/sport.mallet"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	String inferTopics(String article)
	{
		String inferredTopics = null;
		
		//Create a new instance list using the pipe from the sport instance.
		final InstanceList newInstances = new InstanceList(_sportInstance.getPipe());
		
		//Add the article's text as an Instance to the new instance list.
		newInstances.addThruPipe(new Instance(article, "user article", null, null));
		
		//Get the sampled distribution for the new instance.
		final double [] distributions = _topicModel.getInferencer().getSampledDistribution(newInstances.get(0), 10, 1, 5);
		double maxDistribution = distributions[0];
		int maxDistributionPosition = 0;
		
		//Find the greatest distribution value.
		for(int i = 0; i < distributions.length; i++)
		{
			if(distributions[i] > maxDistribution)
			{
				maxDistribution = distributions[i];
				maxDistributionPosition = i;
			}
		}
		
		//Get the top 20 words from the topic with the greatest distribution value.
		Object[][] topWords = _topicModel.getTopWords(20);
		final int totalTopWords = topWords[maxDistributionPosition].length;
		
		if(totalTopWords > 0)
		{
			final int toLoop = totalTopWords > 20 ? 20 : totalTopWords;
			
			inferredTopics = "These are the top " + toLoop + " topics related to your article, matching at " + (int)(maxDistribution * 100) + "%...\n\n";
			
			//Only return the words from the topic with the greatest distribution value (maxDistributionPosition).
			for(int i = 0; i < toLoop; i++)
			{
				inferredTopics += topWords[maxDistributionPosition][i] + " ";
			}
		}
		
		return inferredTopics;
	}
}