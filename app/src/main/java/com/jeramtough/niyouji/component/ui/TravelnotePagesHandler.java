package com.jeramtough.niyouji.component.ui;

import android.content.Context;
import android.widget.TextView;
import com.jeramtough.jtandroid.adapter.ViewsPagerAdapter;
import com.jeramtough.jtandroid.ui.JtViewPager;
import com.jeramtough.jtandroid.ui.springindicator.SpringIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 11718
 *         on 2017  November 27 Monday 14:01.
 */

public class TravelnotePagesHandler implements JtViewPager.JumpToLastCaller
{
	private Context context;
	
	private JtViewPager viewPager;
	private SpringIndicator indicator;
	private ViewsPagerAdapter viewsPagerAdapter;
	
	private List<LiveTravelnotePageView> liveTravelnotePageViews;
	
	public TravelnotePagesHandler(JtViewPager viewPager, SpringIndicator indicator)
	{
		this.context = viewPager.getContext();
		this.viewPager = viewPager;
		this.indicator = indicator;
		
		initResources();
	}
	
	protected void initResources()
	{
		liveTravelnotePageViews = new ArrayList<>();
		addPage();
		addPage();
		addPage();
		
		viewsPagerAdapter = new ViewsPagerAdapter(liveTravelnotePageViews);
		viewPager.setAdapter(viewsPagerAdapter);
		
		indicator.setViewPager(viewPager);
		
		List<TextView> textViews = indicator.getTabs();
		for (int i = 0; i < textViews.size(); i++)
		{
			textViews.get(i).setText("o");
		}
		
		viewPager.setJumpToLastCaller(this);
	}
	
	@Override
	public void jumpToLast()
	{
		this.addPage();
		indicator.updatePointerUI();
		List<TextView> textViews=indicator.getTabs();
		for (int i=0;i<textViews.size();i++)
		{
			TextView textView=textViews.get(i);
			textView.setText(i+"");
		}
	}
	
	
	public void addPage()
	{
		LiveTravelnotePageView liveTravelnotePageView = new LiveTravelnotePageView(context);
		liveTravelnotePageViews.add(liveTravelnotePageView);
	}
	
	public void deletePage(int position)
	{
	
	}
	
	//**********************************
	
}
