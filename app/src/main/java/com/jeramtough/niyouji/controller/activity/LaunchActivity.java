package com.jeramtough.niyouji.controller.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.business.LaunchBusiness;
import com.jeramtough.niyouji.business.LaunchService;
import com.jeramtough.niyouji.component.adapter.BragAdapter;

/**
 * @author 11718
 */
public class LaunchActivity extends AppBaseActivity implements BragAdapter.GoToAppIndexCaller
{
	private final int REQUEST_NEEDED_PERMISSIONS_CALLER_CODE = 0;
	
	private final int BUSINESS_CODE_INIT_APP_DATE = 1;
	private final int BUSINESS_CODE_CREATE_DIRECTORY = 2;
	
	private ViewPager viewPageBrag;
	private TimedCloseTextView timedCloseTextView;
	
	@InjectService(service = LaunchService.class)
	private LaunchBusiness launchBusiness;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		
		viewPageBrag = findViewById(R.id.viewPage_brag);
		timedCloseTextView = findViewById(R.id.timedCloseTextView);
		
		
		initResources();
	}
	
	protected void initResources()
	{
		if (!launchBusiness.isFirstBoot())
		{
			viewPageBrag.setVisibility(View.GONE);
			if (launchBusiness
					.requestNeededPermission(this, REQUEST_NEEDED_PERMISSIONS_CALLER_CODE))
			{
				this.whenGetAllNeededPermissions();
			}
		}
		else
		{
			BragAdapter bragAdapter = new BragAdapter(this);
			bragAdapter.setGoToAppIndexCaller(this);
			viewPageBrag.setAdapter(bragAdapter);
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
			@NonNull int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		
		switch (requestCode)
		{
			case REQUEST_NEEDED_PERMISSIONS_CALLER_CODE:
			{
				for (int grantResulte : grantResults)
				{
					if (grantResulte != PackageManager.PERMISSION_GRANTED)
					{
						showNotingNoHavePermissionsDialog();
						return;
					}
				}
				whenGetAllNeededPermissions();
			}
			
		}
	}
	
	@Override
	public void handleActivityMessage(Message message)
	{
		switch (message.what)
		{
			case BUSINESS_CODE_INIT_APP_DATE:
				launchBusiness.createAppDirectory(new BusinessCaller(getActivityHandler(),
						BUSINESS_CODE_CREATE_DIRECTORY), this);
				break;
			
			case BUSINESS_CODE_CREATE_DIRECTORY:
				IntentUtil.toTheOtherActivity(this, MainActivity.class);
				this.finish();
				break;
		}
	}
	
	@Override
	public void goToIndex()
	{
		launchBusiness.hasBootFinally();
		
		boolean isHasAllPermissions = launchBusiness
				.requestNeededPermission(this, REQUEST_NEEDED_PERMISSIONS_CALLER_CODE);
		if (isHasAllPermissions)
		{
			this.whenGetAllNeededPermissions();
		}
	}
	
	//-----------------------------------------------------
	protected void whenGetAllNeededPermissions()
	{
		timedCloseTextView.setPrimaryMessage("正在初始化资源中。。。");
		timedCloseTextView.visible();
		
		launchBusiness.initAppData(
				new BusinessCaller(getActivityHandler(), BUSINESS_CODE_INIT_APP_DATE));
	}
	
	//*************************************************
	private void showNotingNoHavePermissionsDialog()
	{
		AlertDialog dialog =
				new AlertDialog.Builder(this).setMessage("没有授权应用将不发正常使用，请重启应用进行重新授权").create();
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定", (dialog1, which) ->
		{
			this.finish();
			System.exit(0);
		});
		dialog.show();
	}
	
	
}
