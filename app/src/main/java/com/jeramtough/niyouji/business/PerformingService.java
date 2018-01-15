package com.jeramtough.niyouji.business;

import android.content.Context;
import com.alibaba.sdk.android.oss.ServiceException;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.function.NetworkIsAble;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.component.ali.oss.AliOssManager;
import com.jeramtough.niyouji.component.ali.sts.NiyoujiStsManager;
import com.jeramtough.niyouji.component.app.AppUser;

import java.util.concurrent.*;

/**
 * @author 11718
 *         on 2018  January 09 Tuesday 21:36.
 */
@JtService
public class PerformingService implements PerformingBusiness
{
	private Executor executor;
	
	private NiyoujiStsManager niyoujiStsManager;
	private AliOssManager aliOssManager;
	
	private AppUser appUser;
	private NetworkIsAble networkIsAble;
	
	@IocAutowire
	PerformingService(NiyoujiStsManager niyoujiStsManager, AliOssManager aliOssManager,
			AppUser appUser, NetworkIsAble networkIsAble)
	{
		this.niyoujiStsManager = niyoujiStsManager;
		this.aliOssManager = aliOssManager;
		this.appUser = appUser;
		this.networkIsAble = networkIsAble;
		
		executor = new ThreadPoolExecutor(0, 20, 60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());
		
	}
	
	@Override
	public int getTravelnoteId()
	{
		return 0;
	}
	
	@Override
	public String getNicknameOfPerformer()
	{
		return appUser.getNickname();
	}
	
	@Override
	public void uploadImageFile(Context context, String filename, String imageFilePath,
			BusinessCaller businessCaller)
	{
		boolean isHad = checkTheNetword(context, businessCaller);
		if (isHad)
		{
			executor.execute(() ->
			{
				P.debug(filename, imageFilePath);
				setPutRequest(businessCaller);
				aliOssManager.uploadImageFile(filename, imageFilePath);
			});
		}
	}
	
	@Override
	public void uploadVideoFile(Context context, String filename, String videoFilePath,
			BusinessCaller businessCaller)
	{
		boolean isHad = checkTheNetword(context, businessCaller);
		if (isHad)
		{
			executor.execute(() ->
			{
				setPutRequest(businessCaller);
				aliOssManager.uploadVideoFile(filename, videoFilePath);
			});
		}
	}
	
	//*******************************
	private boolean checkTheNetword(Context context, BusinessCaller businessCaller)
	{
		boolean isHad = networkIsAble.isNetworkConnected(context);
		P.debug(isHad);
		if (!isHad)
		{
			businessCaller.getData().putString("exceptionMessage", "没有可用网络连接！");
			businessCaller.getData().putBoolean("hasUploaded", true);
			businessCaller.getData().putBoolean("isSuccessful", false);
			
			businessCaller.callBusiness();
		}
		return isHad;
	}
	
	private void setPutRequest(BusinessCaller businessCaller)
	{
		AssumeRoleResponse.Credentials credentials = null;
		try
		{
			credentials = niyoujiStsManager.getCredentials();
			
		}
		catch (ClientException e)
		{
			e.printStackTrace();
			businessCaller.getData().putBoolean("hasUploaded", true);
			businessCaller.getData().putBoolean("isSuccessful", false);
			String exceptionMessage = e.getMessage();
			businessCaller.getData().putString("exceptionMessage", exceptionMessage);
			businessCaller.callBusiness();
		}
		
		aliOssManager.connect(credentials.getAccessKeyId(), credentials.getAccessKeySecret(),
				credentials.getSecurityToken());
		
		aliOssManager.setPuttingTaskCallback(new AliOssManager.PuttingTaskCallback()
		{
			@Override
			public void onPutProgress(String filename, long currentSize, long totalSize,
					float percent)
			{
				businessCaller.getData().putFloat("percent", percent);
				businessCaller.getData().putBoolean("hasUploaded", false);
				businessCaller.callBusiness();
			}
			
			@Override
			public void onPutSuccess(String filename)
			{
				businessCaller.getData().putBoolean("hasUploaded", true);
				businessCaller.getData().putBoolean("isSuccessful", true);
				businessCaller.callBusiness();
			}
			
			@Override
			public void onPutFailure(String filename,
					com.alibaba.sdk.android.oss.ClientException clientException,
					ServiceException serviceException)
			{
				businessCaller.getData().putBoolean("hasUploaded", true);
				businessCaller.getData().putBoolean("isSuccessful", false);
				String exceptionMessage = clientException.getMessage();
				P.debug(exceptionMessage);
				if (exceptionMessage == null)
				{
					exceptionMessage = serviceException.getRawMessage();
				}
				P.debug(exceptionMessage);
				businessCaller.getData().putString("exceptionMessage", exceptionMessage);
				businessCaller.callBusiness();
			}
		});
	}
	
}
