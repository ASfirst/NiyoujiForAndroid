package com.jeramtough.niyouji.bean.landr;

/**
 * @author 11718
 *         on 2017  December 30 Saturday 13:18.
 */

public class RegisterInfo
{
	private String phoneNumber;
	private String password;
	private String repeatPassword;
	private String verificationCode;
	
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
	
	public String getRepeatPassword()
	{
		return repeatPassword;
	}
	
	public void setRepeatPassword(String repeatPassword)
	{
		this.repeatPassword = repeatPassword;
	}
	
	public String getVerificationCode()
	{
		return verificationCode;
	}
	
	public void setVerificationCode(String verificationCode)
	{
		this.verificationCode = verificationCode;
	}
}
