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
	public static final String IS_SUCCESSFUL = "isSuccessful";
	public static final String MESSAGE = "BusinessCaller_message";
	
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
	
	public void setSuccessful(boolean successful)
	{
		getData().putBoolean(IS_SUCCESSFUL, successful);
	}
	
	
	public void setMessage(String message)
	{
		getData().putString(MESSAGE, message);
	}
}
