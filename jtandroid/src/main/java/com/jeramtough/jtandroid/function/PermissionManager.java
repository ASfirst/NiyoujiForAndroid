package com.jeramtough.jtandroid.function;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

import java.util.ArrayList;

/**
 * @author 11718
 *         on 2017  November 20 Monday 23:45.
 */
@JtComponent
public class PermissionManager
{
	private ArrayList<String> permissions;
	
	public PermissionManager()
	{
		this.permissions = new ArrayList<>();
	}
	
	public void addNeededPermission(String permission)
	{
		permissions.add(permission);
	}
	
	public boolean checkIsHaveAllPermission(Activity activity)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
		{
			for (String permission : getNeededPermissions())
			{
				// 检查该权限是否已经获取
				int i = ContextCompat.checkSelfPermission(activity, permission);
				// 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
				if (i != PackageManager.PERMISSION_GRANTED)
				{
					return false;
				}
			}
			return true;
		}
		else
		{
			return true;
		}
	}
	
	public void requestNeededPermissions(Activity activity, int requestCode)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
		{
			ActivityCompat
					.requestPermissions(activity, getNeededPermissions(), requestCode);
		}
	}
	
	//******************************************
	private String[] getNeededPermissions()
	{
		String[] permissions=new String[this.permissions.size()];
		for (int i=0;i<this.permissions.size();i++)
		{
			permissions[i] =this.permissions.get(i);
		}
		return permissions;
	}
}
