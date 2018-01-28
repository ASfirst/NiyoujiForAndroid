package com.jeramtough.niyouji.business;

import android.content.Context;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.niyouji.bean.landr.InputtingLegality;
import com.jeramtough.niyouji.bean.landr.RegisterInfo;

/**
 * @author 11718
 */
public interface RegisterBusiness
{
	
	/**
	 * 检查输入信息的合法性
	 */
	InputtingLegality checkInputtingIsLegal(RegisterInfo registerInfo);
	
	/**
	 * 检查网络
	 */
	boolean checkNetwork(Context context);
	
	/**
	 * 请求发送手机验证码
	 */
	void requestVerificationCode(String phoneNumber, BusinessCaller businessCaller);
	
	/**
	 * 注册一名新用户到系统
	 */
	void registerNewUser(RegisterInfo registerInfo, BusinessCaller businessCaller);
}
