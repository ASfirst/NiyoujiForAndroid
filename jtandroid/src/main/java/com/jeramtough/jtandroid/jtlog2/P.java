package com.jeramtough.jtandroid.jtlog2;

/**
 * Created by 11718
 * on 2017  October 14 Saturday 17:18.
 */

public class P
{
	private static JtLogManager jtLogManager;
	
	static
	{
		
		jtLogManager = new JtLogManager();
		
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < JtLog2Config.getJtLog2Config().getMaxLengthOfRow() * 2; i++)
		{
			text.append("-");
		}
		P.p(text.toString());
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
	
	
	public static void info(String tag, String message)
	{
		LogInformation logInformation = new LogInformation(message);
		logInformation.setTag(tag);
		jtLogManager.print(logInformation, MyLoggerFactory.getInfoLogger());
		//		MyLoggerFactory.getInfoLogger().println(logInformation);
	}
	
	public static <T extends Number> void info(String tag, T message)
	{
		LogInformation logInformation = new LogInformation(message + "");
		logInformation.setTag(tag);
		jtLogManager.print(logInformation, MyLoggerFactory.getInfoLogger());
	}
	
	
	public static void info(String tag, Object message)
	{
		LogInformation logInformation = new LogInformation(message.toString());
		logInformation.setTag(tag);
		jtLogManager.print(logInformation, MyLoggerFactory.getInfoLogger());
	}
	
	public static void warn(String tag, String message)
	{
		LogInformation logInformation = new LogInformation(message);
		logInformation.setTag(tag);
		jtLogManager.print(logInformation, MyLoggerFactory.getWarnLogger());
	}
	
	public static <T extends Number> void warn(String tag, T message)
	{
		LogInformation logInformation = new LogInformation(message + "");
		logInformation.setTag(tag);
		jtLogManager.print(logInformation, MyLoggerFactory.getWarnLogger());
	}
	
	
	public static void warn(String tag, Object message)
	{
		LogInformation logInformation = new LogInformation(message.toString());
		logInformation.setTag(tag);
		jtLogManager.print(logInformation, MyLoggerFactory.getWarnLogger());
	}
	
	public static void error(String tag, String message)
	{
		LogInformation logInformation = new LogInformation(message);
		logInformation.setTag(tag);
		jtLogManager.print(logInformation, MyLoggerFactory.getErrorLogger());
	}
	
	public static <T extends Number> void error(String tag, T message)
	{
		LogInformation logInformation = new LogInformation(message + "");
		logInformation.setTag(tag);
		jtLogManager.print(logInformation, MyLoggerFactory.getErrorLogger());
	}
	
	
	public static void error(String tag, Object message)
	{
		LogInformation logInformation = new LogInformation(message.toString());
		logInformation.setTag(tag);
		jtLogManager.print(logInformation, MyLoggerFactory.getErrorLogger());
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
