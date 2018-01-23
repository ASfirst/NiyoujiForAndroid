package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.file.DiskUsage;
import com.jeramtough.jtandroid.ui.JtVideoView;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.R;

import java.io.File;
import java.io.IOException;

/**
 * @author 11718
 */
public class Test1Activity extends AppCompatActivity
{
	private JtVideoView videoView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test1);
		
		videoView = findViewById(R.id.videoView);
		
		String url = "http://niyouji.oss-cn-shenzhen.aliyuncs.com/videos/vdo_0_204654938.mp4";
		
		HttpProxyCacheServer proxy = new HttpProxyCacheServer.Builder(this)
				.maxCacheSize(100 * 1024 * 1024)       // 1 Gb for cache
				.diskUsage(new DiskUsage()
				{
					@Override
					public void touch(File file) throws IOException
					{
						P.debug(file.getAbsolutePath());
					}
				}).build();
		String proxyUrl = proxy.getProxyUrl(url);
		videoView.setVideoPath(proxyUrl);
		videoView.setRepeated(true);
		
		videoView.start();
		
		videoView.postDelayed(() ->
		{
			videoView.stopAndClear();
		}, 2000);
	}
	
}
