package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import com.jeramtough.jtandroid.ui.JtViewPager;
import com.jeramtough.jtandroid.ui.springindicator.SpringIndicator;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.ui.TravelnotePagesHandler;

/**
 * @author 11718
 */
public class PerformingActivity extends AppCompatActivity
{
	private TravelnotePagesHandler travelnotePagesHandler;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_performing);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		JtViewPager viewPagerTravelnotePages = findViewById(R.id.viewPager_travelnote_pages);
		SpringIndicator indicator = findViewById(R.id.indicator);
		travelnotePagesHandler =
				new TravelnotePagesHandler(viewPagerTravelnotePages, indicator);
		
		
	}
}
