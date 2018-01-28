package com.jeramtough.niyouji.bean.landr;

/**
 * @author 11718
 *         on 2018  January 28 Sunday 20:54.
 */

public class LoginRequestMessage
{
	private String uep;
	private String password;
	
	public String getUep()
	{
		return uep;
	}
	
	public void setUep(String uep)
	{
		this.uep = uep;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
}
