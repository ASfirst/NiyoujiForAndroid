package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.widget.TextView;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
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
	private JtVideoView videoView;
	
	@InjectComponent
	private VideoCacheServer videoCacheServer;
	
	@InjectService(service = PerformingService.class)
	private PerformingBusiness performingBusiness;
	
	//@InjectService(service = PerformingService1.class)
	//private PerformingBusiness1 performingBusiness1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test1);
		
		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					PerformerWebSocketClient performerWebSocketClient=new PerformerWebSocketClient();
					performerWebSocketClient.connectBlocking();
					performerWebSocketClient.closeBlocking();
					performerWebSocketClient=
							(PerformerWebSocketClient) performerWebSocketClient.clone();
					performerWebSocketClient.connectBlocking();
				}
				catch (URISyntaxException | InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
		
		
		
	}
	
}
