package com.jeramtough.niyouji.component.ui.travelnote;

import android.content.Context;
import android.support.annotation.NonNull;
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

public class LiveTravelnotePicandwrodPageView extends LiveTravelnotePage
{
	
	public LiveTravelnotePicandwrodPageView(@NonNull Context context)
	{
		super(context);
	}
	
	@Override
	public int loadLayout()
	{
		return R.layout.view_live_travelnote_picandword_page;
	}
	
	@Override
	public void initViews(ViewGroup viewGroup)
	{
	
	}
}
