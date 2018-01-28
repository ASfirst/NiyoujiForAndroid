package com.jeramtough.niyouji.business;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.function.NetworkIsAble;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.jtandroid.util.CommonValidatorUtil;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.bean.landr.InputtingLegality;
import com.jeramtough.niyouji.bean.landr.RegisterInfo;
import com.jeramtough.niyouji.bean.landr.RegisterResponse;
import com.jeramtough.niyouji.component.httpclient.RandLHttpClient;
import com.jeramtough.niyouji.controller.activity.RegisterActivity;

/**
 * @author 11718
 */
@JtService
public class RegisterService implements RegisterBusiness
{
	private RandLHttpClient randLHttpClient;
	private NetworkIsAble networkIsAble;
	
	@IocAutowire
	public RegisterService(RandLHttpClient randLHttpClient, NetworkIsAble networkIsAble)
	{
		this.randLHttpClient = randLHttpClient;
		this.networkIsAble = networkIsAble;
	}
	
	@Override
	public InputtingLegality checkInputtingIsLegal(RegisterInfo registerInfo)
	{
		InputtingLegality inputtingLegality = new InputtingLegality();
		inputtingLegality.setPassed(false);
		
		if (registerInfo.getPhoneNumber() == null ||
				registerInfo.getPhoneNumber().length() == 0 ||
				registerInfo.getPassword() == null || registerInfo.getPassword().length() == 0)
		{
			inputtingLegality.setPassed(false);
			inputtingLegality.setIllegalMessage("注册信息有未填写！");
			return inputtingLegality;
		}
		
		if (CommonValidatorUtil.isMobile(registerInfo.getPhoneNumber()) == false)
		{
			inputtingLegality.setPassed(false);
			inputtingLegality.setIllegalMessage("手机号码格式不正确！");
			return inputtingLegality;
		}
		
		if (registerInfo.getPassword().length() < 6)
		{
			inputtingLegality.setIllegalMessage("密码不能少于6位!");
			return inputtingLegality;
		}
		
		if (registerInfo.getPassword().equals(registerInfo.getRepeatPassword()) == false)
		{
			inputtingLegality.setIllegalMessage("两次密码不一致!");
			return inputtingLegality;
		}
		
		
		if ((CommonValidatorUtil.isPassword(registerInfo.getPassword()) == false) ||
				(CommonValidatorUtil.isPassword(registerInfo.getRepeatPassword()) == false))
		{
			inputtingLegality.setPassed(false);
			inputtingLegality.setIllegalMessage("密码只能是字母和数字！");
			return inputtingLegality;
			
		}
		
		if (registerInfo.getVerificationCode().length() == 0)
		{
			inputtingLegality.setPassed(false);
			inputtingLegality.setIllegalMessage("短信验证码未填写！");
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
	public void requestVerificationCode(String phoneNumber, BusinessCaller businessCaller)
	{
		if (CommonValidatorUtil.isMobile(phoneNumber) == false)
		{
			businessCaller.getData().putString("errorMessage", "手机号码格式不正确！");
			businessCaller.setSuccessful(false);
			businessCaller.callBusiness();
		}
		else
		{
			new Thread()
			{
				@Override
				public void run()
				{
					boolean isSuccessful = randLHttpClient.sendVerificationCode(phoneNumber);
					businessCaller.setSuccessful(isSuccessful);
					businessCaller.callBusiness();
				}
			}.start();
		}
	}
	
	@Override
	public void registerNewUser(RegisterInfo registerInfo, BusinessCaller businessCaller)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				RegisterResponse registerResponse = randLHttpClient
						.registerNewUser(registerInfo.getPhoneNumber(),
								registerInfo.getPassword(),
								registerInfo.getVerificationCode());
				
				businessCaller.setSuccessful(registerResponse.isSuccessful());
				businessCaller.setMessage(registerResponse.getFailedMessage());
				businessCaller.callBusiness();
			}
		}.start();
	}
	
	
}
