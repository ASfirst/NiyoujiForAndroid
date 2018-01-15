package com.jeramtough.niyouji.business;

import android.content.Context;
import com.jeramtough.jtandroid.function.NetworkIsAble;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.niyouji.component.app.AppUser;

/**
 * @author 11718
 *         on 2018  January 15 Monday 17:06.
 */
@JtService
public class MainService implements MainBusiness
{
	private AppUser appUser;
	private NetworkIsAble networkIsAble;
	
	
	@IocAutowire
	public MainService(AppUser appUser, NetworkIsAble networkIsAble)
	{
		this.appUser = appUser;
		this.networkIsAble = networkIsAble;
	}
	
	
	@Override
	public boolean userHasLogined()
	{
		return appUser.getHasLogined();
	}
	
	@Override
	public boolean hasTheNetwork(Context context)
	{
		return networkIsAble.isNetworkConnected(context);
	}
}
