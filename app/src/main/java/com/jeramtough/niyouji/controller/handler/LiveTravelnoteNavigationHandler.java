package com.jeramtough.niyouji.controller.handler;

import android.app.Activity;
import android.graphics.Color;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jeramtough.jtandroid.adapter.ViewsPagerAdapter;
import com.jeramtough.jtandroid.controller.handler.JtBaseHandler;
import com.jeramtough.jtandroid.jtlog2.P;
import com.jeramtough.jtandroid.ui.JtViewPager;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.ui.travelnote.LiveTravelnotePage;

import java.util.ArrayList;

/**
 * @author 11718
 *         on 2017  November 30 Thursday 14:45.
 */

public class LiveTravelnoteNavigationHandler extends JtBaseHandler
		implements ViewPager.OnPageChangeListener
{
	public final static int ACTIVATE_ACTION = 0X1;
	public final static int DELETE_ACTION = 0X2;
	
	private JtViewPager viewPagerTravelnotePages;
	private TextView textViewAttentionsCount;
	private TextView textViewPagesCount;
	private TextView textViewAudiencesCount;
	private LinearLayout layoutShutdownForLive;
	
	private ArrayList<View> liveTravelnotePageViews;
	
	public LiveTravelnoteNavigationHandler(Activity activity)
	{
		super(activity);
		viewPagerTravelnotePages = findViewById(R.id.viewPager_travelnote_pages);
		textViewAttentionsCount = findViewById(R.id.textView_attentions_count);
		textViewPagesCount = findViewById(R.id.textView_pages_count);
		textViewAudiencesCount = findViewById(R.id.textView_audiences_count);
		layoutShutdownForLive = findViewById(R.id.layout_shutdown_for_live);
		
		viewPagerTravelnotePages.addOnPageChangeListener(this);
		
		this.initResources();
	}
	
	protected void initResources()
	{
		liveTravelnotePageViews = new ArrayList<>();
		
		this.addPageViewToList();
		
		ViewsPagerAdapter adapter = new ViewsPagerAdapter(liveTravelnotePageViews);
		viewPagerTravelnotePages.setAdapter(adapter);
	}
	
	@Override
	public void handleMessage(Message msg)
	{
		switch (msg.what)
		{
			case ACTIVATE_ACTION:
				layoutShutdownForLive.setVisibility(View.INVISIBLE);
				
				this.addPageViewToList();
				this.updatePagesCount();
				
				this.updateViewPagerUI(false);
				break;
			
			case DELETE_ACTION:
				/*P.debug(viewPagerTravelnotePages.getCurrentItem(),
						liveTravelnotePageViews.size());*/
				if (viewPagerTravelnotePages.getCurrentItem() < liveTravelnotePageViews.size())
				{
					liveTravelnotePageViews.remove(viewPagerTravelnotePages.getCurrentItem());
					this.updateViewPagerUI(true);
					this.updatePagesCount();
				}
				
				break;
		}
	}
	
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{
	
	}
	
	@Override
	public void onPageSelected(int position)
	{
		if (position == liveTravelnotePageViews.size() - 1)
		{
			layoutShutdownForLive.setVisibility(View.VISIBLE);
		}
		else
		{
			if (layoutShutdownForLive.getVisibility() == View.VISIBLE)
			{
				layoutShutdownForLive.setVisibility(View.INVISIBLE);
			}
		}
		updatePagesCount();
		
		
	}
	
	@Override
	public void onPageScrollStateChanged(int state)
	{
	
	}
	
	//************************************
	private void addPageViewToList()
	{
		LiveTravelnotePage page = new LiveTravelnotePage(getContext(), this);
		liveTravelnotePageViews.add(page);
	}
	
	private void updateViewPagerUI(boolean isForceUpdateMode)
	{
		if (isForceUpdateMode == true)
		{
			ViewsPagerAdapter adapter =
					(ViewsPagerAdapter) viewPagerTravelnotePages.getAdapter();
			adapter.setForceUpdateMode(true);
			viewPagerTravelnotePages.getAdapter().notifyDataSetChanged();
			adapter.setForceUpdateMode(false);
		}
		else
		{
			viewPagerTravelnotePages.getAdapter().notifyDataSetChanged();
		}
	}
	
	private void updatePagesCount()
	{
		int position = viewPagerTravelnotePages.getCurrentItem();
		if (position == liveTravelnotePageViews.size() - 1)
		{
			textViewPagesCount.setText("-/" + (liveTravelnotePageViews.size() - 1));
		}
		else
		{
			textViewPagesCount
					.setText((position + 1) + "/" + (liveTravelnotePageViews.size() - 1));
		}
	}
}
