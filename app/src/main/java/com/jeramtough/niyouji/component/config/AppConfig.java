package com.jeramtough.niyouji.component.config;

import android.content.Context;
import android.os.Environment;
import com.jeramtough.jtandroid.jtlog2.P;

import java.io.File;

/**
 * @author 11718
 *         on 2017  November 21 Tuesday 13:01.
 */

public class AppConfig
{
	public static final String APP_DIRECTORY_NAME = "niyouji";
	
	public static String getAppDirecotry()
	{
		return Environment.getExternalStorageDirectory() + File.separator + APP_DIRECTORY_NAME;
	}
	
	public static String getImagesDirectory()
	{
		return getAppDirecotry() + File.separator + "images";
	}
	
	public static String getVideosDirectory()
	{
		return getAppDirecotry() + File.separator + "videos";
	}
	
	public static String getFiltersDirectory(Context context)
	{
		P.debug(context.getFilesDir() + File.separator + "filters");
		return context.getFilesDir() + File.separator + "filters";
	}
	
	public static String getMusicsDirectory(Context context)
	{
		return context.getFilesDir() + File.separator + "musics";
	}
}
