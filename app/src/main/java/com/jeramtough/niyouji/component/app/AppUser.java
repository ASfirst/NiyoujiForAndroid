package com.jeramtough.niyouji.component.app;

import android.content.Context;
import android.content.SharedPreferences;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.niyouji.bean.user.PrimaryInfoOfUser;

/**
 * @author 11718
 *         on 2017  December 30 Saturday 14:01.
 */
@JtComponent
public class AppUser
{
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	
	@IocAutowire
	public AppUser(Context context)
	{
		sharedPreferences = context.getSharedPreferences(this.getClass().getSimpleName(), 0);
		editor = sharedPreferences.edit();
	}
	
	public void setPrimaryInfoOfUser(PrimaryInfoOfUser primaryInfoOfUser)
	{
		editor.putBoolean("hasLogined", true);
		
		editor.putString("userId", primaryInfoOfUser.getUserId());
		editor.putString("nickname", primaryInfoOfUser.getNickname());
		editor.putString("phoneNumber", primaryInfoOfUser.getPhoneNumber());
		editor.putString("surfaceImageUrl", primaryInfoOfUser.getSurfaceImageUrl());
		editor.commit();
	}
	
	public boolean getHasLogined()
	{
		return sharedPreferences.getBoolean("hasLogined", false);
	}
	
	public void setHasLogined(boolean hasLogined)
	{
		editor.putBoolean("hasLogined", hasLogined);
		editor.commit();
	}
	
	public String getUserId()
	{
		return sharedPreferences.getString("userId", null);
	}
	
	public String getNickname()
	{
		return sharedPreferences.getString("nickname", null);
	}
	
	public String getPhoneNumber()
	{
		return sharedPreferences.getString("phoneNumber", null);
	}
	
	public String getSurfaceImageUrl()
	{
		return sharedPreferences.getString("surfaceImageUrl", null);
	}
	
	public PrimaryInfoOfUser getPrimaryInfoOfUser()
	{
		PrimaryInfoOfUser primaryInfoOfUser = new PrimaryInfoOfUser();
		primaryInfoOfUser.setUserId(getUserId());
		primaryInfoOfUser.setNickname(getNickname());
		primaryInfoOfUser.setPhoneNumber(getPhoneNumber());
		primaryInfoOfUser.setSurfaceImageUrl(getSurfaceImageUrl());
		return primaryInfoOfUser;
	}
	
	public void setFreshmen(boolean isFreshmen)
	{
		editor.putBoolean("isFreshmen", isFreshmen);
		editor.commit();
	}
	
	public boolean isFreshmen()
	{
		return sharedPreferences.getBoolean("isFreshmen", true);
	}
	
	public boolean isFirstBoot()
	{
		return sharedPreferences.getBoolean("isFirstBoot", true);
	}
	
	public void setFirstBoot(boolean isFirstBoot)
	{
		editor.putBoolean("isFirstBoot", isFirstBoot);
		editor.commit();
	}
}
