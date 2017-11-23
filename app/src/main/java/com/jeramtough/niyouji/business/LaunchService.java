package com.jeramtough.niyouji.business;

import android.Manifest;
import android.app.Activity;
import com.jeramtough.jtandroid.function.PermissionManager;
import com.jeramtough.jtandroid.java.Directory;
import com.jeramtough.jtandroid.java.ExtractedZip;
import com.jeramtough.jtandroid.jtlog2.P;
import com.jeramtough.niyouji.component.config.AppConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
	public boolean requestNeededPermission(Activity activity, int requestCode)
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
			permissionManager.requestNeededPermissions(activity, requestCode);
			return false;
		}
	}
	
	@Override
	public void createAppDirectory(Activity activity)
	{
		Directory appDirectory = new Directory(AppConfig.getAppDirecotry());
		
		Directory filtersDirectory = new Directory(AppConfig.getFiltersDirectory(activity));
		Directory musicsDirectory = new Directory(AppConfig.getMusicsDirectory(activity));
		Directory videosDirectory = new Directory(AppConfig.getVideosDirectory());
		Directory imagesDirectory = new Directory(AppConfig.getImagesDirectory());
		
		if (!appDirectory.exists())
		{
			videosDirectory.mkdirs();
			imagesDirectory.mkdirs();
		}
		
		if (!filtersDirectory.exists()||!musicsDirectory.exists())
		{
			filtersDirectory.mkdirs();
			musicsDirectory.mkdirs();
			
			String filtersFileName = "filters.zip";
			String musicsFileName = "musics.zip";
			File filtersFile =
					new File(AppConfig.getAppDirecotry() + File.separator + filtersFileName);
			File musicsFile =
					new File(AppConfig.getAppDirecotry() + File.separator + musicsFileName);
			try
			{
				filtersFile.createNewFile();
				musicsFile.createNewFile();
				
				IOUtils.copy(activity.getResources().getAssets().open(filtersFileName),
						new FileOutputStream(filtersFile));
				IOUtils.copy(activity.getResources().getAssets().open(musicsFileName),
						new FileOutputStream(musicsFile));
				
				ExtractedZip extractedZip=new ExtractedZip(filtersFile);
				ExtractedZip extractedZip1=new ExtractedZip(musicsFile);
				
				extractedZip.extract(filtersDirectory.getAbsolutePath());
				extractedZip1.extract(musicsDirectory.getAbsolutePath());
				
				filtersFile.delete();
				musicsFile.delete();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
	}
}
