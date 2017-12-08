package com.jeramtough.jtandroid.jtlog2;

/**
 * Created by 11718
 * on 2017  October 14 Saturday 17:18.
 */

public class P
{
	
	static
	{
		StringBuilder text= new StringBuilder();
		for (int i = 0; i< Configuration.getConfiguration().getMaxNumberOfRow()*2; i++)
		{
			text.append("-");
		}
		P.p(text.toString());
	}
	
	private P()
	{
		
	}
	
	public static void p(String message)
	{
		LogInformation logInformation = new LogInformation(message);
		MyLoggerFactory.getSimpleLogger().println(logInformation);
	}
	
	public static <T extends Number> void p(T message)
	{
		LogInformation logInformation = new LogInformation(message + "");
		MyLoggerFactory.getSimpleLogger().println(logInformation);
	}
	
	public static void p(Object message)
	{
		LogInformation logInformation = new LogInformation(message.toString());
		MyLoggerFactory.getSimpleLogger().println(logInformation);
	}
	
	
	public static void info(String message)
	{
		LogInformation logInformation = new LogInformation(message);
		MyLoggerFactory.getInfoLogger().println(logInformation);
	}
	
	public static <T extends Number> void info(T message)
	{
		LogInformation logInformation = new LogInformation(message + "");
		MyLoggerFactory.getInfoLogger().println(logInformation);
	}
	
	
	public static void info(Object message)
	{
		LogInformation logInformation = new LogInformation(message.toString());
		MyLoggerFactory.getInfoLogger().println(logInformation);
	}
	
	public static void warn(String message)
	{
		LogInformation logInformation = new LogInformation(message);
		MyLoggerFactory.getWarnLogger().println(logInformation);
	}
	
	public static <T extends Number> void warn(T message)
	{
		LogInformation logInformation = new LogInformation(message + "");
		MyLoggerFactory.getWarnLogger().println(logInformation);
	}
	
	
	public static void warn(Object message)
	{
		LogInformation logInformation = new LogInformation(message.toString());
		MyLoggerFactory.getWarnLogger().println(logInformation);
	}
	
	public static void error(String message)
	{
		LogInformation logInformation = new LogInformation(message);
		MyLoggerFactory.getErrorLogger().println(logInformation);
	}
	
	public static <T extends Number> void error(T message)
	{
		LogInformation logInformation = new LogInformation(message + "");
		MyLoggerFactory.getErrorLogger().println(logInformation);
	}
	
	
	public static void error(Object message)
	{
		LogInformation logInformation = new LogInformation(message.toString());
		MyLoggerFactory.getErrorLogger().println(logInformation);
	}
	
	public static void debug(String message)
	{
		LogInformation logInformation = new LogInformation(message);
		MyLoggerFactory.getDebugLogger().println(logInformation);
	}
	
	public static <T extends Number> void debug(T message)
	{
		LogInformation logInformation = new LogInformation(message + "");
		MyLoggerFactory.getDebugLogger().println(logInformation);
	}
	
	public static void debug(Object... messages)
	{
		StringBuilder stringBuilder = new StringBuilder();
		for (Object message : messages)
		{
			if (message == null)
			{
				message = "[null]";
			}
			stringBuilder.append(message.toString()).append(" , ");
		}
		String message =
				stringBuilder.toString().substring(0, stringBuilder.toString().length() - 3);
		LogInformation logInformation = new LogInformation(message);
		MyLoggerFactory.getDebugLogger().println(logInformation);
	}
	
	
	public static void debug(Object message)
	{
		LogInformation logInformation = new LogInformation(message.toString());
		MyLoggerFactory.getDebugLogger().println(logInformation);
	}
	
	public static void verbose(String message)
	{
		LogInformation logInformation = new LogInformation(message);
		MyLoggerFactory.getVerboseLogger().println(logInformation);
	}
	
	public static <T extends Number> void verbose(T message)
	{
		LogInformation logInformation = new LogInformation(message + "");
		MyLoggerFactory.getVerboseLogger().println(logInformation);
	}
	
	
	public static void verbose(Object message)
	{
		LogInformation logInformation = new LogInformation(message.toString());
		MyLoggerFactory.getVerboseLogger().println(logInformation);
	}
	
	public static void arrive()
	{
		LogInformation logInformation = new LogInformation("");
		MyLoggerFactory.getVerboseLogger().println(logInformation);
	}
	
}
