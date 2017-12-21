package com.jeramtough.jtemoji;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * @author JeramTough
 *         on 2017  December 20 Wednesday 23:55.
 */

public class JtEmojisAdapter extends BaseAdapter
{
	private Context context;
	private ArrayList<JtEmoji> jtEmojis;
	
	public JtEmojisAdapter(Context context, ArrayList<JtEmoji> jtEmojis)
	{
		this.context = context;
		this.jtEmojis = jtEmojis;
	}
	
	@Override
	public int getCount()
	{
		return jtEmojis.size();
	}
	
	@Override
	public Object getItem(int position)
	{
		return jtEmojis.get(position);
	}
	
	@Override
	public long getItemId(int position)
	{
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewsHolder viewsHolder = null;
		if (convertView == null)
		{
			viewsHolder = new ViewsHolder();
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			convertView = layoutInflater.inflate(R.layout.adapter_emoji, null);
			
			viewsHolder.imageView = convertView.findViewById(R.id.imageView_emoji);
			
			convertView.setTag(viewsHolder);
		}
		viewsHolder = (ViewsHolder) convertView.getTag();
		
		JtEmoji jtEmoji = jtEmojis.get(position);
		viewsHolder.imageView.setImageResource(jtEmoji.getImageResId());
		
		return convertView;
	}
	
	
	//{{{{{{{{{{{{{}}}}}}}}
	class ViewsHolder
	{
		private ImageView imageView;
	}
}
