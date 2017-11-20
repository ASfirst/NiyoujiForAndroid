package com.jeramtough.jtandroid.jtlog2.printer;

/**
 * Created by 11718
 * on 2017  October 14 Saturday 18:02.
 */

public class InfoPrinter extends BasicPrinter
{
	@Override
	public String toString()
	{
		String message=getHead()+getColorMessage("green");
		return message;
	}
}
