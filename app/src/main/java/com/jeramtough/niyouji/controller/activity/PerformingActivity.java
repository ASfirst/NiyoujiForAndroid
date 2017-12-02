package com.jeramtough.niyouji.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.jeramtough.jtandroid.controller.activity.JtBaseActivity;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.controller.handler.LiveTravelnoteNavigationHandler;

/**
 * @author 11718
 */
public class PerformingActivity extends JtBaseActivity
{
	public static final int TAKE_PHOTO_REQUEST_CODE = 0X1;
	public static final int VIDEO_REQUEST_CODE = 0X2;
	
	private LiveTravelnoteNavigationHandler liveTravelnoteNavigationHandler;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_performing);
		
		liveTravelnoteNavigationHandler = new LiveTravelnoteNavigationHandler(this);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == TAKE_PHOTO_REQUEST_CODE &&
				resultCode == TakePhotoActivity.TAKE_PHOTO_RESULT_CODE)
		{
			String path=data.getStringExtra(TakePhotoActivity.PHOTO_PATH_NAME);
			liveTravelnoteNavigationHandler.setPageResourcePath(path);
		}
		else if (requestCode == VIDEO_REQUEST_CODE &&
				resultCode == VideoActivity.VIDEO_RESULT_CODE)
		{
		
		}
	}
}