package com.jeramtough.niyouji.business;

import android.os.Handler;
import android.os.Message;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.jtandroid.util.CommonValidatorUtil;
import com.jeramtough.niyouji.bean.landr.InputtingLegality;
import com.jeramtough.niyouji.bean.landr.RegisterInfo;
import com.jeramtough.niyouji.controller.activity.RegisterActivity;

/**
 * @author 11718
 */
@JtService
public class RegisterService implements RegisterBusiness
{
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
		inputtingLegality.setPassed(true);
		return inputtingLegality;
	}
	
	@Override
	public void requestVerificationCode(String phoneNumber, Handler handler)
	{
		if (CommonValidatorUtil.isMobile(phoneNumber) == false)
		{
			Message message = new Message();
			message.getData().putString("errorMessage", "手机号码格式不正确！");
			message.what = RegisterActivity.BUSINESS_CODE_SENT_VERIFICATION_CODE_FAILED;
			handler.sendMessage(message);
		}
		else
		{
			handler.sendEmptyMessage(
					RegisterActivity.BUSINESS_CODE_SENT_VERIFICATION_CODE_SUCCESSFULLY);
		}
	}
	
	@Override
	public void registerNewUser(RegisterInfo registerInfo, Handler handler)
	{
		handler.sendEmptyMessage(RegisterActivity.BUSINESS_CODE_REGISTERED_SUCCESSFULLY);
	}
	
	
}
