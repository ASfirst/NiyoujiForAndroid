package com.jeramtough.niyouji.component.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.ali.CameraFilter;

import java.util.ArrayList;

/**
 * @author 11718
 *         on 2017  November 22 Wednesday 20:47.
 */

public class FiltersAdapter extends BaseAdapter
{
	private Context context;
	private ArrayList<CameraFilter> cameraFilters;
	private ViewGroup[] layouts;
	
	public FiltersAdapter(Context context, ArrayList<CameraFilter> cameraFilters)
	{
		this.context = context;
		this.cameraFilters = cameraFilters;
		
		layouts = new ViewGroup[cameraFilters.size()];
		LayoutInflater inflater =
				(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for (int i = 0; i < cameraFilters.size(); i++)
		{
			CameraFilter cameraFilter = cameraFilters.get(i);
			ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.adapter_filters, null);
			ImageView imageViewFilterIcon;
			TextView textViewFilterName;
			imageViewFilterIcon = viewGroup.findViewById(R.id.imageView_filter_icon);
			textViewFilterName = viewGroup.findViewById(R.id.textView_filter_name);
			textViewFilterName.setText(cameraFilter.getName());
			
			Bitmap bitmap=BitmapFactory.decodeFile(cameraFilter.getIconPath());
			imageViewFilterIcon.setImageBitmap(bitmap);
			
			layouts[i] = viewGroup;
		}
	}
	
	@Override
	public int getCount()
	{
		return cameraFilters.size();
	}
	
	@Override
	public Object getItem(int position)
	{
		return cameraFilters.get(position);
	}
	
	@Override
	public long getItemId(int position)
	{
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return layouts[position];
	}
}
