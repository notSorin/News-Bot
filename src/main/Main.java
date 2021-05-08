package main;

/***
 * 
 * @author Sorin
 * Entry point of the application.
 */
public class Main
{
	public static void main(String[] args)
	{
		//Only one argument is required: The bot token.
		if(args.length == 1)
		{
			new NewsBot(args[0]);
		}
		else
		{
			System.err.println("Must provide the Telegram API Token as the first argument to the program.");
		}
	}
}