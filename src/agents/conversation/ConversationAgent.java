package agents.conversation;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import agents.behaviors.ConversationAgentBehavior;
import gui.TelegramChatbot;
import jade.core.Agent;

/**
 * Agent used to process the information sent by the user.
 * */
public class ConversationAgent extends Agent
{
	private static final long serialVersionUID = 1L;
	private Bot _nlpBot;
	private Chat _nlpChat;
	private TelegramChatbot _telegramBot;
	
	@Override
	protected void setup()
	{
		super.setup();
		
		_nlpBot = new Bot("nlpbot", "src/main/resources");
		_nlpChat = new Chat(_nlpBot);
		
		Object [] args = getArguments();
		
		initializeTelegramChatbot(args[0].toString());
		addBehaviour(new ConversationAgentBehavior());
		
		//AIMLProcessor.extension = new MyProcessorExtension();
	}
	
	private void initializeTelegramChatbot(String telegramApiToken)
	{
		try
		{
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			
			_telegramBot = new TelegramChatbot(telegramApiToken, this);
			
			botsApi.registerBot(_telegramBot);
		}
		catch(TelegramApiException e)
		{
			e.printStackTrace();
		}
	}
	
	public void processTelegramUpdate(Update update)
	{
		//We check if the update has a message and the message has text.
	    if(update.hasMessage() && update.getMessage().hasText())
	    {
	    	Message message = update.getMessage();
	    	String nlpResponse = _nlpChat.multisentenceRespond(message.getText());
	    	
	    	_telegramBot.sendMessage(nlpResponse, update.getMessage().getChatId().toString());
	    }
	}
}