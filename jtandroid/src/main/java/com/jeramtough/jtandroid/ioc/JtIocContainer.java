package com.jeramtough.jtandroid.ioc;

import android.content.Context;
import com.jeramtough.jtandroid.jtlog2.P;

/**
 * @author 11718
 *         on 2017  December 05 Tuesday 22:42.
 */

public class JtIocContainer implements IocContainer
{
	private static volatile IocContainer iocContainer;
	
	
	private InjectedObjects injectedObjects;
	
	private JtIocContainer()
	{
	
	}
	
	@Override
	public InjectedObjects getInjectedObjects(Context context)
	{
		if (injectedObjects == null)
		{
			IocConfig iocConfig = new IocConfig(context.getResources());
			try
			{
				P.debug(iocConfig.getInjectedObjectsClassPath());
				injectedObjects = (InjectedObjects) Class
						.forName("com.jeramtough.niyouji.component.ioc.MyInjectedObjects")
						.newInstance();
				injectedObjects.setContext(context);
			}
			catch (ClassNotFoundException | IllegalAccessException | InstantiationException e)
			{
				e.printStackTrace();
			}
		}
		return injectedObjects;
	}
	
	public static IocContainer getIocContainer()
	{
		if (iocContainer == null)
		{
			synchronized (JtIocContainer.class)
			{
				if (iocContainer == null)
				{
					iocContainer = new JtIocContainer();
				}
			}
		}
		return iocContainer;
	}
	
	//******************************
	
}
