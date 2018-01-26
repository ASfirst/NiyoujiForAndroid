package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.JtVideoView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.business.*;
import com.jeramtough.niyouji.component.cache.VideoCacheServer;
import com.jeramtough.niyouji.controller.dialog.AudienceTravelnoteEndDialog;

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
		
		videoView = findViewById(R.id.videoView);
		videoView.setFullScreen(false);
		videoView.setVideoPath(videoCacheServer.toCacheUrl("http://niyouji.oss-cn-shenzhen.aliyuncs.com/videos/vdo_1_251934020.mp4"));
		videoView.setRepeated(true);
		
		videoView.start();
		
		AudienceTravelnoteEndDialog dialog=new AudienceTravelnoteEndDialog(this);
		dialog.show();
		
	}
	
}
