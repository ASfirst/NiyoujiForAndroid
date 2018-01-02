package com.jeramtough.niyouji.business;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.jtandroid.java.Directory;
import com.jeramtough.niyouji.component.app.AppConfig;
import com.jeramtough.niyouji.component.app.AppUser;

import java.io.File;
import java.util.concurrent.*;

/**
 * @author 11718
 *         on 2018  January 02 Tuesday 14:18.
 */
@JtService
public class LeftPanelService implements LeftPanelBusiness
{
	private AppUser appUser;
	
	@IocAutowire
	public LeftPanelService(AppUser appUser)
	{
		this.appUser = appUser;
	}
	
	@Override
	public boolean hasLogined()
	{
		return appUser.getHasLogined();
	}
	
	@Override
	public String getGoldCount()
	{
		return "110";
	}
	
	@Override
	public String getSurfaceImageUrl()
	{
		return appUser.getSurfaceImageUrl();
	}
	
	@Override
	public String getUserNickname()
	{
		return appUser.getNickname();
	}
	
	@Override
	public void clearTravelnoteCaches(Context context)
	{
		ThreadPoolExecutor threadPool =
				new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
						new SynchronousQueue<>());
		
		Directory videosDirectory = new Directory(AppConfig.getVideosDirectory());
		Directory imagesDirectory = new Directory(AppConfig.getImagesDirectory());
		
		int totalSpace = 0;
		if (videosDirectory.exists())
		{
			totalSpace =
					totalSpace + (int) (videosDirectory.getTotalSpace() / 1080 / 1080 / 1080);
			for (File file : videosDirectory.listFiles())
			{
				threadPool.execute(() ->
				{
					file.delete();
				});
			}
			
		}
		if (imagesDirectory.exists())
		{
			totalSpace =
					totalSpace + (int) (videosDirectory.getTotalSpace() / 1080 / 1080 / 1080);
			for (File file : imagesDirectory.listFiles())
			{
				threadPool.execute(() ->
				{
					file.delete();
				});
			}
		}
		threadPool.shutdown();
		Toast.makeText(context, "清除了" + totalSpace + "MB缓存", Toast.LENGTH_SHORT).show();
	}
	
}
