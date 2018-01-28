package com.jeramtough.niyouji.business;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.niyouji.component.app.AppUser;

/**
 * @author 11718
 *         on 2018  January 29 Monday 04:03.
 */
@JtService
public class UserService implements UserBusiness
{
	private AppUser appUser;
	
	@IocAutowire
	public UserService(AppUser appUser)
	{
		this.appUser = appUser;
	}
	
	@Override
	public void userLogout()
	{
		appUser.setHasLogined(false);
	}
}
