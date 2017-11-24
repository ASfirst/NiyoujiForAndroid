package com.jeramtough.jtandroid.ioc;

/**
 * Created by 11718
 * on 2017  November 19 Sunday 18:53.
 *
 * @author 11718
 */

public interface IocContainerListener
{
	/**
	 * call before objects will is injected , this thread<br/>
	 * isn't main thread.
	 */
	void onBeforeInject();
	
	/**
	 * call when all objects is injected successfully
	 * @param injectedObjects instance of injected objects
	 */
	void onInjectedSuccessfully(InjectedObjects injectedObjects);
	
	/**
	 * call if injecting failed
	 * @param e exception for reason
	 */
	void onInjectedFailed(Exception e);
}
