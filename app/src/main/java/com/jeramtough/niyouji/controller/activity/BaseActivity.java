package com.jeramtough.niyouji.controller.activity;

import android.support.v7.app.AppCompatActivity;
import com.jeramtough.jtandroid.ioc.IocContainer;
import com.jeramtough.jtandroid.ioc.IocContainerImpl;
import com.jeramtough.niyouji.component.ioc.MyInjectedObjects;

/**
 * @author 11718
 *         on 2017  November 19 Sunday 20:44.
 */

public class BaseActivity extends AppCompatActivity
{
	
	public MyInjectedObjects getMyInjectedObjects()
	{
		IocContainer iocContainer=IocContainerImpl.getIocContainer();
		return (MyInjectedObjects) iocContainer.getInjectedObjects();
	}
}
