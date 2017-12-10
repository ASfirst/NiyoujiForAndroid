package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.jtlog2.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.picandword.PicAndWordResourcesHandler;

/**
 * @author 11718
 */
public class TestActivity extends AppBaseActivity
{
	
	@InjectComponent
	private PicAndWordResourcesHandler picAndWordResourcesHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
	}
}
