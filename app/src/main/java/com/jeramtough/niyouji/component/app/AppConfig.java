package com.jeramtough.niyouji.component.app;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @author 11718
 *         on 2017  November 21 Tuesday 13:01.
 */

public class AppConfig
{
	public static final String APP_DIRECTORY_NAME = "niyouji";
	
	/*public static final String SOCKET_SERVER_HOST = "192.168.0.120:8080";
	public static final String NIYOUJI_SERVER_HOST = "192.168.0.120:8080";
	public static final String RANDL_SERVER_HOST = "192.168.0.120:8666";*/
	
	public static final String SOCKET_SERVER_HOST = "112.74.51.247:8080";
	public static final String NIYOUJI_SERVER_HOST = "112.74.51.247:8080";
	/*public static final String SOCKET_SERVER_HOST = "192.168.31.204:8080";
	public static final String NIYOUJI_SERVER_HOST = "192.168.31.204:8080";*/
	//	public static final String RANDL_SERVER_HOST = "192.168.31.204:8666";
	public static final String RANDL_SERVER_HOST = "112.74.51.247:8666";
	
	public static String getAppDirectory(Context context)
	{
		boolean sdCardExist = Environment.getExternalStorageState()
				.equals(android.os.Environment.MEDIA_MOUNTED);
		if (sdCardExist)
		{
			return Environment.getExternalStorageDirectory() + File.separator +
					APP_DIRECTORY_NAME;
		}
		else
		{
			return context.getFilesDir() + File.separator + APP_DIRECTORY_NAME;
		}
	}
	
	public static String getImagesDirectory(Context context)
	{
		return getAppDirectory(context) + File.separator + "images";
	}
	
	public static String getVideosDirectory(Context context)
	{
		return getAppDirectory(context) + File.separator + "videos";
	}
	
	public static String getFiltersDirectory(Context context)
	{
		return context.getFilesDir() + File.separator + "filters";
	}
	
	public static String getMusicsDirectory(Context context)
	{
		return context.getFilesDir() + File.separator + "musics";
	}
	
	public static String getPwThemesDirectory(Context context)
	{
		return context.getFilesDir() + File.separator + "pwthemes";
	}
}
