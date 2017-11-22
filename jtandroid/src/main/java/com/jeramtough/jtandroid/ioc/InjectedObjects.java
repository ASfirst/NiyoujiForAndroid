package com.jeramtough.jtandroid.ioc;

import android.content.Context;

/**
 * Created by 11718
 * on 2017  November 19 Sunday 18:22.
 */

public abstract class InjectedObjects
{
	private Context context;
	
	public void setContext(Context context)
	{
		this.context=context;
	}
	
	public Context getContext()
	{
		return context;
	}
	
	public abstract void injectComponents();
	
	public abstract void injectServices();
	
}
