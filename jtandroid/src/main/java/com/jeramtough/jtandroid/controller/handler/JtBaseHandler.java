package com.jeramtough.jtandroid.controller.handler;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.view.View;

/**
 * @author 11718
 *         on 2017  November 30 Thursday 15:05.
 */

public class JtBaseHandler extends Handler
{
	private Activity activity;
	
	public JtBaseHandler(Activity activity)
	{
		this.activity = activity;
	}
	
	public <T extends View> T findViewById(@IdRes int viewId)
	{
		return activity.findViewById(viewId);
	}
	
	public Activity getActivity()
	{
		return activity;
	}
	
	public Context getContext()
	{
		return activity;
	}
	
	public void onDestroy()
	{
	
	}
}
