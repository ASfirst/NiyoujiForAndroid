package com.jeramtough.niyouji.component.ui.travelnote;

import android.media.MediaPlayer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;
import com.jeramtough.jtandroid.jtlog2.P;
import com.jeramtough.jtandroid.ui.FullScreenVideoView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.controller.handler.LiveTravelnoteNavigationHandler;


/**
 * @author 11718
 *         on 2017  December 03 Sunday 13:49.
 */

public class LiveVideoPage
		implements View.OnClickListener, MediaPlayer.OnCompletionListener, View.OnTouchListener
{
	private ViewGroup viewGroup;
	private Handler handler;
	private ImageButton btnDeletePage;
	private FullScreenVideoView videoViewTravelnotePage;
	
	private boolean isTouchable=false;
	
	
	public LiveVideoPage(ViewGroup viewGroup, Handler handler)
	{
		this.viewGroup = viewGroup;
		this.handler = handler;
		
		btnDeletePage = viewGroup.findViewById(R.id.btn_delete_page);
		videoViewTravelnotePage = viewGroup.findViewById(R.id.videoView_travelnote_page);
		
		
		btnDeletePage.setOnClickListener(this);
		videoViewTravelnotePage.setOnCompletionListener(this);
		videoViewTravelnotePage.setOnTouchListener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.btn_delete_page:
				handler.sendEmptyMessage(LiveTravelnoteNavigationHandler.DELETE_ACTION);
				break;
		}
	}
	
	@Override
	public void onCompletion(MediaPlayer mp)
	{
		videoViewTravelnotePage.start();
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			if (isTouchable)
			{
				handler.sendEmptyMessage(LiveTravelnoteNavigationHandler.VIDEO_ACTION);
			}
		}
		return true;
	}
	
	public VideoView getVideoViewTravelnotePage()
	{
		return videoViewTravelnotePage;
	}
	
	public void displayVideo(String path)
	{
		videoViewTravelnotePage.setVideoPath(path);
		videoViewTravelnotePage.start();
	}
	
	public void setTouchable(boolean touchable)
	{
		isTouchable = touchable;
	}
}
