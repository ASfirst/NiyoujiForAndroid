package com.jeramtough.jtandroid;

import com.jeramtough.jtandroid.ioc.InjectedObjects;
import com.jeramtough.jtandroid.ioc.IocContainer;
import com.jeramtough.jtandroid.ioc.IocContainerImpl;
import com.jeramtough.jtandroid.ioc.IocContainerListener;

/**
 * @author 11718
 *         on 2017  November 19 Sunday 20:06.
 */

public class Test
{
	@org.junit.Test
	public void test()
	{
		IocContainer iocContainer = IocContainerImpl.getIocContainer();
		iocContainer.setIocContainerListener(new IocContainerListener()
		{
			@Override
			public void onInjectedSuccessfully(InjectedObjects injectedObjects)
			{
			}
			
			@Override
			public void onInjectedFailed(Exception e)
			{
			
			}
		});
		
		iocContainer.inject(MyInjectedObjects.class);
		
		
	}
}

