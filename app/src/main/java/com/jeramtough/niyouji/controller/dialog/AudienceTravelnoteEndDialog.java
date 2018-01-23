package com.jeramtough.niyouji.controller.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import com.jeramtough.niyouji.R;

/**
 * @author 11718
 *         on 2018  January 24 Wednesday 01:58.
 */

public class AudienceTravelnoteEndDialog extends Dialog implements View.OnClickListener
{
	private Activity activity;
	private Button buttonDone;
	
	
	public AudienceTravelnoteEndDialog(@NonNull Activity activity)
	{
		super(activity);
		this.activity=activity;
		
		this.setContentView(R.layout.dialog_audience_travelnote_end);
		
		buttonDone = findViewById(R.id.button_done);
		
		buttonDone.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.button_done:
				activity.finish();
				break;
		}
	}
}
