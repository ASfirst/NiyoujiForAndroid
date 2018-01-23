package com.jeramtough.jtandroid.ui;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * @author 11718
 *         on 2017  November 26 Sunday 20:42.
 */

public class JtVideoView extends VideoView
{
	private boolean isRepeated = false;
	private boolean isFullScreen = true;
	
	public JtVideoView(Context context)
	{
		super(context);
		initResources();
	}
	
	public JtVideoView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initResources();
	}
	
	public JtVideoView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		initResources();
	}
	
	protected void initResources()
	{
		this.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mp)
			{
				if (isRepeated)
				{
					start();
				}
			}
		});
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		if (isFullScreen)
		{
			int width = getDefaultSize(0, widthMeasureSpec);
			int height = getDefaultSize(0, heightMeasureSpec);
			setMeasuredDimension(width, height);
		}
		else
		{
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
	
	public boolean isRepeated()
	{
		return isRepeated;
	}
	
	public void setRepeated(boolean repeated)
	{
		isRepeated = repeated;
	}
	
	public boolean isFullScreen()
	{
		return isFullScreen;
	}
	
	public void setFullScreen(boolean fullScreen)
	{
		isFullScreen = fullScreen;
	}
	
	public void stopAndClear()
	{
		if (isPlaying())
		{
			this.stopPlayback();
			this.destroyDrawingCache();
		}
	}
}
