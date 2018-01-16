package com.jeramtough.niyouji.component.ali.oss;

import android.content.Context;
import com.alibaba.sdk.android.oss.*;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtlog3.WithLogger;

/**
 * @author 11718
 *         on 2018  January 09 Tuesday 21:50.
 */
@JtComponent
public class AliOssManager implements WithLogger
{
	private OSS oss;
	private Context context;
	
	private final String bucketName = "niyouji";
	private final String ossImagesDirectoryPath = "images/";
	private final String ossvideosDirectoryPath = "videos/";
	
	private PuttingTaskCallback puttingTaskCallback;
	
	@IocAutowire
	public AliOssManager(Context context)
	{
		this.context = context;
	}
	
	public void connect(AssumeRoleResponse.Credentials credentials)
	{
		this.connect(credentials.getAccessKeyId(), credentials.getAccessKeySecret(),
				credentials.getSecurityToken());
	}
	
	public void connect(String stsTokenAccessKeyId, String stsTokenSecretKeyId,
			String stsTokenSecurityToken)
	{
		String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
		// 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考访问控制章节
		// 也可查看sample 中 sts 使用方式了解更多(https://github.com/aliyun/aliyun-oss-android-sdk/tree/master/app/src/main/java/com/alibaba/sdk/android/oss/app)
		OSSCredentialProvider credentialProvider =
				new OSSStsTokenCredentialProvider(stsTokenAccessKeyId, stsTokenSecretKeyId,
						stsTokenSecurityToken);
		//该配置类如果不设置，会有默认配置，具体可看该类
		ClientConfiguration conf = new ClientConfiguration();
		// 连接超时，默认15秒
		conf.setConnectionTimeout(15 * 1000);
		// socket超时，默认15秒
		conf.setSocketTimeout(15 * 1000);
		// 最大并发请求数，默认5个
		conf.setMaxConcurrentRequest(5);
		// 失败后最大重试次数，默认2次
		conf.setMaxErrorRetry(2);
		
		oss = new OSSClient(context, endpoint, credentialProvider);
	}
	
	public void uploadImageFile(String filename, String imageFilePath)
	{
		PutObjectRequest put =
				new PutObjectRequest(bucketName, ossImagesDirectoryPath + filename,
						imageFilePath);
		startPutObjectToOss(filename, put);
	}
	
	public void uploadVideoFile(String filename, String videoFilePath)
	{
		PutObjectRequest put =
				new PutObjectRequest(bucketName, ossvideosDirectoryPath + filename,
						videoFilePath);
		startPutObjectToOss(filename, put);
	}
	
	
	public void setPuttingTaskCallback(PuttingTaskCallback puttingTaskCallback)
	{
		this.puttingTaskCallback = puttingTaskCallback;
	}
	
	public boolean uploadImageFileBlocking(String filename, String imageFilePath)
	{
		PutObjectRequest put =
				new PutObjectRequest(bucketName, ossImagesDirectoryPath + filename,
						imageFilePath);
		
		try
		{
			PutObjectResult putResult = oss.putObject(put);
			getP().info("upload the image file["+filename+"] successfully");
			return true;
		}
		catch (ClientException | ServiceException e)
		{
			e.printStackTrace();
			getP().error("upload the image file["+filename+"] failed");
			return false;
		}
	}
	
	public boolean uploadVideoFileBlocking(String filename, String videoFilePath)
	{
		PutObjectRequest put =
				new PutObjectRequest(bucketName, ossvideosDirectoryPath + filename,
						videoFilePath);
		
		try
		{
			PutObjectResult putResult = oss.putObject(put);
			getP().info("upload the video file["+filename+"] successfully");
			return true;
		}
		catch (ClientException | ServiceException e)
		{
			e.printStackTrace();
			getP().error("upload the video file["+filename+"] failed");
			return false;
		}
	}
	
	
	//**********************************************************
	private void startPutObjectToOss(String filename, PutObjectRequest putObjectRequest)
	{
		// 异步上传时可以设置进度回调
		putObjectRequest.setProgressCallback((request, currentSize, totalSize) ->
		{
			if (puttingTaskCallback != null)
			{
				puttingTaskCallback.onPutProgress(filename, currentSize, totalSize,
						((float) currentSize / (float) totalSize));
			}
		});
		
		OSSAsyncTask task = oss.asyncPutObject(putObjectRequest,
				new OSSCompletedCallback<PutObjectRequest, PutObjectResult>()
				{
					@Override
					public void onSuccess(PutObjectRequest request, PutObjectResult result)
					{
						if (puttingTaskCallback != null)
						{
							puttingTaskCallback.onPutSuccess(filename);
						}
					}
					
					@Override
					public void onFailure(PutObjectRequest request,
							ClientException clientException, ServiceException serviceException)
					{
						if (puttingTaskCallback != null)
						{
							puttingTaskCallback
									.onPutFailure(filename, clientException, serviceException);
						}
					}
				});
	}
	
	
	//{{{{{{{{{{{{{}}}}}}}}}}}}}}}}}}}}}}}}
	public interface PuttingTaskCallback
	{
		void onPutProgress(String filename, long currentSize, long totalSize, float percent);
		
		void onPutSuccess(String filename);
		
		void onPutFailure(String filename, ClientException clientException,
				ServiceException serviceException);
	}
}
