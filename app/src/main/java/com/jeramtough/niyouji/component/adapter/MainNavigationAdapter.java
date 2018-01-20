package com.jeramtough.niyouji.component.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.jeramtough.niyouji.controller.fragment.DiscoverFragment;
import com.jeramtough.niyouji.controller.fragment.PerformingFragment;

/**
 * @author 11718
 *         on 2017  November 20 Monday 12:06.
 */

public class MainNavigationAdapter extends FragmentPagerAdapter
{
	private DiscoverFragment discoverFragment;
	private PerformingFragment performingFragment;
	
	public MainNavigationAdapter(FragmentManager fm)
	{
		super(fm);
		discoverFragment = new DiscoverFragment();
		performingFragment = new PerformingFragment();
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
			return performingFragment;
		}
	}
	
	@Override
	public int getCount()
	{
		return 2;
	}
}
