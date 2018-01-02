package com.jeramtough.jtandroid.function;

import android.content.Context;
import android.content.SharedPreferences;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

@JtComponent
public class LoginPreferences
{
	
	private Context context;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	
	@IocAutowire
	public LoginPreferences(Context context)
	{
		super();
		this.context = context;
		
		sharedPreferences = context.getSharedPreferences(this.getClass().getSimpleName(), 0);
		editor = sharedPreferences.edit();
	}
	
	public void setAccount(String account)
	{
		editor.putString("account", account);
		editor.commit();
	}
	
	public String getAccount()
	{
		return sharedPreferences.getString("account", null);
	}
	
	public void setPassword(String password)
	{
		editor.putString("password", password);
		editor.commit();
	}
	
	public String getPassword()
	{
		return sharedPreferences.getString("password", null);
	}
	
	public void setHasRememberAp(boolean hasRemember)
	{
		editor.putBoolean("hasRemember", hasRemember);
		editor.commit();
	}
	
	public boolean hasRememberAp()
	{
		return sharedPreferences.getBoolean("hasRemember", false);
	}
	
	public void setHasAutomaticallyLogin(boolean hasAutomaticallyLogin)
	{
		editor.putBoolean("hasAutomaticallyLogin", hasAutomaticallyLogin);
		editor.commit();
	}
	
	public boolean hasAutomaticallyLogin()
	{
		return sharedPreferences.getBoolean("hasAutomaticallyLogin", true);
	}
	
}
