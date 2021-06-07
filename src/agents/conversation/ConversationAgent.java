package agents.conversation;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
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
	private Update _lastUpdate;
	
	@Override
	protected void setup()
	{
		super.setup();
		
		_nlpBot = new Bot("nlpbot", "./resources");
		_nlpChat = new Chat(_nlpBot);
		_lastUpdate = null;
		
		Object [] args = getArguments();
		
		initializeTelegramChatbot(args[0].toString());
		addBehaviour(new ConversationAgentBehavior());
		
		//We set our own processor extension for processing new AIML tags.
		AIMLProcessor.extension = new NBAIMLProcessorExtension(this);
	}
	
	/**
	 * Initializes the class' telegram bot.
	 * @param telegramApiToken The Telegram API token.
	 */
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
	
	/**
	 * Takes an update from Telegram and processes it using Natural Language Processing.
	 * @param update A Telegram Update.
	 */
	void processTelegramUpdate(Update update)
	{
		//We check if the update has a message and the message has text.
	    if(update.hasMessage() && update.getMessage().hasText())
	    {
	    	_lastUpdate = update;
	    	
	    	//Sanitize the user input.
	    	final String messageText = update.getMessage().getText();
	    	String sanitizedInput = messageText.replaceAll("['.\"]", "");
	    	
	    	sanitizedInput = sanitizedInput.replaceAll("[^$£€a-zA-Z0-9- ]", " ");
	    	
	    	final String nlpResponse = _nlpChat.multisentenceRespond(sanitizedInput);
	    	
	    	if(!nlpResponse.equals("null"))
	    	{
	    		_telegramBot.sendMessage(nlpResponse, update.getMessage().getChatId().toString());
	    	}
	    }
	}
	
	/**
	 * Sends a Telegram message to the chat in the last Update received.
	 * @param message Message text to send to the Telegram chat.
	 */
	void sendTelegramMessage(String message)
	{
		final String chatId = _lastUpdate.getMessage().getChatId().toString();
		
		_telegramBot.sendMessage(message, chatId);
	}
	
	TelegramChatbot getTelegramChatbot()
	{
		return _telegramBot;
	}
	
	Update getLastUpdate()
	{
		return _lastUpdate;
	}
}