package com.jeramtough.jtandroid.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author 11718
 *         on 2017  November 27 Monday 15:17.
 */

public class ViewsPagerAdapter extends PagerAdapter
{
	private List<? extends View> views;
	private boolean isForceUpdateMode = false;
	
	public ViewsPagerAdapter(List<? extends View> views)
	{
		this.views = views;
	}
	
	@Override
	public int getCount()
	{
		return views.size();
	}
	
	@Override
	public boolean isViewFromObject(View view, Object object)
	{
		return view == object;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		container.addView(views.get(position));
		return views.get(position);
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		/*if (position < views.size())
		{
			container.removeView(views.get(position));
		}*/
		if (object!=null)
		{
			View view = (View) object;
			container.removeView(view);
		}
	}
	
	@Override
	public int getItemPosition(Object object)
	{
		if (isForceUpdateMode)
		{
			return POSITION_NONE;
		}
		else
		{
			return POSITION_UNCHANGED;
		}
	}
	
	public boolean isForceUpdateMode()
	{
		return isForceUpdateMode;
	}
	
	public void setForceUpdateMode(boolean forceUpdateMode)
	{
		isForceUpdateMode = forceUpdateMode;
	}
}
