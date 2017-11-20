package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.jeramtough.jtandroid.ioc.InjectedObjects;
import com.jeramtough.jtandroid.ioc.IocContainer;
import com.jeramtough.jtandroid.ioc.IocContainerImpl;
import com.jeramtough.jtandroid.ioc.IocContainerListener;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.ioc.MyInjectedObjects;

/**
 * @author 11718
 */
public class LaunchActivity extends AppCompatActivity implements IocContainerListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		
		IocContainer iocContainer = IocContainerImpl.getIocContainer();
		iocContainer.setIocContainerListener(this);
		iocContainer.inject(MyInjectedObjects.class);
	}
	
	@Override
	public void onInjectedSuccessfully(InjectedObjects injectedObjects)
	{
		IntentUtil.toTheOtherActivity(this, MainActivity.class);
	}
	
	@Override
	public void onInjectedFailed(Exception e)
	{
	
	}
}
