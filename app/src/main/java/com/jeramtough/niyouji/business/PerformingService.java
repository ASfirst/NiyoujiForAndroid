package com.jeramtough.niyouji.business;

import android.net.Credentials;
import android.os.Handler;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.niyouji.component.ali.oss.AliOssManager;
import com.jeramtough.niyouji.component.ali.sts.NiyoujiStsManager;

/**
 * @author 11718
 *         on 2018  January 09 Tuesday 21:36.
 */
@JtService
public class PerformingService implements PerformingBusiness
{
	private NiyoujiStsManager niyoujiStsManager;
	private AliOssManager aliOssManager;
	
	@IocAutowire
	PerformingService(NiyoujiStsManager niyoujiStsManager, AliOssManager aliOssManager)
	{
		this.niyoujiStsManager = niyoujiStsManager;
		this.aliOssManager = aliOssManager;
		
		
	}
	
	@Override
	public void uploadImageFile(String filename, String imageFilePath, Handler handler)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					AssumeRoleResponse.Credentials credentials =
							niyoujiStsManager.getCredentials();
					aliOssManager.connect(credentials.getAccessKeyId(),
							credentials.getAccessKeySecret(), credentials.getSecurityToken());
					aliOssManager.updateImageFile(filename, imageFilePath);
				}
				catch (ClientException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
}
