package com.jeramtough.niyouji.component.ioc;

import com.jeramtough.jtandroid.function.PermissionManager;
import com.jeramtough.jtandroid.ioc.InjectedObjects;
import com.jeramtough.niyouji.business.LaunchBusiness;
import com.jeramtough.niyouji.business.LaunchService;

/**
 * @author 11718
 *         on 2017  November 19 Sunday 20:45.
 */

public class MyInjectedObjects extends InjectedObjects
{
	//Components
	private PermissionManager permissionManager;
	
	//Businesses
	private LaunchBusiness launchBusiness;
	
	@Override
	public void injectComponents()
	{
		permissionManager = new PermissionManager();
	}
	
	@Override
	public void injectServices()
	{
		launchBusiness=new LaunchService(permissionManager);
	}
	
	
	//GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
	
	public LaunchBusiness getLaunchBusiness()
	{
		return launchBusiness;
	}
}
