package com.jeramtough.niyouji.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.travelnote.LiveTravelnoteEventsCaller;
import com.jeramtough.niyouji.component.travelnote.LiveTravelnotePageView;
import com.jeramtough.niyouji.controller.handler.LiveTravelnoteNavigationHandler;
import com.jeramtough.niyouji.controller.handler.TravelnoteWithAudiencesHandler;

/**
 * @author 11718
 */
public class PerformingActivity extends AppBaseActivity implements LiveTravelnoteEventsCaller
{
	public static final int TAKE_PHOTO_REQUEST_CODE = 0X1;
	public static final int VIDEO_REQUEST_CODE = 0X2;
	
	private LiveTravelnoteNavigationHandler liveTravelnoteNavigationHandler;
	private TravelnoteWithAudiencesHandler travelnoteWithAudiencesHandler;
	
	/*@InjectService(service = PerformingService.class)
	private PerformingBusiness performingBusiness;*/
	
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
				resultCode == TakePhotoActivity.TAKE_PHOTO_RESULT_CODE)
		{
			String path = data.getStringExtra(TakePhotoActivity.PHOTO_PATH_NAME);
			
			liveTravelnoteNavigationHandler.setPageResourcePath(path);
			
		}
		else if (requestCode == VIDEO_REQUEST_CODE &&
				resultCode == VideoActivity.VIDEO_RESULT_CODE)
		{
			String path = data.getStringExtra(VideoActivity.VIDEO_PATH_NAME);
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
		liveTravelnoteNavigationHandler.shutdownForLiveByUseingDialog();
	}
	
	@Override
	public void onTravelnoteSelectedPage(int position)
	{
		P.debug("select " + position);
	}
	
	@Override
	public void onTravelnoteAddedPage(LiveTravelnotePageView liveTravelnotePageView)
	{
		P.debug("add a page");
	}
	
	@Override
	public void onTravelnoteDeletedPage(int position)
	{
		P.debug("delete " + position);
	}
	
	@Override
	public void onPageSetPicture(int position, String imageUrl)
	{
	
	}
	
	@Override
	public void onPageSetVideo(int position, String videoUrl)
	{
	
	}
	
	
	@Override
	public void onPageContentChanged(boolean isAdded, String words, int start)
	{
	
	}
	
	@Override
	public void onPageSetTheme(int position, int themePosition)
	{
		P.debug(position,themePosition);
	}
	
	@Override
	public void onPageSetBackgroundMusic(int position, String musicPath)
	{
		P.debug(position,musicPath);
	}
	
	@Override
	public void onTravelnoteSentPerformerBarrage(String barrageContent)
	{
		P.debug("sent a barrage"+barrageContent);
	}
	
	@Override
	public void onTravelnoteEnd()
	{
		P.debug("shutdown");
	}
}
