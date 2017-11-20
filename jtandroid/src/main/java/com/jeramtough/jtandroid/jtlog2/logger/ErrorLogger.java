package com.jeramtough.jtandroid.jtlog2.logger;

import android.util.Log;
import com.jeramtough.jtandroid.jtlog2.LogInformation;
import com.jeramtough.jtandroid.jtlog2.printer.BasicPrinter;

/**
 * Created by 11718
 * on 2017  October 14 Saturday 19:31.
 */

public class ErrorLogger extends BasicLogger
{
	public ErrorLogger(BasicPrinter basicPrinter)
	{
		super(basicPrinter);
	}
	
	@Override
	public void println(LogInformation logInformation)
	{
		super.println(logInformation);
		Log.e(getTagName(),getPrinter().toString());
	}
}
