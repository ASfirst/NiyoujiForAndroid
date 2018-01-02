package com.jeramtough.niyouji.business;

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
	 * 请求发送手机验证码
	 */
	void requestVerificationCode(String phoneNumber, android.os.Handler handler);
	
	/**
	 * 注册一名新用户到系统
	 */
	void registerNewUser(RegisterInfo registerInfo, android.os.Handler handler);
}
