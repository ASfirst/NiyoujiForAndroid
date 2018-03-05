package com.jeramtough.jtandroid.function;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.widget.Toast;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

/**
 * @author 11718
 *         on 2018  February 21 Wednesday 11:04.
 */
@JtComponent
public class DblclickExit
{
	private Context context;
	
	private long minIntervalTime = 2200;
	private boolean isFirstClick = true;
	private long firstClickTime;
	
	@IocAutowire
	public DblclickExit(Context context)
	{
		this.context = context;
	}
	
	public void clickExit()
	{
		if (isFirstClick)
		{
			isFirstClick = false;
			firstClickTime = System.currentTimeMillis();
			Toast.makeText(context, "再次点击返回键退出", Toast.LENGTH_SHORT).show();
		}
		else
		{
			long intervalTime = System.currentTimeMillis() - firstClickTime;
			
			if (intervalTime <= minIntervalTime)
			{
				try
				{
					Activity activity = (Activity) context;
					activity.finish();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				System.exit(0);
			}
			
			isFirstClick = true;
		}
	}
	
	/**
	 * @param minIntervalTime millisecond
	 */
	public void setMinIntervalTime(long minIntervalTime)
	{
		this.minIntervalTime = minIntervalTime;
	}
}
