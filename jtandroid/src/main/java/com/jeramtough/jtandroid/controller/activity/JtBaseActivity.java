package com.jeramtough.jtandroid.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.jeramtough.jtandroid.ioc.InjectedObjects;
import com.jeramtough.jtandroid.ioc.IocContainer;
import com.jeramtough.jtandroid.ioc.IocContainerImpl;

/**
 * @author 11718
 *         on 2017  November 19 Sunday 20:44.
 */

public abstract class JtBaseActivity extends AppCompatActivity implements View.OnClickListener
{
	private IocContainer iocContainer;
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putSerializable("iocContainer",iocContainer);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		iocContainer= (IocContainer) savedInstanceState.getSerializable("iocContainer");
	}
	
	@Override
	@Deprecated
	public void onClick(View v)
	{
		onClick(v, v.getId());
	}
	
	public InjectedObjects getInjectedObjects()
	{
		if (iocContainer==null)
		{
			iocContainer = IocContainerImpl.getIocContainer();
		}
		return iocContainer.getInjectedObjects();
	}
	
	public void onClick(View view, int viewId)
	{
	
	}
}
