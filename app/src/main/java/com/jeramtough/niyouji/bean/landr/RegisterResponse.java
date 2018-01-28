package com.jeramtough.niyouji.bean.landr;

/**
 * @author 11718
 *         on 2018  January 28 Sunday 23:18.
 */

public class RegisterResponse
{
	private boolean isSuccessful;
	private String failedMessage;
	
	public boolean isSuccessful()
	{
		return isSuccessful;
	}
	
	public void setSuccessful(boolean successful)
	{
		isSuccessful = successful;
	}
	
	public String getFailedMessage()
	{
		return failedMessage;
	}
	
	public void setFailedMessage(String failedMessage)
	{
		this.failedMessage = failedMessage;
	}
}
