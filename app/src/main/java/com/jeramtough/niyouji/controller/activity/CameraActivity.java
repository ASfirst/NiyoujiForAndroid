package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.view.WindowManager;
import com.jeramtough.niyouji.R;

/**
 * @author 11718
 */
public class CameraActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_camera);
		
		//去掉信息栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}
