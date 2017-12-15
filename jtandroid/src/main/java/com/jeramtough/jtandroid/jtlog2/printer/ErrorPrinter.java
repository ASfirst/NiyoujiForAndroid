package com.jeramtough.jtandroid.jtlog2.printer;

/**
 * Created by 11718
 * on 2017  October 14 Saturday 19:41.
 */

public class ErrorPrinter extends BasicPrinter
{
	@Override
	public String toString()
	{
		String message = getHead() + getTag() + getColorMessage("red");
		return message;
	}
}
