package com.jeramtough.jtandroid.jtlog2.printer;

/**
 * Created by 11718
 * on 2017  October 14 Saturday 19:37.
 */

public class WarnPrinter extends BasicPrinter
{
	@Override
	public String toString()
	{
		String message=getHead()+getColorMessage("black");
		return message;
	}
}
