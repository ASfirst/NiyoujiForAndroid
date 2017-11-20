package com.jeramtough.niyouji.business;

import android.Manifest;
import android.app.Activity;
import com.jeramtough.jtandroid.function.PermissionManager;

/**
 * @author 11718
 *         on 2017  November 20 Monday 23:42.
 */

public class LaunchService implements LaunchBusiness
{
	private final PermissionManager permissionManager;
	
	public LaunchService(PermissionManager permissionManager)
	{
		this.permissionManager = permissionManager;
	}
	
	@Override
	public boolean requestNeededPermission(Activity activity,int requestCode)
	{
		permissionManager.addNeededPermission(Manifest.permission.CAMERA);
		permissionManager.addNeededPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
		permissionManager.addNeededPermission(Manifest.permission.RECORD_AUDIO);
		permissionManager.addNeededPermission(Manifest.permission.ACCESS_FINE_LOCATION);
		
		boolean isHaveAllNeededPermissions =
				permissionManager.checkIsHaveAllPermission(activity);
		if (isHaveAllNeededPermissions)
		{
			return true;
		}
		else
		{
			permissionManager.requestNeededPermissions(activity,requestCode);
			return false;
		}
	}
}
