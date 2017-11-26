package com.jeramtough.jtandroid.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * @author 11718
 *         on 2017  November 26 Sunday 20:42.
 */

public class FullScreenVideoView extends VideoView
{
	public FullScreenVideoView(Context context)
	{
		super(context);
	}
	
	public FullScreenVideoView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	public FullScreenVideoView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int width = getDefaultSize(0, widthMeasureSpec);
		int height = getDefaultSize(0, heightMeasureSpec);
		setMeasuredDimension(width, height);
	}
}
