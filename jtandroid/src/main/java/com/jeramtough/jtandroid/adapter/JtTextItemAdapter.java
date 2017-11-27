package com.jeramtough.jtandroid.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 11718
 *         on 2017  November 24 Friday 11:06.
 */

public class JtTextItemAdapter extends BaseAdapter
{
	private Context context;
	private List<TextView> textViews;
	
	public JtTextItemAdapter(List<TextView> textViews)
	{
		this.textViews = textViews;
	}
	
	public JtTextItemAdapter(Context context, String[] contents)
	{
		this.context = context;
		this.textViews = new ArrayList<>();
		
		for (String content : contents)
		{
			TextView textView = new TextView(context);
			this.setTextViewStyle(textView);
			
			textView.setText(content);
			
			textViews.add(textView);
		}
	}
	
	@Override
	public int getCount()
	{
		return textViews.size();
	}
	
	@Override
	public Object getItem(int position)
	{
		return textViews.get(position);
	}
	
	@Override
	public long getItemId(int position)
	{
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return textViews.get(position);
	}
	
	public TextView getTextView(int position)
	{
		return textViews.get(position);
	}
	
	public List<TextView> getTextViews()
	{
		return textViews;
	}
	
	public void resetTextViewsStyle()
	{
		for (TextView textView : textViews)
		{
			this.setTextViewStyle(textView);
		}
	}
	
	//*****************************************
	private void setTextViewStyle(TextView textView)
	{
		AbsListView.LayoutParams params =
				new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
						AbsListView.LayoutParams.WRAP_CONTENT);
		textView.setLayoutParams(params);
		textView.setBackgroundColor(Color.WHITE);
		textView.setTextColor(Color.BLACK);
		textView.setPadding(20, 0, 0, 0);
		textView.setTextSize(18);
	}
	
}
