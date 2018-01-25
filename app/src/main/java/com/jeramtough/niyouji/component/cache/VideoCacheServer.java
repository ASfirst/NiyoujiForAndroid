package com.jeramtough.niyouji.component.cache;

import android.content.Context;
import com.danikula.videocache.HttpProxyCacheServer;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtlog3.WithLogger;

import java.io.File;

/**
 * @author 11718
 *         on 2018  January 23 Tuesday 23:23.
 */
@JtComponent
public class VideoCacheServer implements WithLogger
{
	private Context context;
	private HttpProxyCacheServer proxy;
	
	private static final int MAX_CACHE_MB = 100;
	
	@IocAutowire
	public VideoCacheServer(Context context)
	{
		this.context = context;
		
		//cancel logging
		unableLog();
		
		proxy = new HttpProxyCacheServer.Builder(context)
				.maxCacheSize(MAX_CACHE_MB * 1024 * 1024).diskUsage((File file) ->
				{
					getP().info("cache a file [" + file.getName() + "]");
				}).build();
	}
	
	public String toCacheUrl(String originalUrl)
	{
		return proxy.getProxyUrl(originalUrl);
	}
}
