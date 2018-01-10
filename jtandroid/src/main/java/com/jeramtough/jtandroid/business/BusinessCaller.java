package com.jeramtough.jtandroid.business;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * @author 11718
 *         on 2018  January 10 Wednesday 20:09.
 */

public class BusinessCaller
{
	private Handler handler;
	private int businessCode;
	private Message message;
	
	public BusinessCaller(Handler handler, int businessCode)
	{
		this.handler = handler;
		this.businessCode = businessCode;
	}
	
	public void callBusiness()
	{
		if (message == null)
		{
			message = new Message();
		}
		message.what = businessCode;
		handler.sendMessage(message);
		message = null;
	}
	
	public Bundle getData()
	{
		if (message == null)
		{
			message = new Message();
		}
		return message.getData();
	}
}
