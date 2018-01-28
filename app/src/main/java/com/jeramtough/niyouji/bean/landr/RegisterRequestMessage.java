package com.jeramtough.niyouji.bean.landr;

/**
 * @author 11718
 *         on 2018  January 28 Sunday 20:43.
 */

public class RegisterRequestMessage
{
	private String phoneNumber;
	private String smsVerificationCode;
	private String password;
	
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	public String getSmsVerificationCode()
	{
		return smsVerificationCode;
	}
	
	public void setSmsVerificationCode(String smsVerificationCode)
	{
		this.smsVerificationCode = smsVerificationCode;
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
