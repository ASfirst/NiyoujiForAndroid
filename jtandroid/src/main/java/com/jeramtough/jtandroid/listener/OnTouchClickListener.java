package com.jeramtough.jtandroid.listener;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author 11718
 *         on 2018  February 06 Tuesday 19:41.
 */

public abstract class OnTouchClickListener implements View.OnTouchListener
{
	private long downTime;
	
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			downTime = System.currentTimeMillis();
		}
		else if (event.getAction() == MotionEvent.ACTION_UP)
		{
			long upTime = System.currentTimeMillis();
			long intervalTime = upTime - downTime;
			if (intervalTime < 100)
			{
				onClick(v);
			}
		}
		return false;
	}
	
	public abstract void onClick(View view);
}
