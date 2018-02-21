package com.jeramtough.niyouji.controller.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.controller.activity.LoginActivity;

/**
 * @author 11718
 *         on 2018  January 15 Monday 17:38.
 */

public class GoToLoginDialog extends Dialog implements View.OnClickListener
{
	private Button buttonGoToLogin;
	private Activity activity;
	private boolean isFinishActivity = false;
	
	
	public GoToLoginDialog(@NonNull Activity activity)
	{
		super(activity);
		this.activity = activity;
		this.setContentView(R.layout.dialog_go_to_the_login);
		buttonGoToLogin = findViewById(R.id.button_go_to_login);
		
		buttonGoToLogin.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.button_go_to_login:
				IntentUtil.toTheOtherActivity(activity, LoginActivity.class);
				this.cancel();
				if (isFinishActivity)
				{
					activity.finish();
				}
				break;
		}
	}
	
	public boolean isFinishActivity()
	{
		return isFinishActivity;
	}
	
	public void setFinishActivity(boolean finishActivity)
	{
		isFinishActivity = finishActivity;
	}
}
