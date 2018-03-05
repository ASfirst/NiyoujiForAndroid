package com.jeramtough.niyouji.controller.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import com.jeramtough.jtandroid.controller.dialog.TutorialDialog;
import com.jeramtough.jtandroid.util.MeasureUtil;
import com.jeramtough.niyouji.R;

/**
 * @author 11718
 *         on 2018  March 05 Monday 21:15.
 */

public class MyTutorialDialog extends TutorialDialog
{
	public MyTutorialDialog(@NonNull Context context)
	{
		super(context);
		
	}
	
	@Override
	protected void initViews()
	{
		super.initViews();
		
		getUnderstandButton().setBackgroundResource(R.drawable.btn_done);
		getUnderstandButton().setTextColor(Color.WHITE);
		getUnderstandButton().setTextSize(MeasureUtil.dp2px(getContext(), 8));
		getUnderstandButton().setPadding(30, 10, 30, 15);
	}
}
