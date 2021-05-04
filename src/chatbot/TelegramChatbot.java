package chatbot;

import java.util.Date;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/***
 * 
 * @author Sorin
 * A Telegram bot which allows for interaction between the user and the application.
 */
public class TelegramChatbot extends TelegramLongPollingBot
{
	private final String API_TOKEN;
	
	public TelegramChatbot(String apiToken)
	{
		API_TOKEN = apiToken;
	}
	
	@Override
	public void onUpdateReceived(Update update)
	{
		//We check if the update has a message and the message has text.
	    if(update.hasMessage() && update.getMessage().hasText())
	    {
	    	//Create a SendMessage object with mandatory fields.
	        SendMessage message = new SendMessage();
	     
	        message.setChatId(update.getMessage().getChatId().toString());
	        message.setText((new Date().toString())); //Send back the date for now...

	        try
	        {
	            execute(message);
	        }
	        catch(TelegramApiException e)
	        {
	            e.printStackTrace();
	        }
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