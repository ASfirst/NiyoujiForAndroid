package com.jeramtough.niyouji.component.ui.travelnote;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.controller.handler.LiveTravelnoteNavigationHandler;


/**
 * @author 11718
 *         on 2017  November 29 Wednesday 21:45.
 */

public class LiveTravelnotePage extends FrameLayout implements View.OnClickListener
{
	private Handler handler;
	private LayoutInflater inflater;
	private FrameLayout layoutSelectWhichPage;
	private ImageView imageViewPicandwordPage;
	private ImageView imageViewVideoPage;
	private ImageButton btnDeletePage;
	
	private LiveTravelnotePageType liveTravelnotePageType;
	
	private boolean isActivated = false;
	
	public LiveTravelnotePage(@NonNull Context context, Handler handler)
	{
		super(context);
		this.handler = handler;
		inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		initResources();
	}
	
	protected void initResources()
	{
		layoutSelectWhichPage =
				(FrameLayout) inflater.inflate(R.layout.view_live_travelnote_page_cover, null);
		imageViewPicandwordPage =
				layoutSelectWhichPage.findViewById(R.id.imageView_picandword_page);
		imageViewVideoPage = layoutSelectWhichPage.findViewById(R.id.imageView_video_page);
		addView(layoutSelectWhichPage);
		
		imageViewPicandwordPage.setOnClickListener(this);
		imageViewVideoPage.setOnClickListener(this);
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
					liveTravelnotePageType = LiveTravelnotePageType.PICANDWORD;
					
					ViewGroup viewGroupPicandwordPage = (ViewGroup) inflater
							.inflate(R.layout.view_live_travelnote_picandword_page, null);
					this.addView(viewGroupPicandwordPage);
					
					btnDeletePage = viewGroupPicandwordPage.findViewById(R.id.btn_delete_page);
					break;
				case R.id.imageView_video_page:
					liveTravelnotePageType = LiveTravelnotePageType.VIDEO;
					
					ViewGroup viewGroupVideoPage = (ViewGroup) inflater
							.inflate(R.layout.view_live_travelnote_video_page, null);
					this.addView(viewGroupVideoPage);
					
					btnDeletePage = viewGroupVideoPage.findViewById(R.id.btn_delete_page);
					break;
			}
			btnDeletePage.setOnClickListener(this);
			handler.sendEmptyMessage(LiveTravelnoteNavigationHandler.ACTIVATE_ACTION);
			this.removeView(layoutSelectWhichPage);
		}
		else
		{
			switch (viewId)
			{
				case R.id.btn_delete_page:
					handler.sendEmptyMessage(LiveTravelnoteNavigationHandler.DELETE_ACTION);
					break;
			}
		}
	}
	
	public LiveTravelnotePageType getLiveTravelnotePageType()
	{
		return liveTravelnotePageType;
	}
}
