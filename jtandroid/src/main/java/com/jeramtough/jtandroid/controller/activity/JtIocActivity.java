package com.jeramtough.jtandroid.controller.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.jeramtough.jtandroid.ioc.IocContainer;
import com.jeramtough.jtandroid.ioc.JtIocContainer;
import com.jeramtough.jtandroid.ioc.annotation.JtObject;

/**
 * @author 11718
 *         on 2017  December 07 Thursday 16:53.
 */
@JtObject
public abstract class JtIocActivity extends JtBaseActivity
{
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getIocContainer().injectComponentAndService(this, this);
	}
	
	public void injectViewsIntoThis()
	{
		getIocContainer().injectView(this, this);
	}
	
	
	public IocContainer getIocContainer()
	{
		return JtIocContainer.getIocContainer();
	}
	
	//********************************
	
	
}
