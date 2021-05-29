package agents.informationextractor;

import java.io.File;
import gate.AnnotationSet;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.util.persistence.PersistenceManager;

/**
 * Class used for extracting information from news articles, using GATE.
 * @author Sorin
 *
 */
public class GATEHandle
{
	private CorpusController _controller;
	
	public GATEHandle()
	{
		try
		{
			Gate.init();
			
			File gappFile = new File("./resources/gate/ANNIE.xgapp");
			
			_controller = (CorpusController)PersistenceManager.loadObjectFromFile(gappFile);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns an annotation set of an input string, or null if an error occurs.
	 * @param input Any input string, preferably representing the text of a news article.
	 * @return An annotation set of an input string, or null if an error occurs.
	 */
	public AnnotationSet getAnnotations(String input)
	{
		AnnotationSet annotations = null;
		
		if(!input.isEmpty())
		{
			try
			{
				Document doc = Factory.newDocument(input);
				Corpus corpus = Factory.newCorpus("StandAloneAnnie corpus");
				
				corpus.add(doc);
				_controller.setCorpus(corpus);
				_controller.execute();
				
				annotations = doc.getAnnotations();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return annotations;
	}
}