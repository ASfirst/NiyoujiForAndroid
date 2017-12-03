package com.jeramtough.niyouji.component.ui.travelnote;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.controller.handler.LiveTravelnoteNavigationHandler;


/**
 * @author 11718
 *         on 2017  November 29 Wednesday 21:45.
 */

public class LiveTravelnotePageView extends FrameLayout implements View.OnClickListener
{
	private Handler handler;
	private LayoutInflater inflater;
	private FrameLayout layoutSelectWhichPage;
	private ImageView imageViewPicandwordPage;
	private ImageView imageViewVideoPage;
	private String resourcePath;
	
	private LiveVideoPage liveVideoPage;
	private LivePicandwordPage livePicandwordPage;
	
	private LiveTravelnotePageType liveTravelnotePageType;
	
	private boolean isActivated = false;
	
	public LiveTravelnotePageView(@NonNull Context context, Handler handler)
	{
		super(context);
		this.handler = handler;
		inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		//初始化游记页封面的控件
		layoutSelectWhichPage =
				(FrameLayout) inflater.inflate(R.layout.view_live_travelnote_page_cover, null);
		imageViewPicandwordPage =
				layoutSelectWhichPage.findViewById(R.id.imageView_picandword_page);
		imageViewVideoPage = layoutSelectWhichPage.findViewById(R.id.imageView_video_page);
		addView(layoutSelectWhichPage);
		
		imageViewPicandwordPage.setOnClickListener(this);
		imageViewVideoPage.setOnClickListener(this);
		
		initResources();
	}
	
	protected void initResources()
	{
	
	}
	
	@Override
	public void onClick(View v)
	{
		int viewId = v.getId();
		if (viewId == R.id.imageView_picandword_page || viewId == R.id.imageView_video_page)
		{
			switch (viewId)
			{
				case R.id.imageView_picandword_page:
					//设置游记页种类
					liveTravelnotePageType = LiveTravelnotePageType.PICANDWORD;
					//实例化图文页的对象
					ViewGroup viewGroupPicandwordPage = (ViewGroup) inflater
							.inflate(R.layout.view_live_travelnote_picandword_page, null);
					this.addView(viewGroupPicandwordPage);
					
					livePicandwordPage =
							new LivePicandwordPage(viewGroupPicandwordPage, handler);
					
					//回调更新Activity动作
					handler.sendEmptyMessage(
							LiveTravelnoteNavigationHandler.ACTIVATE_IMAGE_ACTION);
					break;
				case R.id.imageView_video_page:
					liveTravelnotePageType = LiveTravelnotePageType.VIDEO;
					
					ViewGroup viewGroupVideoPage = (ViewGroup) inflater
							.inflate(R.layout.view_live_travelnote_video_page, null);
					this.addView(viewGroupVideoPage);
					
					liveVideoPage=new LiveVideoPage(viewGroupVideoPage,handler);
					
					handler.sendEmptyMessage(
							LiveTravelnoteNavigationHandler.ACTIVATE_VIDEO_ACTION);
					break;
			}
			isActivated = true;
			this.removeView(layoutSelectWhichPage);
		}
	}
	
	public LiveTravelnotePageType getLiveTravelnotePageType()
	{
		return liveTravelnotePageType;
	}
	
	
	public String getResourcePath()
	{
		return resourcePath;
	}
	
	public LivePicandwordPage getLivePicandwordPage()
	{
		return livePicandwordPage;
	}
	
	public LiveVideoPage getLiveVideoPage()
	{
		return liveVideoPage;
	}
	
	public void setResourcePath(String resourcePath)
	{
		this.resourcePath = resourcePath;
	}
	
	//**********************************
}
