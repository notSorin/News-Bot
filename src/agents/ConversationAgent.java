package agents;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import jade.core.Agent;

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
	
	/***
	 * 
	 * @author Sorin
	 * A Telegram bot which allows for interaction between the user and the application.
	 */
	private class TelegramChatbot extends TelegramLongPollingBot
	{
		private final String API_TOKEN;
		private final ConversationAgent CONVERSATION_AGENT;
		
		public TelegramChatbot(String apiToken, ConversationAgent ca)
		{
			API_TOKEN = apiToken;
			CONVERSATION_AGENT = ca;
		}
		
		@Override
		public void onUpdateReceived(Update update)
		{
			CONVERSATION_AGENT.processTelegramUpdate(update);
		}
		
		public void sendMessage(String messageText, String chatId)
		{
			//Create a SendMessage object with mandatory fields.
	        SendMessage message = new SendMessage();
	     
	        message.setChatId(chatId);
	        message.setText(messageText);

	        try
	        {
	            execute(message);
	        }
	        catch(TelegramApiException e)
	        {
	            e.printStackTrace();
	        }
		}

		@Override
		public String getBotUsername()
		{
			return "Whatian";
		}

		@Override
		public String getBotToken()
		{
			return API_TOKEN;
		}
	}
}