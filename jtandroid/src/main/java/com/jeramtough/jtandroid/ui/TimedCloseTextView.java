package com.jeramtough.jtandroid.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author 11718
 *         on 2017  December 03 Sunday 12:23.
 */

public class TimedCloseTextView extends android.support.v7.widget.AppCompatTextView
{
	public TimedCloseTextView(Context context)
	{
		super(context);
	}
	
	public TimedCloseTextView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	public void closeDelayed(long time)
	{
		this.post(new Runnable()
		{
			@Override
			public void run()
			{
				TimedCloseTextView.this.setVisibility(View.VISIBLE);
			}
		});
		this.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				TimedCloseTextView.this.setVisibility(View.GONE);
			}
		}, time);
	}
}
