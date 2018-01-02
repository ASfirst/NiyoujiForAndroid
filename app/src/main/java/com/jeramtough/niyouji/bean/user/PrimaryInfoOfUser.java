package com.jeramtough.niyouji.bean.user;

/**
 * @author 11718
 *         on 2017  November 29 Wednesday 18:57.
 */

public class PrimaryInfoOfUser
{
	private String userId;
	private String nickname;
	private String phoneNumber;
	private String surfaceImageUrl;
	
	public String getUserId()
	{
		return userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getNickname()
	{
		return nickname;
	}
	
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
	
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	public String getSurfaceImageUrl()
	{
		return surfaceImageUrl;
	}
	
	public void setSurfaceImageUrl(String surfaceImageUrl)
	{
		this.surfaceImageUrl = surfaceImageUrl;
	}
}
