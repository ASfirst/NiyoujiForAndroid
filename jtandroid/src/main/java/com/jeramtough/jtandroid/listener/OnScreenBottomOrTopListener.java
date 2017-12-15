package com.jeramtough.jtandroid.listener;

import android.view.View;
import android.widget.AbsListView;

/**
 * @author 11718
 */
public abstract class OnScreenBottomOrTopListener implements AbsListView.OnScrollListener
{
	
	private boolean isStart = false;
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
			int totalItemCount)
	{
		
		if (firstVisibleItem == 0)
		{
			View firstVisibleItemView = view.getChildAt(0);
			if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0)
			{
				
				if (isStart)
				{
					onScreenTop();
				}
				
			}
		}
		else if ((firstVisibleItem + visibleItemCount) == totalItemCount)
		{
			View lastVisibleItemView = view.getChildAt(view.getChildCount() - 1);
			if (lastVisibleItemView != null &&
					lastVisibleItemView.getBottom() == view.getHeight())
			{
				
				onScreenBottom();
			}
		}
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		switch (scrollState)
		{
			case SCROLL_STATE_IDLE:
				if (isStart == false)
				{
					isStart = true;
				}
				break;
			
			default:
				break;
		}
	}
	
	public abstract void onScreenBottom();
	
	public abstract void onScreenTop();
	
}
