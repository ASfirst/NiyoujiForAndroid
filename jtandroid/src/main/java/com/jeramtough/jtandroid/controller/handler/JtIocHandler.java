package com.jeramtough.jtandroid.controller.handler;

import android.app.Activity;
import com.jeramtough.jtandroid.ioc.iocimpl.IocContainer;
import com.jeramtough.jtandroid.ioc.iocimpl.JtIocContainer;
import com.jeramtough.jtandroid.ioc.annotation.JtController;

/**
 * @author 11718
 *         on 2017  December 07 Thursday 17:24.
 */
@JtController
public class JtIocHandler extends JtBaseHandler
{
	public JtIocHandler(Activity activity)
	{
		super(activity);
		getIocContainer().injectCSV(activity, this);
	}
	
	public IocContainer getIocContainer()
	{
		return JtIocContainer.getIocContainer();
	}
}
