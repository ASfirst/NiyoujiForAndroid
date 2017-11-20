package com.jeramtough.niyouji.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.jeramtough.jtandroid.ioc.IocContainer;
import com.jeramtough.jtandroid.ioc.IocContainerImpl;
import com.jeramtough.niyouji.component.ioc.MyInjectedObjects;

/**
 * @author 11718
 *         on 2017  November 19 Sunday 20:44.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener
{
	
	public MyInjectedObjects getMyInjectedObjects()
	{
		IocContainer iocContainer = IocContainerImpl.getIocContainer();
		return (MyInjectedObjects) iocContainer.getInjectedObjects();
	}
	
	@Override
	@Deprecated
	public void onClick(View v)
	{
		onClick(v, v.getId());
	}
	
	public void onClick(View view, int viewId)
	{
	
	}
}
