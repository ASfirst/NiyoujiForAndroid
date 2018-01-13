package com.jeramtough.niyouji.component.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.jeramtough.niyouji.R;

/**
 * @author JeramTough
 *         on 2017  December 18 Monday 14:16.
 */

public class AppraisalAreaView extends ScrollView implements View.OnTouchListener
{
	public static final int STYLE_1 = 1;
	public static final int STYLE_2 = 2;
	
	private boolean isHidden = false;
	private boolean isTiming = false;
	private final int howLong = 5;
	private int time = 0;
	
	private LinearLayout linearLayout;
	
	public AppraisalAreaView(Context context)
	{
		super(context);
		initResources();
	}
	
	public AppraisalAreaView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
		initResources();
	}
	
	protected void initResources()
	{
		this.setOnTouchListener(this);
		linearLayout=new LinearLayout(getContext());
		ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams
				.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
		linearLayout.setLayoutParams(params);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		this.addView(linearLayout);
	}
	
	
	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if (msg.what == 0)
			{
				AppraisalAreaView.this.setAlpha(0.4f);
			}
		}
	};
	
	public void addAppraisal(String name, String content, int style)
	{
		FrameLayout frameLayout = new FrameLayout(getContext());
		TextView textViewName = new TextView(getContext());
		TextView textViewContent = new TextView(getContext());
		
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		frameLayout.setLayoutParams(params);
		params.setMargins(0, 0, 0, 10);
		
		FrameLayout.LayoutParams params1 =
				new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
						FrameLayout.LayoutParams.MATCH_PARENT);
		textViewName.setLayoutParams(params1);
		textViewContent.setLayoutParams(params1);
		
		frameLayout.setBackgroundResource(R.drawable.background_oval);
		
		textViewName.setText(name + ":  ");
		textViewContent.setText(name + ":  " + content);
		
		if (style == STYLE_1)
		{
			textViewName.setTextColor(0XFFE2E0DF);
			textViewName.setPadding(10, 5, 10, 5);
			textViewName.setShadowLayer(1, 1, 1, Color.BLACK);
			
			textViewContent.setTextColor(Color.WHITE);
			textViewContent.setPadding(10, 5, 10, 5);
			textViewContent.setShadowLayer(1, 1, 1, Color.BLACK);
		}
		else if (style==STYLE_2)
		{
			textViewName.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
			textViewName.setPadding(10, 5, 10, 5);
			textViewName.setShadowLayer(1, 1, 1, Color.BLACK);
			textViewName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
			
			textViewContent.setTextColor(getResources().getColor(R.color.colorPrimary));
			textViewContent.setPadding(10, 5, 10, 5);
			textViewContent.setShadowLayer(1, 1, 1, Color.BLACK);
			textViewContent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		}
		
		
		frameLayout.addView(textViewContent);
		frameLayout.addView(textViewName);
		linearLayout.addView(frameLayout);
		this.post(() ->
		{
			this.fullScroll(View.FOCUS_DOWN);
		});
		
		//开始定时，当五秒无弹幕后自动淡化
		startTiming();
	}
	
	
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			startTiming();
		}
		return false;
	}
	
	//***********************
	private void startTiming()
	{
		
		if (isTiming == false)
		{
			time = howLong;
			isTiming = true;
			
			new Thread(() ->
			{
				while (time != 0)
				{
					try
					{
						Thread.sleep(1000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					time--;
				}
				handler.sendEmptyMessage(0);
				isHidden = true;
				isTiming = false;
			}).start();
		}
		else
		{
			time = howLong;
		}
		
		if (isHidden == true)
		{
			this.setAlpha(1f);
			isHidden = false;
		}
	}
	
	
}
