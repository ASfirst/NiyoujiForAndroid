package com.jeramtough.niyouji.controller.activity;

import com.jeramtough.jtandroid.controller.activity.JtBaseActivity;
import com.jeramtough.niyouji.component.ioc.MyInjectedObjects;

/**
 * @author 11718
 * on 2017  November 19 Sunday 20:44.
 */

public abstract class BaseActivity extends JtBaseActivity
{
	
	public MyInjectedObjects getMyInjectedObjects()
	{
		return (MyInjectedObjects) getInjectedObjects();
	}
	
}
