package com.jeramtough.jtandroid.function;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author 11718
 *         on 2017  November 20 Monday 21:11.
 */

public class ScreenHandler
{
	private Context context;
	private DisplayMetrics metric;
	
	public ScreenHandler(Context context)
	{
		this.context = context;
		Activity activity = (Activity) context;
		metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
	}
	
	public int getScreenHeight()
	{
		// 屏幕高度（像素）
		int height = metric.heightPixels;
		return height;
	}
	
	public int getScreenWidth()
	{
		int width = metric.widthPixels;
		return width;
	}
	
}
