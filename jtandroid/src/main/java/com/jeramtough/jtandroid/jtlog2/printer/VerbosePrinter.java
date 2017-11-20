package com.jeramtough.jtandroid.jtlog2.printer;

/**
 * Created by 11718
 * on 2017  October 14 Saturday 19:38.
 */

public class VerbosePrinter extends BasicPrinter
{
	@Override
	public String toString()
	{
		String message;
		
		if (getLogInformation().getMessage().length() != 0)
		{
			message = getHead() + getPosition() + getCaller() + getColorMessage("black");
		}
		else
		{
			String arriveTag ="(O.O)";
			
			message = arriveTag + "   {time}=" + getLogInformation().getTime() + " , " +
					"{thread}=" + getLogInformation().getThread() + getPosition() +
					getCaller()+"\n";
		}
		return message;
	}
}
