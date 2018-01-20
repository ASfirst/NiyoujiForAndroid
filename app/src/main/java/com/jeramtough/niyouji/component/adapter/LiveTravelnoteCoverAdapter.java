package com.jeramtough.niyouji.component.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.travelnote.LiveTravelnoteCover;
import com.jeramtough.niyouji.component.travelnote.TravelnoteResourceTypes;

import java.util.Objects;

/**
 * @author 11718
 *         on 2018  January 20 Saturday 17:59.
 */

public class LiveTravelnoteCoverAdapter extends BaseAdapter
{
	private Context context;
	private LiveTravelnoteCover[] liveTravelnoteCovers;
	
	public LiveTravelnoteCoverAdapter(Context context,
			LiveTravelnoteCover[] liveTravelnoteCovers)
	{
		this.context = context;
		this.liveTravelnoteCovers = liveTravelnoteCovers;
	}
	
	@Override
	public int getCount()
	{
		return liveTravelnoteCovers.length;
	}
	
	@Override
	public Object getItem(int position)
	{
		return liveTravelnoteCovers[position];
	}
	
	@Override
	public long getItemId(int position)
	{
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewsHolder viewsHolder;
		if (convertView == null)
		{
			viewsHolder = new ViewsHolder();
			
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.adapter_travelnote_covers, null);
			
			viewsHolder.imageViewTravelnoteCover =
					convertView.findViewById(R.id.imageView_travelnote_cover);
			viewsHolder.textViewTravelnoteTitle =
					convertView.findViewById(R.id.textView_travelnote_title);
			viewsHolder.textViewPerformerName =
					convertView.findViewById(R.id.textView_performer_name);
			viewsHolder.textViewAttentionsCount =
					convertView.findViewById(R.id.textView_attentions_count);
			
			convertView.setTag(viewsHolder);
		}
		else
		{
			viewsHolder = (ViewsHolder) convertView.getTag();
		}
		
		LiveTravelnoteCover liveTravelnoteCover = liveTravelnoteCovers[position];
		viewsHolder.textViewPerformerName.setText(liveTravelnoteCover.getPerformerId());
		viewsHolder.textViewTravelnoteTitle.setText(liveTravelnoteCover.getTravelnoteTitle());
		viewsHolder.textViewAttentionsCount
				.setText(liveTravelnoteCover.getAttentionsCount() + "");
		
		if (Objects.equals(liveTravelnoteCover.getCoverType(),
				TravelnoteResourceTypes.IMAGE.toString()))
		{
			//load image
			Glide.with(convertView).load(liveTravelnoteCover.getCoverResourceUrl())
					.into(viewsHolder.imageViewTravelnoteCover);
		}
		else if (Objects.equals(liveTravelnoteCover.getCoverType(),
				TravelnoteResourceTypes.VIDEO.toString()))
		{
			//load video
		}
		return convertView;
	}
	
	//{{{{{{{{}}}}}}}}}}
	private class ViewsHolder
	{
		ImageView imageViewTravelnoteCover;
		TextView textViewTravelnoteTitle;
		TextView textViewPerformerName;
		TextView textViewAttentionsCount;
	}
}
