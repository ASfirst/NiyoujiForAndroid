package com.jeramtough.jtandroid.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 11718
 *         on 2017  November 27 Monday 15:02.
 */

public class ViewsAdapter extends BaseAdapter
{
	private List<? extends View> views;
	
	public ViewsAdapter(List<? extends View> views)
	{
		this.views = views;
	}
	
	
	@Override
	public int getCount()
	{
		return views.size();
	}
	
	@Override
	public Object getItem(int position)
	{
		return views.get(position);
	}
	
	@Override
	public long getItemId(int position)
	{
		return views.get(position).hashCode();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return views.get(position);
	}
}
