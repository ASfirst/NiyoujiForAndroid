package com.jeramtough.niyouji.business;

import android.content.Context;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.function.LoginPreferences;
import com.jeramtough.jtandroid.function.NetworkIsAble;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.jtandroid.util.CommonValidatorUtil;
import com.jeramtough.niyouji.bean.landr.InputtingLegality;
import com.jeramtough.niyouji.bean.landr.LoginInfo;
import com.jeramtough.niyouji.bean.landr.LoginResponse;
import com.jeramtough.niyouji.bean.user.PrimaryInfoOfUser;
import com.jeramtough.niyouji.component.app.AppUser;
import com.jeramtough.niyouji.component.httpclient.RandLHttpClient;

@JtService
public class LoginService implements LoginBusiness
{
	private LoginPreferences loginPreferences;
	private AppUser appUser;
	private RandLHttpClient randLHttpClient;
	private NetworkIsAble networkIsAble;
	
	@IocAutowire
	public LoginService(LoginPreferences loginPreferences, AppUser appUser,
			RandLHttpClient randLHttpClient, NetworkIsAble networkIsAble)
	{
		this.loginPreferences = loginPreferences;
		this.appUser = appUser;
		this.randLHttpClient = randLHttpClient;
		this.networkIsAble = networkIsAble;
	}
	
	@Override
	public InputtingLegality checkInputtingIsLegal(LoginInfo loginInfo)
	{
		InputtingLegality inputtingLegality = new InputtingLegality();
		if (loginInfo.getPhoneNumber() == null || loginInfo.getPhoneNumber().length() == 0 ||
				loginInfo.getPassword() == null || loginInfo.getPassword().length() == 0)
		{
			inputtingLegality.setPassed(false);
			inputtingLegality.setIllegalMessage("登录信息未填写！");
			return inputtingLegality;
		}
		
		if (CommonValidatorUtil.isMobile(loginInfo.getPhoneNumber()) == false)
		{
			inputtingLegality.setPassed(false);
			inputtingLegality.setIllegalMessage("手机号码格式不正确！");
			return inputtingLegality;
		}
		
		if (loginInfo.getPassword().length() < 6)
		{
			inputtingLegality.setPassed(false);
			inputtingLegality.setIllegalMessage("密码长度6位及以上！");
			return inputtingLegality;
			
		}
		
		if (CommonValidatorUtil.isPassword(loginInfo.getPassword()) == false)
		{
			inputtingLegality.setPassed(false);
			inputtingLegality.setIllegalMessage("密码只能是字母和数字！");
			return inputtingLegality;
			
		}
		inputtingLegality.setPassed(true);
		return inputtingLegality;
	}
	
	@Override
	public boolean checkNetwork(Context context)
	{
		return networkIsAble.isNetworkConnected(context);
	}
	
	@Override
	public LoginInfo getHasRememberLoginInfo()
	{
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setPassword(loginPreferences.getPassword());
		loginInfo.setPhoneNumber(loginPreferences.getAccount());
		return loginInfo;
	}
	
	@Override
	public void rememberLoginInfo(LoginInfo loginInfo)
	{
		loginPreferences.setAccount(loginInfo.getPhoneNumber());
		loginPreferences.setPassword(loginInfo.getPassword());
	}
	
	@Override
	public void login(LoginInfo loginInfo, BusinessCaller businessCaller)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				LoginResponse loginResponse = randLHttpClient
						.userLogin(loginInfo.getPhoneNumber(), loginInfo.getPassword());
				if (loginResponse.isSuccessful())
				{
					PrimaryInfoOfUser primaryInfoOfUser = loginResponse.getPrimaryInfoOfUser();
					appUser.setPrimaryInfoOfUser(primaryInfoOfUser);
					
					businessCaller.getData().putBoolean("isSuccessful", true);
				}
				else
				{
					businessCaller.getData().putBoolean("isSuccessful", false);
					businessCaller.getData()
							.putString("failedMessage", loginResponse.getFailedMessage());
				}
				
				businessCaller.callBusiness();
			}
		}.start();
		
	}
	
	
}
