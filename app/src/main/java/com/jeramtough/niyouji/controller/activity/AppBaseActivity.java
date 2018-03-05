package com.jeramtough.niyouji.controller.activity;

import android.content.pm.ActivityInfo;
import com.jeramtough.jtandroid.controller.activity.JtIocActivity;

/**
 * @author 11718
 *         on 2017  November 19 Sunday 20:44.
 */

public abstract class AppBaseActivity extends JtIocActivity
{
	@Override
	protected void onResume()
	{
		super.onResume();
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
}
