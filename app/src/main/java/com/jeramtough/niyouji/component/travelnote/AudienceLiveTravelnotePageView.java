package com.jeramtough.niyouji.component.travelnote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.jeramtough.jtandroid.ui.FullScreenVideoView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.travelnote.TravelnotePage;

/**
 * @author 11718
 *         on 2018  January 22 Monday 00:46.
 */

public class AudienceLiveTravelnotePageView extends FrameLayout
{
	private AppCompatImageView viewPictureOfPage;
	private AppCompatImageView imageViewFrame;
	private TextView textViewTravelnotePageContent;
	private FullScreenVideoView videoViewTravelnotePage;
	
	private TravelnotePage travelnotePage;
	
	
	public AudienceLiveTravelnotePageView(@NonNull Context context)
	{
		super(context);
		initViews();
	}
	
	public AudienceLiveTravelnotePageView(@NonNull Context context,
			@Nullable AttributeSet attrs)
	{
		super(context, attrs);
		initViews();
	}
	
	protected void initViews()
	{
		FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(getContext())
				.inflate(R.layout.audience_live_travelnote_view, null);
		this.addView(frameLayout);
		
		viewPictureOfPage = findViewById(R.id.view_picture_of_page);
		imageViewFrame = findViewById(R.id.imageView_frame);
		textViewTravelnotePageContent = findViewById(R.id.textView_travelnote_page_content);
		videoViewTravelnotePage = findViewById(R.id.videoView_travelnote_page);
	}
	
	public AppCompatImageView getViewPictureOfPage()
	{
		return viewPictureOfPage;
	}
	
	public AppCompatImageView getImageViewFrame()
	{
		return imageViewFrame;
	}
	
	public FullScreenVideoView getVideoViewTravelnotePage()
	{
		return videoViewTravelnotePage;
	}
	
	public TravelnotePage getTravelnotePage()
	{
		return travelnotePage;
	}
	
	public void setTravelnotePage(TravelnotePage travelnotePage)
	{
		this.travelnotePage = travelnotePage;
	}
	
	public TextView getTextViewTravelnotePageContent()
	{
		return textViewTravelnotePageContent;
	}
}
