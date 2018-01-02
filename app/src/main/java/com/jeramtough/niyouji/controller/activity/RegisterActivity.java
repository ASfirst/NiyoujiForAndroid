package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.business.RegisterBusiness;
import com.jeramtough.niyouji.business.RegisterService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 11718
 */
public class RegisterActivity extends AppBaseActivity
{
	public static final int BUSINESS_CODE_SENT_VERIFICATION_CODE_SUCCESSFULLY = 0;
	
	private AppCompatImageView viewBack;
	private TimedCloseTextView timedCloseTextViewShowMessage;
	private EditText editAccount;
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
		editAccount = findViewById(R.id.edit_account);
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
				afterSentVerificationCode();
				break;
			case R.id.button_register_user:
				break;
		}
	}
	
	@Override
	public void handleActivityMessage(Message message)
	{
		switch (message.what)
		{
			case BUSINESS_CODE_SENT_VERIFICATION_CODE_SUCCESSFULLY:
				timedCloseTextViewShowMessage.setNiceMessage("发送成功");
				break;
		}
	}
	
	//***************************************
	private void afterSentVerificationCode()
	{
		final int[] count = {30};
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
						textViewSendingCountdown.setText("(30)s");
					}
				});
				
			}
		};
		new Timer().schedule(timerTask, 1000, 1000);
	}
}
