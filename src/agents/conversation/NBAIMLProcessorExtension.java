package agents.conversation;

import java.util.Set;
import org.alicebot.ab.PCAIMLProcessorExtension;
import org.alicebot.ab.ParseState;
import org.alicebot.ab.Utilities;
import org.w3c.dom.Node;

/**
 * @author Sorin
 * A AIML Processor Extension used to processing new AIML tags.
 */
class NBAIMLProcessorExtension extends PCAIMLProcessorExtension
{
	private final Set<String> NEW_TAGS = Utilities.stringSet("TODO");
	
    @Override
	public Set <String> extensionTagSet()
    {
        return NEW_TAGS;
    }
	
	@Override
	public String recursEval(Node node, ParseState ps)
	{
		String ret = null;
		
		try
        {
            String nodeName = node.getNodeName();
            
            //TODO...
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
		
		return ret;
	}
}