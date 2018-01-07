package com.jeramtough.niyouji.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import com.jeramtough.jtandroid.controller.activity.JtBaseActivity;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.travelnote.LiveTravelnoteEventsCaller;
import com.jeramtough.niyouji.controller.handler.LiveTravelnoteNavigationHandler;
import com.jeramtough.niyouji.controller.handler.TravelnoteWithAudiencesHandler;

/**
 * @author 11718
 */
public class PerformingActivity extends JtBaseActivity implements LiveTravelnoteEventsCaller
{
	public static final int TAKE_PHOTO_REQUEST_CODE = 0X1;
	public static final int VIDEO_REQUEST_CODE = 0X2;
	
	private LiveTravelnoteNavigationHandler liveTravelnoteNavigationHandler;
	private TravelnoteWithAudiencesHandler travelnoteWithAudiencesHandler;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_performing);
		
		liveTravelnoteNavigationHandler =
				new LiveTravelnoteNavigationHandler(this, getSupportFragmentManager());
		travelnoteWithAudiencesHandler = new TravelnoteWithAudiencesHandler(this);
		
		liveTravelnoteNavigationHandler.setLiveTravelnoteEventsCaller(this);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == TAKE_PHOTO_REQUEST_CODE &&
				resultCode == TakePhotoActivityApp.TAKE_PHOTO_RESULT_CODE)
		{
			String path = data.getStringExtra(TakePhotoActivityApp.PHOTO_PATH_NAME);
			liveTravelnoteNavigationHandler.setPageResourcePath(path);
		}
		else if (requestCode == VIDEO_REQUEST_CODE &&
				resultCode == VideoActivityApp.VIDEO_RESULT_CODE)
		{
			String path = data.getStringExtra(VideoActivityApp.VIDEO_PATH_NAME);
			liveTravelnoteNavigationHandler.setPageResourcePath(path);
		}
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		liveTravelnoteNavigationHandler.onDestroy();
	}
	
	@Override
	public void onBackPressed()
	{
		//super.onBackPressed();
		liveTravelnoteNavigationHandler.shutdownForLiveByUseingDialog();
	}
	
	@Override
	public void onTravelnoteSelectedPage()
	{
	
	}
	
	@Override
	public void onTravelnoteAddedPage()
	{
	
	}
	
	@Override
	public void onTravelnoteDeletedPage()
	{
	
	}
	
	@Override
	public void onPageSetPicture()
	{
	
	}
	
	@Override
	public void onPageSetVideo()
	{
	
	}
	
	@Override
	public void onPageContentChanged()
	{
	
	}
	
	@Override
	public void onPageSetTheme()
	{
	
	}
	
	@Override
	public void onPageSetBackgroundMusic()
	{
	
	}
	
	@Override
	public void onTravelnoteSentPerformerBarrage()
	{
	
	}
	
	@Override
	public void onTravelnoteEnd()
	{
	
	}
}
