package com.jeramtough.niyouji.business;

import android.os.Handler;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.niyouji.bean.landr.InputtingLegality;
import com.jeramtough.niyouji.bean.landr.RegisterInfo;

/**
 * @author 11718
 */
@JtService
public class RegisterService implements RegisterBusiness
{
	@Override
	public InputtingLegality checkInputtingIsLegal(RegisterInfo registerInfo)
	{
		return null;
	}
	
	@Override
	public void requestVerificationCode(String phoneNumber, Handler handler)
	{
	
	}
	
	@Override
	public void registerNewUser(RegisterInfo registerInfo, Handler handler)
	{
	
	}
	
	
}
