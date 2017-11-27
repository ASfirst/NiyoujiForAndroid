package com.jeramtough.jtandroid.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import com.jeramtough.jtandroid.jtlog2.P;

/**
 * @author 11718
 *         on 2017  November 27 Monday 16:14.
 */

public class JtViewPager extends ViewPager
{
	private boolean isLastPage = false;
	private boolean isDragPage = false;
	private boolean canJumpPage = true;
	
	private JumpToLastCaller jumpToLastCaller;
	
	public JtViewPager(Context context)
	{
		super(context);
		this.initResources();
	}
	
	public JtViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.initResources();
	}
	
	public void initResources()
	{
		this.addOnPageChangeListener(new OnPageChangeListener()
		{
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels)
			{
				if ((isLastPage||getChildCount()==1) && isDragPage && positionOffsetPixels == 0)
				{
					//当前页是最后一页，并且是拖动状态，并且像素偏移量为0
					if (canJumpPage)
					{
						canJumpPage = false;
						jumpToLast();
					}
				}
			}
			
			@Override
			public void onPageSelected(int position)
			{
				isLastPage = (position == getChildCount() - 1);
			}
			
			@Override
			public void onPageScrollStateChanged(int state)
			{
				isDragPage = state == 1;
			}
		});
	}
	
	public void setCanJumpPage(boolean canJumpPage)
	{
		this.canJumpPage = canJumpPage;
	}
	
	public void setJumpToLastCaller(JumpToLastCaller jumpToLastCaller)
	{
		this.jumpToLastCaller = jumpToLastCaller;
	}
	
	//************************************
	private void jumpToLast()
	{
		if (jumpToLastCaller!=null)
		{
			jumpToLastCaller.jumpToLast();
		}
	}
	
	
	//{{{{{{{{{{{{{}}}}}}}}}}}}}}}}}}
	public interface JumpToLastCaller
	{
		void jumpToLast();
	}
	
}
