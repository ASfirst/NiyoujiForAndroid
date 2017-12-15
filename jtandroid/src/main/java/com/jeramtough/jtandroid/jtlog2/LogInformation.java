package com.jeramtough.jtandroid.jtlog2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 11718
 * on 2017  October 14 Saturday 17:38.
 */

public class LogInformation
{
	private String message;
	private StackTraceElement stackTraceElement;
	private String tag="default";
	
	public LogInformation(String message)
	{
		this.message = message;
		/*int length=((new Exception()).getStackTrace()).length;
		int callerNumber=length-10;
		System.out.println(length+","+callerNumber);*/
		stackTraceElement = ((new Exception()).getStackTrace())[2];
	}
	
	public String getTime()
	{
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
		String time = format.format(date);
		return time;
	}
	
	public String getThread()
	{
		String thread = Thread.currentThread().getName();
		return thread;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public String getClassName()
	{
		String className = stackTraceElement.getClassName();
		return className;
	}
	
	public String getMethodName()
	{
		String methodName = stackTraceElement.getMethodName();
		return methodName;
	}
	
	public String getLine()
	{
		String line = stackTraceElement.getLineNumber() + "";
		return line;
	}
	
	public String getFileName()
	{
		return stackTraceElement.getFileName();
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	public String getTag()
	{
		return tag;
	}
	
	public void setTag(String tag)
	{
		this.tag = tag;
	}
}
