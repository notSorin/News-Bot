package agents.conversation;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/***
 * 
 * @author Sorin
 * A Telegram bot which allows for interaction between the user and the application.
 */
class TelegramChatbot extends TelegramLongPollingBot
{
	private final String API_TOKEN;
	private final ConversationAgent CONVERSATION_AGENT;
	
	public TelegramChatbot(String apiToken, ConversationAgent ca)
	{
		API_TOKEN = apiToken;
		CONVERSATION_AGENT = ca;
	}
	
	/**
	 * Called whenever a message is sent through Telegram.
	 */
	@Override
	public void onUpdateReceived(Update update)
	{
		//Let the agent decide what to do with the update.
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