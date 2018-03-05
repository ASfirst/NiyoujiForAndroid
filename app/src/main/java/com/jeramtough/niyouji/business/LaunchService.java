package com.jeramtough.niyouji.business;

import android.Manifest;
import android.app.Activity;
import com.aliyun.common.httpfinal.QupaiHttpFinal;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.function.PermissionManager;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.jtandroid.java.Directory;
import com.jeramtough.jtandroid.java.ExtractedZip;
import com.jeramtough.niyouji.component.app.AppConfig;
import com.jeramtough.niyouji.component.app.AppUser;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 11718
 *         on 2017  November 20 Monday 23:42.
 */
@JtService
public class LaunchService implements LaunchBusiness
{
	private final PermissionManager permissionManager;
	private final AppUser appUser;
	
	@IocAutowire
	public LaunchService(PermissionManager permissionManager, AppUser appUser)
	{
		this.permissionManager = permissionManager;
		this.appUser = appUser;
	}
	
	@Override
	public boolean isFirstBoot()
	{
		return appUser.isFirstBoot();
	}
	
	@Override
	public void hasBootFinally()
	{
		appUser.setFirstBoot(false);
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
		Directory appDirectory = new Directory(AppConfig.getAppDirectory(activity));
		
		Directory filtersDirectory = new Directory(AppConfig.getFiltersDirectory(activity));
		Directory musicsDirectory = new Directory(AppConfig.getMusicsDirectory(activity));
		Directory pwThemesDirectory = new Directory(AppConfig.getPwThemesDirectory(activity));
		Directory videosDirectory = new Directory(AppConfig.getVideosDirectory(activity));
		Directory imagesDirectory = new Directory(AppConfig.getImagesDirectory(activity));
		
		if (!videosDirectory.exists())
		{
			videosDirectory.mkdirs();
		}
		if (!imagesDirectory.exists())
		{
			imagesDirectory.mkdirs();
		}
		
		
		if (!filtersDirectory.exists())
		{
			String filtersFileName = "filters.zip";
			unZipFile(activity, filtersDirectory, filtersFileName);
		}
		if (!musicsDirectory.exists())
		{
			String musicsFileName = "musics.zip";
			unZipFile(activity, musicsDirectory, musicsFileName);
		}
		//!pwThemesDirectory.exists()
		if (!pwThemesDirectory.exists())
		{
			String pwthemesFileName = "pwthemes.zip";
			unZipFile(activity, pwThemesDirectory, pwthemesFileName);
		}
		
	}
	
	@Override
	public void initAppData(BusinessCaller businessCaller)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(1000);
					businessCaller.callBusiness();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	//***************************
	private void unZipFile(Activity activity, Directory resourceDirectory, String zipFileName)
	{
		resourceDirectory.mkdirs();
		
		File zipFile =
				new File(AppConfig.getAppDirectory(activity) + File.separator + zipFileName);
		try
		{
			zipFile.createNewFile();
			IOUtils.copy(activity.getResources().getAssets().open(zipFileName),
					new FileOutputStream(zipFile));
			ExtractedZip extractedZip = new ExtractedZip(zipFile);
			extractedZip.extract(resourceDirectory.getAbsolutePath());
			
			zipFile.delete();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
