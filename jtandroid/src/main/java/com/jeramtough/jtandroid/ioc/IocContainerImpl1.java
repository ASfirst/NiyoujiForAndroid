package com.jeramtough.jtandroid.ioc;

import android.content.Context;

/**
 * @author 11718
 *         on 2017  December 03 Sunday 11:32.
 */

class IocContainerImpl1 implements IocContainer
{
	public static  volatile IocContainer iocContainer;
	
	@Override
	public void injectContext(Context context)
	{
	
	}
	
	@Override
	public void inject(Class<? extends InjectedObjects> c)
	{
	
	}
	
	@Override
	public void setIocContainerListener(IocContainerListener iocContainerListener)
	{
	
	}
	
	@Override
	public InjectedObjects getInjectedObjects()
	{
		return null;
	}
}
