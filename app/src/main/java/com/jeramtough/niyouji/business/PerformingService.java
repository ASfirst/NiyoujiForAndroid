package com.jeramtough.niyouji.business;

import android.content.Context;
import com.alibaba.sdk.android.oss.ServiceException;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.jtandroid.jtlog2.P;
import com.jeramtough.niyouji.component.ali.oss.AliOssManager;
import com.jeramtough.niyouji.component.ali.sts.NiyoujiStsManager;

import java.io.File;
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
	
	@IocAutowire
	PerformingService(NiyoujiStsManager niyoujiStsManager, AliOssManager aliOssManager)
	{
		this.niyoujiStsManager = niyoujiStsManager;
		this.aliOssManager = aliOssManager;
		
		executor = new ThreadPoolExecutor(0, 20, 60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());
		
		executor.execute(() ->
		{
			try
			{
				AssumeRoleResponse.Credentials credentials =
						niyoujiStsManager.getCredentials();
				
				aliOssManager.connect(credentials.getAccessKeyId(),
						credentials.getAccessKeySecret(), credentials.getSecurityToken());
				
				aliOssManager.setPuttingTaskCallback(new AliOssManager.PuttingTaskCallback()
				{
					@Override
					public void onPutProgress(String filename, long currentSize,
							long totalSize, float percent)
					{
					}
					
					@Override
					public void onPutSuccess(String filename)
					{
						P.arrive();
					}
					
					@Override
					public void onPutFailure(String filename,
							com.alibaba.sdk.android.oss.ClientException clientException,
							ServiceException serviceException)
					{
					}
				});
				
			}
			catch (ClientException e)
			{
				e.printStackTrace();
			}
		});
	}
	
	@Override
	public int getTravelnoteId()
	{
		return 0;
	}
	
	@Override
	public void uploadImageFile(Context context, String filename, String imageFilePath,
			BusinessCaller businessCaller)
	{
		executor.execute(() ->
		{
			/*try
			{
				AssumeRoleResponse.Credentials credentials =
						niyoujiStsManager.getCredentials();
				
				aliOssManager.connect(credentials.getAccessKeyId(),
						credentials.getAccessKeySecret(), credentials.getSecurityToken());
				
				aliOssManager.setPuttingTaskCallback(new AliOssManager.PuttingTaskCallback()
				{
					@Override
					public void onPutProgress(String filename, long currentSize,
							long totalSize, float percent)
					{
					}
					
					@Override
					public void onPutSuccess(String filename)
					{
						P.arrive();
					}
					
					@Override
					public void onPutFailure(String filename,
							com.alibaba.sdk.android.oss.ClientException clientException,
							ServiceException serviceException)
					{
					}
				});
				
			}
			catch (ClientException e)
			{
				e.printStackTrace();
			}*/
			//			setPutRequest(businessCaller);
			P.debug(filename, imageFilePath);
			/*aliOssManager.uploadImageFile("img_0_139800184.jpg",
					"/storage/emulated/0/niyouji/images/JPEG_1515598406791.jpg");*/
			/*aliOssManager.uploadImageFile("b.jpg",
					"/storage/emulated/0/niyouji/images/JPEG_1515601175838.jpg");*/
			aliOssManager.uploadImageFile(filename, imageFilePath);
			/*aliOssManager.uploadImageFile("img_0_150645070.jpg",
					"/storage/emulated/0/niyouji/images/JPEG_1515599910848.jpg");*/
		});
	}
	
	@Override
	public void uploadVideoFile(String filename, String videoFilePath,
			BusinessCaller businessCaller)
	{
		executor.execute(() ->
		{
			setPutRequest(businessCaller);
			aliOssManager.uploadVideoFile(filename, videoFilePath);
		});
	}
	
	//*******************************
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
				if (exceptionMessage == null)
				{
					exceptionMessage = serviceException.getRawMessage();
				}
				businessCaller.getData().putString("exceptionMessage", exceptionMessage);
				businessCaller.callBusiness();
			}
		});
	}
	
}
