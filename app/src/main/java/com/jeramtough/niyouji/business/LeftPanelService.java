package com.jeramtough.niyouji.business;

import android.os.Handler;
import android.os.Message;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.jtandroid.java.Directory;
import com.jeramtough.niyouji.component.app.AppConfig;
import com.jeramtough.niyouji.component.app.AppUser;
import com.jeramtough.niyouji.controller.handler.LeftPanelHandler;

import java.io.File;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
	public void clearTravelnoteCaches(Handler handler)
	{
		final ThreadPoolExecutor threadPool =
				new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
						new SynchronousQueue<>());
		
		Directory videosDirectory = new Directory(AppConfig.getVideosDirectory());
		Directory imagesDirectory = new Directory(AppConfig.getImagesDirectory());
		
		final int totalTaskCount =
				videosDirectory.listFiles().length + imagesDirectory.listFiles().length;
		final int[] completedTaskCount = {0};
		final long[] totalSpace = {0};
		
		if (totalTaskCount>0)
		{
			
			if (videosDirectory.exists())
			{
				for (File file : videosDirectory.listFiles())
				{
					threadPool.execute(() ->
					{
						totalSpace[0] = totalSpace[0] + file.length();
						file.delete();
						completedTaskCount[0] = completedTaskCount[0] + 1;
						if (completedTaskCount[0] == totalTaskCount)
						{
							int size = (int) (totalSpace[0] / 1080 / 1080);
							Message message = new Message();
							message.what = LeftPanelHandler.BUSINESS_CODE_CLEAR_CACHES;
							message.getData().putInt("size", size);
							handler.sendMessage(message);
						}
					});
				}
				
			}
			if (imagesDirectory.exists())
			{
				for (File file : imagesDirectory.listFiles())
				{
					threadPool.execute(() ->
					{
						totalSpace[0] = totalSpace[0] + file.length();
						file.delete();
						completedTaskCount[0] = completedTaskCount[0] + 1;
						if (completedTaskCount[0] == totalTaskCount)
						{
							int size = (int) (totalSpace[0] / 1080 / 1080);
							Message message = new Message();
							message.what = LeftPanelHandler.BUSINESS_CODE_CLEAR_CACHES;
							message.getData().putInt("size", size);
							handler.sendMessage(message);
						}
					});
				}
			}
		}
		else
		{
			int size = 0;
			Message message = new Message();
			message.what = LeftPanelHandler.BUSINESS_CODE_CLEAR_CACHES;
			message.getData().putInt("size", size);
			handler.sendMessage(message);
		}
		threadPool.shutdown();
		
	}
	
}
