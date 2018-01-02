package com.jeramtough.jtandroid.controller.handler;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.view.View;

/**
 * @author 11718
 *         on 2017  November 30 Thursday 15:05.
 */

public class JtBaseHandler extends Handler implements View.OnClickListener
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
	
	@Override
	@Deprecated
	public void onClick(View v)
	{
		onClick(v, v.getId());
	}
	
	public void onClick(View view, int viewId)
	{
	
	}
}
