package com.jeramtough.niyouji.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.business.PerformingBusiness;
import com.jeramtough.niyouji.business.PerformingService;
import com.jeramtough.niyouji.component.app.AppConfig;

import java.io.File;

/**
 * @author 11718
 */
public class TestActivity extends AppBaseActivity
{
	private Button btn1;
	
	@InjectService(service = PerformingService.class)
	private PerformingBusiness performingBusiness;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		btn1 = findViewById(R.id.btn1);
		/*new Thread()
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
					aliOssManager
							.setPuttingTaskCallback(new AliOssManager.PuttingTaskCallback()
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
			}
		}.start();*/
		
		IntentUtil.toTheOtherActivity(this,TakePhotoActivity.class,0);
		
		btn1.setOnClickListener(this);
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		String path = data.getStringExtra(TakePhotoActivity.PHOTO_PATH_NAME);
		performingBusiness.uploadImageFile(this,"e.jpg",path,null);
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.btn1:
				
				String imageFilePath =
						AppConfig.getAppDirecotry() + File.separator + "test.jpg";
				String imageFilePath1
						="/storage/emulated/0/niyouji/images/JPEG_1515598406791.jpg";
				String videoFilePath =
						AppConfig.getAppDirecotry() + File.separator + "test.mp4";
				
//				aliOssManager.uploadVideoFile("a.mp4",videoFilePath);
				break;
		}
	}
	
}
