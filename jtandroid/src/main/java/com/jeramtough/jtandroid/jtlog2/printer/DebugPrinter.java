package com.jeramtough.jtandroid.jtlog2.printer;

/**
 * Created by 11718
 * on 2017  October 14 Saturday 19:40.
 */

public class DebugPrinter extends BasicPrinter
{
	@Override
	public String toString()
	{
		String message=getHead()+getPosition()+getCaller()+getColorMessage("blue");
		return message;
	}
}
