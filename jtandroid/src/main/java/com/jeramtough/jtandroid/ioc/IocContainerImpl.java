package com.jeramtough.jtandroid.ioc;


import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * @author 11718
 *         on 2017  November 19 Sunday 19:19.
 */

public final class IocContainerImpl extends Handler implements IocContainer
{
	private static volatile IocContainerImpl iocContainer;
	
	private InjectedObjects injectedObjects;
	private IocContainerListener iocContainerListener;
	private Exception exception;
	
	private Context context;
	
	private IocContainerImpl()
	{
	}
	
	@Override
	public void handleMessage(Message msg)
	{
		switch (msg.what)
		{
			case 1:
				iocContainerListener.onInjectedSuccessfully(injectedObjects);
				break;
			case 2:
				iocContainerListener.onInjectedFailed(exception);
				break;
		}
	}
	
	@Override
	public void injectContext(Context context)
	{
		this.context = context;
	}
	
	@Override
	public void inject(final Class<? extends InjectedObjects> c)
	{
		if (context == null)
		{
			throw new IllegalStateException("No the instance of context is injected");
		}
		else
		{
			if (iocContainerListener != null)
			{
				iocContainerListener.onBeforeInject();
			}
			
			new Thread()
			{
				@Override
				public void run()
				{
					try
					{
						injectedObjects = c.newInstance();
						
						injectedObjects.setContext(context);
						
						injectedObjects.injectComponents();
						injectedObjects.injectServices();
						
						if (iocContainerListener != null)
						{
							IocContainerImpl.this.sendEmptyMessage(1);
						}
					}
					catch (InstantiationException | IllegalAccessException e)
					{
						exception = e;
						if (iocContainerListener != null)
						{
							IocContainerImpl.this.sendEmptyMessage(2);
						}
						e.printStackTrace();
					}
					
				}
				
			}.start();
		}
		
	}
	
	@Override
	public void setIocContainerListener(IocContainerListener iocContainerListener)
	{
		this.iocContainerListener = iocContainerListener;
	}
	
	@Override
	public InjectedObjects getInjectedObjects()
	{
		if (injectedObjects == null)
		{
			throw new IllegalStateException("No objects is injected");
		}
		return injectedObjects;
	}
	
	public static IocContainer getIocContainer()
	{
		if (iocContainer == null)
		{
			synchronized (IocContainerImpl.class)
			{
				if (iocContainer == null)
				{
					iocContainer = new IocContainerImpl();
				}
			}
		}
		return iocContainer;
	}
}
