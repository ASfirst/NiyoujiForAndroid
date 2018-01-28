package com.jeramtough.niyouji.bean.landr;

/**
 * @author 11718
 *         on 2018  January 28 Sunday 20:46.
 */

public class RegisterRequest
{
	private String action="register";
	private RegisterRequestMessage message;
	
	public String getAction()
	{
		return action;
	}
	
	public void setAction(String action)
	{
		this.action = action;
	}
	
	public RegisterRequestMessage getMessage()
	{
		return message;
	}
	
	public void setMessage(RegisterRequestMessage message)
	{
		this.message = message;
	}
}
