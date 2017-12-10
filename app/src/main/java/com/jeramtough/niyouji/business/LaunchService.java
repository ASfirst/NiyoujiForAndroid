package com.jeramtough.niyouji.business;

import android.Manifest;
import android.app.Activity;
import com.jeramtough.jtandroid.function.PermissionManager;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.jtandroid.java.Directory;
import com.jeramtough.jtandroid.java.ExtractedZip;
import com.jeramtough.niyouji.component.app.AppConfig;
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
		Directory pwThemesDirectory = new Directory(AppConfig.getPwThemesDirectory(activity));
		Directory videosDirectory = new Directory(AppConfig.getVideosDirectory());
		Directory imagesDirectory = new Directory(AppConfig.getImagesDirectory());
		
		if (!appDirectory.exists())
		{
			videosDirectory.mkdirs();
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
		if (!pwThemesDirectory.exists())
		{
			String pwthemesFileName = "pwthemes.zip";
			unZipFile(activity, pwThemesDirectory, pwthemesFileName);
		}
		
	}
	
	//***************************
	private void unZipFile(Activity activity, Directory resourceDirectory, String zipFileName)
	{
		resourceDirectory.mkdirs();
		
		File zipFile = new File(AppConfig.getAppDirecotry() + File.separator + zipFileName);
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
