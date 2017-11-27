package com.jeramtough.niyouji.component.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.jeramtough.niyouji.R;

/**
 * @author 11718
 *         on 2017  November 27 Monday 14:38.
 */

public class LiveTravelnotePageView extends FrameLayout
{
	public LiveTravelnotePageView(Context context)
	{
		super(context);
		initResources();
	}
	
	public LiveTravelnotePageView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
		initResources();
	}
	
	
	public void initResources()
	{
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup viewGroup =
				(ViewGroup) inflater.inflate(R.layout.view_live_travelnote_page, null);
		this.addView(viewGroup);
	}
}
