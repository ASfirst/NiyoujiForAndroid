package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.ui.DanmakuLayout;

/**
 * @author 11718
 */
public class TestActivity extends AppBaseActivity
{
	private DanmakuLayout layoutDanmaku;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		layoutDanmaku = findViewById(R.id.layout_danmaku);
		
		TextView textView = new TextView(this);
		textView.setText("第三方的说法苏打粉撒");
		layoutDanmaku.addViewWithAnimation(textView,
				DanmakuLayout.ANIMATION_STYLE2);
		
		TextView textView1 = new TextView(this);
		textView1.setText("dfsdaf sdfsadf");
		layoutDanmaku.addViewWithAnimation(textView1,
				DanmakuLayout.ANIMATION_STYLE1);
	}
	
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
		}
	}
	
}
