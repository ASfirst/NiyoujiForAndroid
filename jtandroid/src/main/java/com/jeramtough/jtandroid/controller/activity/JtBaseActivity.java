package com.jeramtough.jtandroid.controller.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author 11718
 *         on 2017  November 19 Sunday 20:44.
 */

public abstract class JtBaseActivity extends AppCompatActivity implements View.OnClickListener
{
	private ActivityHandler activityHandler;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		activityHandler = new ActivityHandler();
	}
	
	@Override
	@Deprecated
	public void onClick(View v)
	{
		onClick(v, v.getId());
	}
	
	public void onClick(View view, int viewId)
	{
	
	}
	
	public void handleActivityMessage(Message message)
	{
	}
	
	
	public Handler getActivityHandler()
	{
		return activityHandler;
	}
	
	//{{{{{{{{{{{}}}}}}}}}}}}}}}}}}}}}}
	private class ActivityHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			handleActivityMessage(msg);
		}
	}
}
