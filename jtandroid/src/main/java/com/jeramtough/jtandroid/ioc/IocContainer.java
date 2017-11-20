package com.jeramtough.jtandroid.ioc;

/**
 * Created by 11718
 * on 2017  November 19 Sunday 17:20.
 * @author 11718
 */

public interface IocContainer
{
	void inject(Class<? extends InjectedObjects> c);
	
	void setIocContainerListener(IocContainerListener iocContainerListener);
	
	InjectedObjects getInjectedObjects();
}
