package com.jeramtough.niyouji.controller.activity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import com.jeramtough.jtandroid.ioc.InjectedObjects;
import com.jeramtough.jtandroid.ioc.IocContainer;
import com.jeramtough.jtandroid.ioc.IocContainerImpl;
import com.jeramtough.jtandroid.ioc.IocContainerListener;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.business.LaunchBusiness;
import com.jeramtough.niyouji.component.ioc.MyInjectedObjects;

/**
 * @author 11718
 */
public class LaunchActivity extends BaseActivity implements IocContainerListener
{
	private final int REQUEST_NEEDED_PERMISSIONS_CALLER_CODE = 0;
	
	private LaunchBusiness launchBusiness;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		
		IocContainer iocContainer = IocContainerImpl.getIocContainer();
		iocContainer.setIocContainerListener(this);
		iocContainer.inject(MyInjectedObjects.class);
	}
	
	@Override
	public void onInjectedSuccessfully(InjectedObjects injectedObjects)
	{
		launchBusiness = getMyInjectedObjects().getLaunchBusiness();
		if (launchBusiness
				.requestNeededPermission(this, REQUEST_NEEDED_PERMISSIONS_CALLER_CODE))
		{
			this.goToMainActivity();
		}
	}
	
	@Override
	public void onInjectedFailed(Exception e)
	{
	
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
				goToMainActivity();
			}
			
		}
	}
	
	private void goToMainActivity()
	{
		IntentUtil.toTheOtherActivity(this, MainActivity.class);
		this.finish();
	}
	
	private void showNotingNoHavePermissionsDialog()
	{
		AlertDialog dialog =
				new AlertDialog.Builder(this).setMessage("没有授权应用将不发正常使用，请重启应用进行重新授权").create();
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定", (dialog1, which) ->
		{
			System.exit(0);
		});
		dialog.show();
	}
	
	
}
