package chatbot;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
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
	private Bot _nlpBot;
	private Chat _nlpChat;
	
	public TelegramChatbot(String apiToken)
	{
		API_TOKEN = apiToken;
		
		_nlpBot = new Bot("nlpbot", "src/main/resources");
		_nlpChat = new Chat(_nlpBot);
		
		//AIMLProcessor.extension = new MyProcessorExtension();
	}
	
	@Override
	public void onUpdateReceived(Update update)
	{
		//We check if the update has a message and the message has text.
	    if(update.hasMessage() && update.getMessage().hasText())
	    {
	    	Message updateMessage = update.getMessage();
	    	String nlpResponse = _nlpChat.multisentenceRespond(updateMessage.getText());
	    	
	    	//Create a SendMessage object with mandatory fields.
	        SendMessage message = new SendMessage();
	     
	        message.setChatId(updateMessage.getChatId().toString());
	        message.setText(nlpResponse);

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