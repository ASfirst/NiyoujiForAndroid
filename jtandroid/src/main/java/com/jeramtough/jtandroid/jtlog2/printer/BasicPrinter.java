package com.jeramtough.jtandroid.jtlog2.printer;

import com.jeramtough.jtandroid.jtlog2.JtLog2Config;
import com.jeramtough.jtandroid.jtlog2.LogInformation;
import com.jeramtough.jtandroid.jtlog2.util.MyStringUtil;

/**
 * Created by 11718
 * on 2017  October 14 Saturday 17:44.
 */

public abstract class BasicPrinter
{
	private LogInformation logInformation;
	
	public BasicPrinter()
	{
	}
	
	public LogInformation getLogInformation()
	{
		return logInformation;
	}
	
	public void setLogInformation(LogInformation logInformation)
	{
		this.logInformation = logInformation;
	}
	
	@Override
	public String toString()
	{
		return logInformation.getMessage();
	}
	
	protected String getHead()
	{
		String head = "{time}=" + logInformation.getTime() + " , " + "{thread}=" +
				logInformation.getThread();
		
		return head;
	}
	
	protected String getColorMessage(String color)
	{
		String message = "\n" + MyStringUtil.splitByNumberOfRow(
				logInformation.getMessage() == null ? "null" : logInformation.getMessage(),
				JtLog2Config.getJtLog2Config().getMaxLengthOfRow()) + "\n";
		return message;
	}
	
	
	protected String getPosition()
	{
		return " , {location}=" + logInformation.getClassName() + "." +
				logInformation.getMethodName() + "()" + "." + logInformation.getLine();
	}
	
	protected String getCaller()
	{
		return " , {caller}=(" + logInformation.getFileName() + ":" +
				logInformation.getLine() + ")";
	}
	
	protected String getTag()
	{
		return " , {tag}=" + logInformation.getTag();
	}
	
}
