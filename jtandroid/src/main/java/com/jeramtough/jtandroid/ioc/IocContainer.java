package com.jeramtough.jtandroid.ioc;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by 11718
 * on 2017  November 19 Sunday 17:20.
 * @author 11718
 */

public interface IocContainer extends Serializable
{
	void injectContext(Context context);
	
	void inject(Class<? extends InjectedObjects> c);
	
	void setIocContainerListener(IocContainerListener iocContainerListener);
	
	InjectedObjects getInjectedObjects();
}
