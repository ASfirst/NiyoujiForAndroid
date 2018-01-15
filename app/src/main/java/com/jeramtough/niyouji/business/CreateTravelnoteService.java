package com.jeramtough.niyouji.business;

import android.app.Activity;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.niyouji.component.ali.oss.AliOssManager;
import com.jeramtough.niyouji.component.ali.sts.NiyoujiStsManager;
import com.jeramtough.niyouji.component.app.AppUser;

/**
 * @author 11718
 *         on 2018  January 15 Monday 16:33.
 */
@JtService
public class CreateTravelnoteService implements CreateTravelnoteBusiness
{
	private AppUser appUser;
	private NiyoujiStsManager niyoujiStsManager;
	private AliOssManager aliOssManager;
	
	@IocAutowire
	public CreateTravelnoteService(AppUser appUser, NiyoujiStsManager niyoujiStsManager,
			AliOssManager aliOssManager)
	{
		this.appUser = appUser;
		this.niyoujiStsManager = niyoujiStsManager;
		this.aliOssManager = aliOssManager;
	}
	
	@Override
	public void createTravelnote(String travelnoteTitle, String coverResourcePath,
			BusinessCaller businessCaller)
	{
		//第一步，先与服务器连接，然后上传封面，然后发送创建游记的命令
	}
}
