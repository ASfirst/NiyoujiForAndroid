package com.jeramtough.niyouji.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.widget.ProgressBar;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.ali.AliyunVideoGlSurfaceView;

/**
 * @author 11718
 */
public class TakePhotoActivity extends AliCameraActivity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public int loadLayout()
	{
		return R.layout.activity_take_photo;
	}
}
