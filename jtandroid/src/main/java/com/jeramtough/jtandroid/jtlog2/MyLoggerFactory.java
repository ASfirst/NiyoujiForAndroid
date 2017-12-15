package com.jeramtough.jtandroid.jtlog2;


import com.jeramtough.jtandroid.jtlog2.logger.*;
import com.jeramtough.jtandroid.jtlog2.printer.*;

/**
 * Created by 11718
 * on 2017  October 14 Saturday 17:25.
 */

public class MyLoggerFactory
{
	private static volatile SimpleLogger simpleLogger=null;
	private static volatile InfoLogger infoLogger=null;
	private static volatile WarnLogger warnLogger=null;
	private static volatile ErrorLogger errorLogger=null;
	private static volatile VerboseLogger verboseLogger=null;
	private static volatile DebugLogger debugLogger=null;
	
	public static Logger getSimpleLogger()
	{
		if (simpleLogger==null)
		{
			synchronized (MyLoggerFactory.class)
			{
				if (simpleLogger==null)
				{
					SimplePrinter simplePrinter=new SimplePrinter();
					simpleLogger=new SimpleLogger(simplePrinter);
				}
			}
		}
		return simpleLogger;
	}
	
	public static Logger getInfoLogger()
	{
		if (infoLogger==null)
		{
			synchronized (MyLoggerFactory.class)
			{
				if (infoLogger==null)
				{
					InfoPrinter infoPrinter=new InfoPrinter();
					infoLogger=new InfoLogger(infoPrinter);
				}
			}
		}
		return infoLogger;
	}
	
	public static Logger getWarnLogger()
	{
		if (warnLogger==null)
		{
			synchronized (MyLoggerFactory.class)
			{
				if (warnLogger==null)
				{
					WarnPrinter printer=new WarnPrinter();
					warnLogger=new WarnLogger(printer);
				}
			}
		}
		return warnLogger;
	}
	
	public static Logger getErrorLogger()
	{
		if (errorLogger==null)
		{
			synchronized (MyLoggerFactory.class)
			{
				if (errorLogger==null)
				{
					ErrorPrinter errorPrinter=new ErrorPrinter();
					errorLogger=new ErrorLogger(errorPrinter);
				}
			}
		}
		return errorLogger;
	}
	
	public static Logger getDebugLogger()
	{
		if (debugLogger==null)
		{
			synchronized (MyLoggerFactory.class)
			{
				if (debugLogger==null)
				{
					DebugPrinter printer=new DebugPrinter();
					debugLogger=new DebugLogger(printer);
				}
			}
		}
		return debugLogger;
	}
	
	public static Logger getVerboseLogger()
	{
		if (verboseLogger==null)
		{
			synchronized (MyLoggerFactory.class)
			{
				if (verboseLogger==null)
				{
					VerbosePrinter printer=new VerbosePrinter();
					verboseLogger=new VerboseLogger(printer);
				}
			}
		}
		return verboseLogger;
	}
	
}
