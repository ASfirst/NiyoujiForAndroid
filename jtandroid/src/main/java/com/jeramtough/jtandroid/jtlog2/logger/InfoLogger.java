package com.jeramtough.jtandroid.jtlog2.logger;


import android.util.Log;
import com.jeramtough.jtandroid.jtlog2.LogInformation;
import com.jeramtough.jtandroid.jtlog2.printer.BasicPrinter;

/**
 * Created by 11718
 * on 2017  October 14 Saturday 17:52.
 */

public class InfoLogger extends BasicLogger
{
	public InfoLogger(BasicPrinter basicPrinter)
	{
		super(basicPrinter);
	}
	
	@Override
	public void println(LogInformation logInformation)
	{
		super.println(logInformation);
		Log.i(getTagName(),getPrinter().toString());
	}
}
