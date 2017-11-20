package com.jeramtough.niyouji.component.ui;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.jeramtough.jtandroid.jtlog2.P;
import com.jeramtough.niyouji.component.adapter.MainNavigationAdapter;

/**
 * @author 11718
 *         on 2017  November 20 Monday 13:26.
 */

public class MainNavigation
		implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener
{
	private RadioGroup radioGroupMainNavigation;
	private RadioButton radioButton0;
	private RadioButton radioButton1;
	private ViewPager viewPagerMainNavigation;
	
	public MainNavigation(FragmentManager fragmentManager, RadioGroup radioGroupMainNavigation,
			RadioButton radioButton0, RadioButton radioButton1,
			ViewPager viewPagerMainNavigation)
	{
		this.radioGroupMainNavigation = radioGroupMainNavigation;
		this.radioButton0 = radioButton0;
		this.radioButton1 = radioButton1;
		this.viewPagerMainNavigation = viewPagerMainNavigation;
		
		radioButton0.setChecked(true);
		radioButton0.setTextColor(Color.WHITE);
		radioButton1.setTextColor(0xFFE1E1E2);
		
		MainNavigationAdapter adapter = new MainNavigationAdapter(fragmentManager);
		viewPagerMainNavigation.setAdapter(adapter);
		
		radioGroupMainNavigation.setOnCheckedChangeListener(this);
		viewPagerMainNavigation.addOnPageChangeListener(this);
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		if (checkedId == radioButton0.getId())
		{
			viewPagerMainNavigation.setCurrentItem(0);
			radioButton0.setTextColor(Color.WHITE);
			radioButton1.setTextColor(0xFFE1E1E2);
		}
		else if (checkedId == radioButton1.getId())
		{
			viewPagerMainNavigation.setCurrentItem(1);
			radioButton1.setTextColor(Color.WHITE);
			radioButton0.setTextColor(0xFFE1E1E2);
		}
		
	}
	
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{
	
	}
	
	@Override
	public void onPageSelected(int position)
	{
		switch (position)
		{
			case 0:
				if (!radioButton0.isChecked())
				{
					radioButton0.setChecked(true);
				}
				else
				{
					radioButton1.setChecked(false);
				}
				break;
			case 1:
				if (!radioButton1.isChecked())
				{
					radioButton1.setChecked(true);
				}
				else
				{
					radioButton0.setChecked(false);
				}
				break;
		}
	}
	
	@Override
	public void onPageScrollStateChanged(int state)
	{
	
	}
}
