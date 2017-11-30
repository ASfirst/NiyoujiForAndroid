package com.jeramtough.niyouji.component.ui.travelnote;

import android.content.Context;
import android.view.View;
import com.jeramtough.jtandroid.adapter.ViewsPagerAdapter;
import com.jeramtough.jtandroid.ui.JtViewPager;

import java.util.ArrayList;

/**
 * @author 11718
 *         on 2017  November 29 Wednesday 20:25.
 */

public class LiveTravelnotePageHandler
{
	private Context context;
	private JtViewPager jtViewPager;
	
	private ArrayList<View> liveTravelnotePages;
	
	public LiveTravelnotePageHandler(Context context, JtViewPager jtViewPager)
	{
		this.context = context;
		this.jtViewPager = jtViewPager;
		
		liveTravelnotePages = new ArrayList<>();
		
		initResources();
	}
	
	protected void initResources()
	{
		this.addPicandwordPageViewToList();
		this.addPicandwordPageViewToList();
		ViewsPagerAdapter adapter = new ViewsPagerAdapter(liveTravelnotePages);
		jtViewPager.setAdapter(adapter);
	}
	
	public void addPicandwordPage()
	{
	
	}
	
	public void addVideoPage()
	{
	
	}
	
	//************************************
	private void addPicandwordPageViewToList()
	{
		LiveTravelnotePicandwrodPageView picandwrodPageView =
				new LiveTravelnotePicandwrodPageView(context);
		liveTravelnotePages.add(picandwrodPageView);
	}
}
