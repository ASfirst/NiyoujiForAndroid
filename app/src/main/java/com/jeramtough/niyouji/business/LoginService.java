package com.jeramtough.niyouji.business;

import com.jeramtough.jtandroid.function.LoginPreferences;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.jtandroid.util.CommonValidatorUtil;
import com.jeramtough.niyouji.bean.landr.InputtingLegality;
import com.jeramtough.niyouji.bean.landr.LoginInfo;
import com.jeramtough.niyouji.bean.user.PrimaryInfoOfUser;
import com.jeramtough.niyouji.component.app.AppUser;
import com.jeramtough.niyouji.controller.activity.LoginActivity;

@JtService
public class LoginService implements LoginBusiness
{
	private LoginPreferences loginPreferences;
	private AppUser appUser;
	
	@IocAutowire
	public LoginService(LoginPreferences loginPreferences, AppUser appUser)
	{
		this.loginPreferences = loginPreferences;
		this.appUser = appUser;
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
	public void login(LoginInfo loginInfo, android.os.Handler handler)
	{
		PrimaryInfoOfUser primaryInfoOfUser = new PrimaryInfoOfUser();
		primaryInfoOfUser.setUserId("1");
		primaryInfoOfUser.setNickname("JeramTough");
		primaryInfoOfUser.setPhoneNumber("15289678163");
		primaryInfoOfUser.setSurfaceImageUrl(
				"http://112.74.51.247:8666/randl/surfaceImage?name=default.jpg");
		appUser.setPrimaryInfoOfUser(primaryInfoOfUser);
		handler.sendEmptyMessage(LoginActivity.BUSINESS_CODE_LOGIN_SUCCESSFULLY);
	}
	
}
