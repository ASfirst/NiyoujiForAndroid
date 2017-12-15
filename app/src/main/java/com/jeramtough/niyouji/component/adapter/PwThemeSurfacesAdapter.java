package com.jeramtough.niyouji.component.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.picandword.PwResourcePosition;

/**
 * @author 11718 on 2017 December 11 Monday 12:12.
 */
public class PwThemeSurfacesAdapter extends BaseAdapter
{
	private Context context;
	private List<PwResourcePosition> pwResourcePositions;
	
	public PwThemeSurfacesAdapter(Context context,
			List<PwResourcePosition> pwResourcePositions)
	{
		super();
		this.context = context;
		this.pwResourcePositions = pwResourcePositions;
	}
	
	@Override
	public int getCount()
	{
		return pwResourcePositions.size();
	}
	
	@Override
	public Object getItem(int position)
	{
		return pwResourcePositions.get(position);
	}
	
	@Override
	public long getItemId(int position)
	{
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder;
		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			convertView = layoutInflater.inflate(R.layout.adapter_pwtheme, null);
			viewHolder.imageViewSurface = convertView.findViewById(R.id.imageView_surface);
			viewHolder.textViewThemeName = convertView.findViewById(R.id.textView_theme_name);
			
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		PwResourcePosition pwResourcePosition = pwResourcePositions.get(position);
		viewHolder.imageViewSurface
				.setImageBitmap(BitmapFactory.decodeFile(pwResourcePosition.getSurfacePath()));
		viewHolder.textViewThemeName.setText(pwResourcePosition.getThemeName());
		
		return convertView;
	}
	
	// {{{{{{{{}}}}}}}}}
	class ViewHolder
	{
		ImageView imageViewSurface;
		TextView textViewThemeName;
	}
}
