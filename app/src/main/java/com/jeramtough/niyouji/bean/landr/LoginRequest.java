package com.jeramtough.niyouji.bean.landr;

/**
 * @author 11718
 *         on 2018  January 28 Sunday 20:54.
 */

public class LoginRequest
{
	private String action="login";
	private LoginRequestMessage message;
	
	public String getAction()
	{
		return action;
	}
	
	public void setAction(String action)
	{
		this.action = action;
	}
	
	public LoginRequestMessage getMessage()
	{
		return message;
	}
	
	public void setMessage(LoginRequestMessage message)
	{
		this.message = message;
	}
}
