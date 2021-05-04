package main;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import chatbot.TelegramChatbot;

public class Main
{
	public static void main(String[] args)
	{
		//Only one argument is required: The bot token.
		if(args.length == 1)
		{
			try
			{
				TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
				
				botsApi.registerBot(new TelegramChatbot(args[0]));
			}
			catch(TelegramApiException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			System.err.println("Must provide the bot token as the first argument to the program.");
		}
	}
}