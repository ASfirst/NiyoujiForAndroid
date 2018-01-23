package com.jeramtough.niyouji.component.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.jeramtough.jtandroid.ui.JtVideoView;
import com.jeramtough.niyouji.R;

/**
 * @author 11718
 *         on 2017  November 27 Monday 14:30.
 */

public class AddImgOrVideoView extends FrameLayout
{
	public AddImgOrVideoView(@NonNull Context context)
	{
		super(context);
		initResources();
	}
	
	public AddImgOrVideoView(@NonNull Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
		initResources();
	}
	
	protected void initResources()
	{
		FrameLayout.LayoutParams params =
				new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);
		JtVideoView jtVideoView = new JtVideoView(getContext());
		jtVideoView.setLayoutParams(params);
		
		ImageView imageView = new ImageView(getContext());
		imageView.setImageResource(R.drawable.plussign);
		imageView.setLayoutParams(params);
		
		this.addView(jtVideoView);
		this.addView(imageView);
	}
}
