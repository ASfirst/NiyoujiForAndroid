package com.jeramtough.niyouji.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.*;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ui.FullScreenVideoView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.PageSetThemeCommand;
import com.jeramtough.niyouji.component.app.GlideApp;
import com.jeramtough.niyouji.component.travelnote.picandwordtheme.*;

/**
 * @author 11718
 */
public class TestActivity extends AppBaseActivity
{
	private FrameLayout layoutAudienceLiveTravelnotePage;
	private AppCompatImageView viewPictureOfPage;
	private AppCompatImageView imageViewFrame;
	private TextView textViewTravelnotePageContent;
	private FullScreenVideoView videoViewTravelnotePage;
	
	@InjectComponent
	private PicAndWordResourcesHandler picAndWordResourcesHandler;
	@InjectComponent
	private PwResourcesCacheManager pwResourcesCacheManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_audience_live_travelnote_page);
		
		layoutAudienceLiveTravelnotePage = findViewById(R.id.layout_audience_live_travelnote_page);
		viewPictureOfPage = findViewById(R.id.view_picture_of_page);
		imageViewFrame = findViewById(R.id.imageView_frame);
		textViewTravelnotePageContent = findViewById(R.id.textView_travelnote_page_content);
		videoViewTravelnotePage = findViewById(R.id.videoView_travelnote_page);
		
		GlideApp.with(this).load("http://niyouji.oss-cn-shenzhen.aliyuncs" +
				".com/images/img_0_172641769.jpg").fitCenter().skipMemoryCache(true)
				.into(viewPictureOfPage);
		
		PwResourcePosition pwResourcePosition =
				picAndWordResourcesHandler.getPwResourcePositions().get(2);
		PwResourceCache pwResourceCache =
				pwResourcesCacheManager.getPwResourceCache(pwResourcePosition);
		PicAndWordTheme picAndWordTheme = new PicAndWordThemeImpl(this, pwResourceCache);
		
		picAndWordTheme.setMainBackground(layoutAudienceLiveTravelnotePage);
		picAndWordTheme.setFrame(imageViewFrame);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.btn1:
				break;
		}
	}
	
}
