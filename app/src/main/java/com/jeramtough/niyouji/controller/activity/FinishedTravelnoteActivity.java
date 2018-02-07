package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.travelnote.FinishedTravelnoteCover;
import com.jeramtough.niyouji.component.ui.NiyoujiWebView;

/**
 * @author 11718
 */
public class FinishedTravelnoteActivity extends AppBaseActivity
		implements View.OnFocusChangeListener, View.OnTouchListener
{
	private NiyoujiWebView niyoujiWebView;
	private AppCompatImageButton imageButtonBack;
	private EditText editAppraise;
	private FrameLayout layoutAppraise;
	private TextView textViewAppraiseCount;
	private AppCompatImageView imageButtonFavorite;
	private TextView textViewDone;
	private LinearLayout layoutAppraiseAndFavorite;
	
	private FinishedTravelnoteCover finishedTravelnoteCover;
	
	private boolean isFavorite = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finished_travelnote);
		
		niyoujiWebView = findViewById(R.id.webView);
		imageButtonBack = findViewById(R.id.imageButton_back);
		editAppraise = findViewById(R.id.edit_appraise);
		layoutAppraise = findViewById(R.id.layout_appraise);
		textViewAppraiseCount = findViewById(R.id.textView_appraise_count);
		imageButtonFavorite = findViewById(R.id.imageButton_favorite);
		textViewDone = findViewById(R.id.textView_done);
		layoutAppraiseAndFavorite = findViewById(R.id.layout_appraise_and_favorite);
		
		textViewDone.setVisibility(View.GONE);
		
		imageButtonBack.setOnClickListener(this);
		textViewDone.setOnClickListener(this);
		layoutAppraise.setOnClickListener(this);
		imageButtonFavorite.setOnClickListener(this);
		editAppraise.setOnFocusChangeListener(this);
		
		niyoujiWebView.setOnTouchListener(this);
		initResources();
	}
	
	protected void initResources()
	{
		finishedTravelnoteCover = (FinishedTravelnoteCover) getIntent()
				.getSerializableExtra("finishedTravelnoteCover");
		if (finishedTravelnoteCover.getAppraiseCount() > 0)
		{
			textViewAppraiseCount.setText(finishedTravelnoteCover.getAppraiseCount() + "");
		}
		else
		{
			textViewAppraiseCount.setVisibility(View.GONE);
		}
		
		niyoujiWebView.loadTravelnoteWebpage(finishedTravelnoteCover.getTravelnoteId());
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.imageButton_back:
				this.finish();
				break;
			case R.id.textView_done:
				break;
			case R.id.imageButton_favorite:
				//刷新，到时会删掉
				niyoujiWebView.clearCache(true);
				niyoujiWebView.loadTravelnoteWebpage(finishedTravelnoteCover.getTravelnoteId());
				
				if (isFavorite)
				{
					imageButtonFavorite.setImageResource(R.drawable.ic_favorite_gray);
				}
				else
				{
					imageButtonFavorite.setImageResource(R.drawable.ic_favorite_red);
				}
				isFavorite = !isFavorite;
				break;
			case R.id.layout_appraise:
				break;
		}
	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus)
	{
		switch (v.getId())
		{
			case R.id.edit_appraise:
				if (hasFocus)
				{
					textViewDone.setVisibility(View.VISIBLE);
					layoutAppraiseAndFavorite.setVisibility(View.GONE);
				}
				else
				{
					textViewDone.setVisibility(View.GONE);
					layoutAppraiseAndFavorite.setVisibility(View.VISIBLE);
				}
				break;
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if (editAppraise.hasFocus())
			{
				editAppraise.clearFocus();
			}
		}
		return false;
	}
}
