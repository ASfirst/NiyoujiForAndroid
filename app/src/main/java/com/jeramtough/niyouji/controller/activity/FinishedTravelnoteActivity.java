package com.jeramtough.niyouji.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.travelnote.FinishedTravelnoteCover;

/**
 * @author 11718
 */
public class FinishedTravelnoteActivity extends AppCompatActivity
{
	private WebView webView;
	
	private FinishedTravelnoteCover finishedTravelnoteCover;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finished_travelnote);
		
		webView = findViewById(R.id.webView);
		
		initResources();
	}
	
	protected void initResources()
	{
		finishedTravelnoteCover = (FinishedTravelnoteCover) getIntent()
				.getSerializableExtra("finishedTravelnoteCover");
	}
}
