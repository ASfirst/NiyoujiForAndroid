package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.*;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.landr.InputtingLegality;
import com.jeramtough.niyouji.bean.landr.LoginInfo;
import com.jeramtough.niyouji.business.LoginBusiness;
import com.jeramtough.niyouji.business.LoginService;

/**
 * A setPrimaryInfoOfUser screen that offers setPrimaryInfoOfUser via phone number/password.
 *
 * @author 11718
 */
public class LoginActivity extends AppBaseActivity
{
	public static final int ACTIVITY_RESULTE_CODE_LOGIN = 0;
	
	public static final int BUSINESS_CODE_LOGIN_SUCCESSFULLY = 0;
	public static final int BUSINESS_CODE_LOGIN_FAILED = 1;
	
	public static final int ACTIVITY_REQUEST_CODE_REGISTER = 0;
	
	private AppCompatImageView viewBack;
	private EditText editPhoneNumber;
	private EditText editPassword;
	private Button buttonLogin;
	private TextView textViewRegister;
	private TextView textViewForgetPassword;
	private TimedCloseTextView timedCloseTextViewShowMessage;
	
	@InjectService(service = LoginService.class)
	private LoginBusiness loginBusiness;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		viewBack = findViewById(R.id.view_back);
		editPhoneNumber = findViewById(R.id.edit_phone_number);
		editPassword = findViewById(R.id.edit_password);
		buttonLogin = findViewById(R.id.button_login);
		textViewRegister = findViewById(R.id.textView_register);
		textViewForgetPassword = findViewById(R.id.textView_forget_password);
		timedCloseTextViewShowMessage = findViewById(R.id.timedCloseTextView_show_message);
		
		viewBack.setOnClickListener(this);
		buttonLogin.setOnClickListener(this);
		textViewRegister.setOnClickListener(this);
		textViewForgetPassword.setOnClickListener(this);
		
		initResources();
	}
	
	protected void initResources()
	{
		LoginInfo loginInfo = loginBusiness.getHasRememberLoginInfo();
		editPhoneNumber.setText(loginInfo.getPhoneNumber());
		editPassword.setText(loginInfo.getPassword());
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.view_back:
				this.finish();
				break;
			case R.id.button_login:
				LoginInfo loginInfo = new LoginInfo(editPhoneNumber.getText().toString(),
						editPassword.getText().toString());
				InputtingLegality inputtingLegality =
						loginBusiness.checkInputtingIsLegal(loginInfo);
				if (inputtingLegality.isPassed())
				{
					loginBusiness.login(loginInfo, getActivityUiHandler());
					loginBusiness.rememberLoginInfo(loginInfo);
				}
				else
				{
					timedCloseTextViewShowMessage
							.setErrorMessage(inputtingLegality.getIllegalMessage());
					timedCloseTextViewShowMessage.closeDelayed(3000);
				}
				break;
			case R.id.textView_register:
				IntentUtil.toTheOtherActivity(this, RegisterActivity.class,
						ACTIVITY_REQUEST_CODE_REGISTER);
				break;
			case R.id.textView_forget_password:
				break;
		}
	}
	
	@Override
	public void handleActivityMessage(Message message)
	{
		switch (message.what)
		{
			case BUSINESS_CODE_LOGIN_SUCCESSFULLY:
				Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
				setResult(ACTIVITY_RESULTE_CODE_LOGIN, getIntent());
				this.finish();
				break;
			case BUSINESS_CODE_LOGIN_FAILED:
				break;
		}
	}
}

