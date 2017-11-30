package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.controller.handler.LiveTravelnoteNavigationHandler;

/**
 * @author 11718
 */
public class PerformingActivity extends AppCompatActivity
{
	private LiveTravelnoteNavigationHandler liveTravelnoteNavigationHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_performing);
		
		liveTravelnoteNavigationHandler=new LiveTravelnoteNavigationHandler(this);
	}
}
