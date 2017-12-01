package com.jeramtough.jtandroid.jtlog2.logger;

import com.jeramtough.jtandroid.jtlog2.LogInformation;
import com.jeramtough.jtandroid.jtlog2.printer.BasicPrinter;

/**
 * Created by 11718
 * on 2017  October 14 Saturday 17:53.
 */

public class BasicLogger implements Logger
{
	private BasicPrinter basicPrinter;
	private String tagName="jtlog2";
	
	public BasicLogger(BasicPrinter basicPrinter)
	{
		this.basicPrinter = basicPrinter;
	}
	
	public BasicPrinter getPrinter()
	{
		return basicPrinter;
	}
	
	public String getTagName()
	{
		return tagName;
	}
	
	@Override
	public void println(LogInformation logInformation)
	{
		if (logInformation.getMessage()==null)
		{
			logInformation.setMessage("[null]");
		}
		getPrinter().setLogInformation(logInformation);
	}
}
