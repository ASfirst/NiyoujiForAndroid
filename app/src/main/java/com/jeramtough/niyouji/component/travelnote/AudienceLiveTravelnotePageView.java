package com.jeramtough.niyouji.component.travelnote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
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
	private ViewGroup layoutAudienceLiveTravelnotePage;
	
	
	private int currentThemePosition = 0;
	private String musicPath;
	private String resourcePath;
	private TravelnotePageType travelnotePageType;
	
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
				.inflate(R.layout.view_audience_live_travelnote_page, null);
		this.addView(frameLayout);
		
		viewPictureOfPage = findViewById(R.id.view_picture_of_page);
		imageViewFrame = findViewById(R.id.imageView_frame);
		textViewTravelnotePageContent = findViewById(R.id.textView_travelnote_page_content);
		videoViewTravelnotePage = findViewById(R.id.videoView_travelnote_page);
		layoutAudienceLiveTravelnotePage =
				findViewById(R.id.layout_audience_live_travelnote_page);
	}
	
	public void clearTheme()
	{
		layoutAudienceLiveTravelnotePage.setBackgroundResource(R.color.transparent);
		imageViewFrame.setImageResource(R.color.transparent);
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
	
	
	public TextView getTextViewTravelnotePageContent()
	{
		return textViewTravelnotePageContent;
	}
	
	public ViewGroup getLayoutAudienceLiveTravelnotePage()
	{
		return layoutAudienceLiveTravelnotePage;
	}
	
	public int getCurrentThemePosition()
	{
		return currentThemePosition;
	}
	
	public void setCurrentThemePosition(int currentThemePosition)
	{
		this.currentThemePosition = currentThemePosition;
	}
	
	public String getMusicPath()
	{
		return musicPath;
	}
	
	public void setMusicPath(String musicPath)
	{
		this.musicPath = musicPath;
	}
	
	public String getResourcePath()
	{
		return resourcePath;
	}
	
	public void setResourcePath(String resourcePath)
	{
		this.resourcePath = resourcePath;
	}
	
	public TravelnotePageType getTravelnotePageType()
	{
		return travelnotePageType;
	}
	
	public void setTravelnotePageType(TravelnotePageType travelnotePageType)
	{
		this.travelnotePageType = travelnotePageType;
	}
	
	
}
