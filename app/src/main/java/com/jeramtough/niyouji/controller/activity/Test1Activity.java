package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ioc.annotation.InjectView;
import com.jeramtough.jtandroid.ui.JtVideoView;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.business.*;
import com.jeramtough.niyouji.component.app.AppConfig;
import com.jeramtough.niyouji.component.cache.VideoCacheServer;
import com.jeramtough.niyouji.component.websocket.PerformerWebSocketClient;
import com.jeramtough.niyouji.controller.dialog.AudienceTravelnoteEndDialog;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;

import java.net.URISyntaxException;
import java.util.TimerTask;

/**
 * @author 11718
 */
public class Test1Activity extends AppBaseActivity
{
	@InjectView(R.id.videoView_travelnote_page)
	private JtVideoView videoView;
	
	@InjectComponent
	private VideoCacheServer videoCacheServer;
	
	@InjectService(service = PerformingService.class)
	private PerformingBusiness performingBusiness;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test1);
		getIocContainer().injectView(this, this);
		
		String cacheUrl = videoCacheServer.toCacheUrl(
				"http://niyouji.oss-cn-shenzhen.aliyuncs.com/videos/vdo_2_25816732.mp4");
		videoView.setVideoPath(cacheUrl);
		videoView.setRepeated(true);
		videoView.start();
		
		videoView.postDelayed(()->{
			P.arrive();
			Toast.makeText(this,"a",Toast.LENGTH_SHORT).show();
			videoView.stopPlayback();
			videoView.setVideoPath(cacheUrl);
			videoView.start();
		},8000);
	}
	
}
