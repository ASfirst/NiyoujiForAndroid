package com.jeramtough.niyouji.business;


import android.os.Handler;
import com.jeramtough.niyouji.bean.landr.InputtingLegality;
import com.jeramtough.niyouji.bean.landr.LoginInfo;


/**
 * @author 11718
 */
public interface LoginBusiness
{
	
	/**
	 * 验证输入的合法性
	 *
	 * @return 如果合法返回真
	 */
	InputtingLegality checkInputtingIsLegal(LoginInfo loginInfo);
	
	/**
	 * 得到已经记录的登录信息
	 *
	 * @return 登录信息Bean类
	 */
	LoginInfo getHasRememberLoginInfo();
	
	/**
	 * 记住登录信息
	 *
	 * @param loginInfo 登陆信息bean
	 */
	void rememberLoginInfo(LoginInfo loginInfo);
	
	/**
	 * 登录
	 */
	void login(LoginInfo loginInfo, Handler handler);
	
	
}
