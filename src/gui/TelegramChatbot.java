package gui;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import agents.ConversationAgent;

/***
 * 
 * @author Sorin
 * A Telegram bot which allows for interaction between the user and the application.
 */
public class TelegramChatbot extends TelegramLongPollingBot
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