package com.jeramtough.niyouji.bean.landr;

public class LoginInfo
{
	private String phoneNumber;
	private String password;
	
	public LoginInfo()
	{
	}
	
	public LoginInfo(String phoneNumber, String password)
	{
		super();
		this.phoneNumber = phoneNumber;
		this.password = password;
	}
	
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
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
