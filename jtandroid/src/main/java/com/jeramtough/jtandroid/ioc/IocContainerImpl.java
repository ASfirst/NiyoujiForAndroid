package com.jeramtough.jtandroid.ioc;

/**
 * @author 11718
 *         on 2017  November 19 Sunday 19:19.
 */

public final class IocContainerImpl implements IocContainer
{
	private InjectedObjects injectedObjects;
	private IocContainerListener iocContainerListener;
	
	private static volatile IocContainerImpl iocContainer;
	
	private IocContainerImpl()
	{
	
	}
	
	@Override
	public void inject(final Class<? extends InjectedObjects> c)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					injectedObjects = c.newInstance();
					injectedObjects.injectComponents();
					injectedObjects.injectServices();
					
					if (iocContainerListener != null)
					{
						iocContainerListener.onInjectedSuccessfully(injectedObjects);
					}
				}
				catch (InstantiationException | IllegalAccessException e)
				{
					if (iocContainerListener != null)
					{
						iocContainerListener.onInjectedFailed(e);
					}
					e.printStackTrace();
				}
				
			}
			
		}.start();
		
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
