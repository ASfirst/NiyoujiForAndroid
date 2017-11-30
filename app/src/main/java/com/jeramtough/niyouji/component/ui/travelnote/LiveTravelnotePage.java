package com.jeramtough.niyouji.component.ui.travelnote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.jeramtough.niyouji.R;

/**
 * @author 11718
 *         on 2017  November 29 Wednesday 21:45.
 */

public abstract class LiveTravelnotePage extends FrameLayout
{
	private FrameLayout layoutSelectWhichPage;
	private ImageView imageViewPicandwordPage;
	private ImageView imageViewVideoPage;
	
	private boolean isActivated = false;
	
	public LiveTravelnotePage(@NonNull Context context)
	{
		super(context);
		initResources();
	}
	
	protected void initResources()
	{
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(loadLayout(), null);
		this.addView(viewGroup);
		
		layoutSelectWhichPage = findViewById(R.id.layout_select_which_page);
		imageViewPicandwordPage = findViewById(R.id.imageView_picandword_page);
		imageViewVideoPage = findViewById(R.id.imageView_video_page);
		
		this.initViews(viewGroup);
	}
	
	public abstract int loadLayout();
	
	public abstract void initViews(ViewGroup viewGroup);
	
	
}
