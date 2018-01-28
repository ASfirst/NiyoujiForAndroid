package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.landr.InputtingLegality;
import com.jeramtough.niyouji.bean.landr.RegisterInfo;
import com.jeramtough.niyouji.business.RegisterBusiness;
import com.jeramtough.niyouji.business.RegisterService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 11718
 */
public class RegisterActivity extends AppBaseActivity
{
	public static final int BUSINESS_CODE_SENT_VERIFICATION_CODE = 0;
	public static final int BUSINESS_CODE_REGISTERED = 1;
	
	public static final int ACTIVITY_SESULT_CODE_REGISTER = 0;
	
	private AppCompatImageView viewBack;
	private TimedCloseTextView timedCloseTextViewShowMessage;
	private EditText editPhoneNumber;
	private EditText editPassword;
	private EditText editRepeatPassword;
	private EditText editVerificationCode;
	private Button buttonRequestVerificationCode;
	private TextView textViewSendingCountdown;
	private Button buttonRegisterUser;
	
	@InjectService(service = RegisterService.class)
	private RegisterBusiness registerBusiness;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		viewBack = findViewById(R.id.view_back);
		timedCloseTextViewShowMessage = findViewById(R.id.timedCloseTextView_show_message);
		editPhoneNumber = findViewById(R.id.edit_phone_number);
		editPassword = findViewById(R.id.edit_password);
		editRepeatPassword = findViewById(R.id.edit_repeat_password);
		editVerificationCode = findViewById(R.id.edit_verification_code);
		buttonRequestVerificationCode = findViewById(R.id.button_request_verification_code);
		textViewSendingCountdown = findViewById(R.id.textView_sending_countdown);
		buttonRegisterUser = findViewById(R.id.button_register_user);
		
		textViewSendingCountdown.setVisibility(View.INVISIBLE);
		
		viewBack.setOnClickListener(this);
		buttonRegisterUser.setOnClickListener(this);
		buttonRequestVerificationCode.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.view_back:
				this.finish();
				break;
			case R.id.button_request_verification_code:
				if (registerBusiness.checkNetwork(this))
				{
					registerBusiness
							.requestVerificationCode(editPhoneNumber.getText().toString(),
									new BusinessCaller(getActivityHandler(),
											BUSINESS_CODE_SENT_VERIFICATION_CODE));
				}
				else
				{
					timedCloseTextViewShowMessage.setErrorMessage("没有可用网络");
					timedCloseTextViewShowMessage.closeDelayed(3000);
				}
				break;
			case R.id.button_register_user:
				if (registerBusiness.checkNetwork(this))
				{
					RegisterInfo registerInfo = new RegisterInfo();
					registerInfo.setPhoneNumber(editPhoneNumber.getText().toString());
					registerInfo.setPassword(editPassword.getText().toString());
					registerInfo.setRepeatPassword(editRepeatPassword.getText().toString());
					registerInfo.setVerificationCode(editVerificationCode.getText().toString());
					InputtingLegality inputtingLegality =
							registerBusiness.checkInputtingIsLegal(registerInfo);
					if (inputtingLegality.isPassed())
					{
						registerInfo.setVerificationCode(
								editVerificationCode.getText().toString());
						registerBusiness.registerNewUser(registerInfo,
								new BusinessCaller(getActivityHandler(),
										BUSINESS_CODE_REGISTERED));
					}
					else
					{
						timedCloseTextViewShowMessage
								.setErrorMessage(inputtingLegality.getIllegalMessage());
						timedCloseTextViewShowMessage.closeDelayed(3000);
					}
				}
				else
				{
					timedCloseTextViewShowMessage.setErrorMessage("没有可用网络");
					timedCloseTextViewShowMessage.closeDelayed(3000);
				}
				break;
		}
	}
	
	@Override
	public void handleActivityMessage(Message message)
	{
		switch (message.what)
		{
			case BUSINESS_CODE_SENT_VERIFICATION_CODE:
				if (message.getData().getBoolean(BusinessCaller.IS_SUCCESSFUL))
				{
					timedCloseTextViewShowMessage.setNiceMessage("发送成功");
					timedCloseTextViewShowMessage.closeDelayed(3000);
					afterSentVerificationCode();
				}
				else
				{
					String errorMessage = message.getData().getString("errorMessage");
					timedCloseTextViewShowMessage.setErrorMessage("发送失败!" + errorMessage);
					timedCloseTextViewShowMessage.closeDelayed(3000);
				}
				break;
			case BUSINESS_CODE_REGISTERED:
				if (message.getData().getBoolean(BusinessCaller.IS_SUCCESSFUL))
				{
					Toast.makeText(this, "注册成功~", Toast.LENGTH_SHORT).show();
					setResult(ACTIVITY_SESULT_CODE_REGISTER, getIntent());
					getIntent().putExtra("phoneNumber", editPhoneNumber.getText().toString());
					getIntent().putExtra("password", editPassword.getText().toString());
					this.finish();
				}
				else
				{
					String failedMessage=message.getData().getString(BusinessCaller.MESSAGE);
					timedCloseTextViewShowMessage.setErrorMessage(failedMessage);
					timedCloseTextViewShowMessage.closeDelayed(3000);
				}
				break;
		}
	}
	
	//***************************************
	private void afterSentVerificationCode()
	{
		final int[] count = {60};
		textViewSendingCountdown.setVisibility(View.VISIBLE);
		buttonRequestVerificationCode.setVisibility(View.INVISIBLE);
		TimerTask timerTask = new TimerTask()
		{
			@Override
			public void run()
			{
				runOnUiThread(() ->
				{
					count[0]--;
					textViewSendingCountdown.setText("(" + count[0] + ")s");
					if (count[0] == 0)
					{
						this.cancel();
						textViewSendingCountdown.setVisibility(View.INVISIBLE);
						buttonRequestVerificationCode.setVisibility(View.VISIBLE);
						textViewSendingCountdown.setText("(60)s");
					}
				});
				
			}
		};
		new Timer().schedule(timerTask, 1000, 1000);
	}
}
