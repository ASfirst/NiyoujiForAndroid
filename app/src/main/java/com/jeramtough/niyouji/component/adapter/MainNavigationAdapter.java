package com.jeramtough.niyouji.component.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.jeramtough.niyouji.controller.fragment.DiscoverFragment;
import com.jeramtough.niyouji.controller.fragment.PerformanceFragment;

/**
 * @author 11718
 *         on 2017  November 20 Monday 12:06.
 */

public class MainNavigationAdapter extends FragmentPagerAdapter
{
	private DiscoverFragment discoverFragment;
	private PerformanceFragment performanceFragment;
	
	public MainNavigationAdapter(FragmentManager fm)
	{
		super(fm);
		discoverFragment = new DiscoverFragment();
		performanceFragment = new PerformanceFragment();
	}
	
	@Override
	public Fragment getItem(int position)
	{
		if (position == 0)
		{
			return discoverFragment;
		}
		else
		{
			return performanceFragment;
		}
	}
	
	@Override
	public int getCount()
	{
		return 2;
	}
}
