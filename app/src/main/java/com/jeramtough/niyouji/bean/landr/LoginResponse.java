package com.jeramtough.niyouji.bean.landr;

import com.jeramtough.niyouji.bean.user.PrimaryInfoOfUser;

/**
 * @author 11718
 *         on 2018  January 28 Sunday 21:51.
 */

public class LoginResponse
{
	private boolean isSuccessful;
	private String failedMessage="登录失败！";
	private PrimaryInfoOfUser primaryInfoOfUser;
	
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
	
	public PrimaryInfoOfUser getPrimaryInfoOfUser()
	{
		return primaryInfoOfUser;
	}
	
	public void setPrimaryInfoOfUser(PrimaryInfoOfUser primaryInfoOfUser)
	{
		this.primaryInfoOfUser = primaryInfoOfUser;
	}
}
