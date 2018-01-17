package com.jeramtough.niyouji.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.business.PerformingBusiness1;
import com.jeramtough.niyouji.business.PerformingService1;
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
	
	@InjectService(service = PerformingService1.class)
	private PerformingBusiness1 performingBusiness1;
	
	
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
		performingBusiness1.spreadTravelnoteSelectedPage(position);
	}
	
	@Override
	public void onTravelnoteAddedPage(LiveTravelnotePageView liveTravelnotePageView)
	{
		performingBusiness1.spreadTravelnoteAddedPage(liveTravelnotePageView);
	}
	
	@Override
	public void onTravelnoteDeletedPage(int position)
	{
		performingBusiness1.spreadTravelnoteDeletedPage(position);
	}
	
	@Override
	public void onPageSetPicture(int position, String imageUrl)
	{
		performingBusiness1.spreadPageSetPicture(position, imageUrl);
	}
	
	@Override
	public void onPageSetVideo(int position, String videoUrl)
	{
		performingBusiness1.spreadPageSetVideo(position, videoUrl);
	}
	
	@Override
	public void onPageContentChanged(int position, boolean isAdded, String words, int start)
	{
		performingBusiness1.spreadPageContentChanged(position, isAdded, words, start);
	}
	
	@Override
	public void onPageSetTheme(int position, int themePosition)
	{
		performingBusiness1.spreadPageSetTheme(position, themePosition);
	}
	
	@Override
	public void onPageSetBackgroundMusic(int position, String musicPath)
	{
		performingBusiness1.spreadPageSetBackgroundMusic(position, musicPath);
	}
	
	@Override
	public void onTravelnoteSentPerformerBarrage(int position, String barrageContent)
	{
		performingBusiness1.spreadTravelnoteSentPerformerBarrage(position, barrageContent);
	}
	
	@Override
	public void onTravelnoteEnd()
	{
		performingBusiness1.spreadTravelnoteEnd();
	}
}
