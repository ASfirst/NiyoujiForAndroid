package com.jeramtough.niyouji.controller.handler;

import android.app.Activity;
import android.widget.TextView;
import com.jeramtough.heartlayout.HeartLayout;
import com.jeramtough.jtandroid.controller.handler.JtIocHandler;
import com.jeramtough.niyouji.R;

/**
 * @author JeramTough
 *         on 2017  December 20 Wednesday 02:01.
 */

public class TravelnoteWithAudiencesHandler extends JtIocHandler
{
	private HeartLayout heartLayout;
	private TextView textViewAttentionsCount;
	private TextView textViewAudiencesCount;
	
	public TravelnoteWithAudiencesHandler(Activity activity)
	{
		super(activity);
		
		textViewAttentionsCount = findViewById(R.id.textView_attentions_count);
		textViewAudiencesCount = findViewById(R.id.textView_audiences_count);
		heartLayout = findViewById(R.id.heart_layout);
		
		initResources();
	}
	
	protected void initResources()
	{
		//一些模拟操作
		for (int i = 0; i < 5; i++)
		{
			heartLayout.postDelayed(() ->
			{
				addAttention();
			}, i * 2000);
		}
	}
	
	public void addAttention()
	{
		int count = Integer.valueOf(textViewAttentionsCount.getText().toString());
		textViewAttentionsCount.setText((count + 1) + "");
		
		heartLayout.addHeartWithRandomColor();
	}
}
