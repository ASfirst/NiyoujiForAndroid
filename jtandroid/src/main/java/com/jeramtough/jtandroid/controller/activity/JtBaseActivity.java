package com.jeramtough.jtandroid.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.jeramtough.jtandroid.ioc.IocContainer;
import com.jeramtough.jtandroid.ioc.IocContainerImpl;

/**
 * @author 11718
 *         on 2017  November 19 Sunday 20:44.
 */

public abstract class JtBaseActivity extends AppCompatActivity implements View.OnClickListener
{
	
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
