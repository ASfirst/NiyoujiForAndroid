package com.jeramtough.niyouji.component.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.travelnote.LiveTravelnoteCover;
import com.jeramtough.niyouji.component.app.GlideApp;
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
		return liveTravelnoteCovers.length == 0 ? 1 : liveTravelnoteCovers.length;
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
			viewsHolder.viewNoLiveTravelnote =
					convertView.findViewById(R.id.view_no_live_travelnote);
			
			convertView.setTag(viewsHolder);
		}
		else
		{
			viewsHolder = (ViewsHolder) convertView.getTag();
		}
		
		if (liveTravelnoteCovers.length != 0)
		{
			//移除提示
			((FrameLayout) convertView).removeView(viewsHolder.viewNoLiveTravelnote);
			
			LiveTravelnoteCover liveTravelnoteCover = liveTravelnoteCovers[position];
			viewsHolder.textViewPerformerName
					.setText(liveTravelnoteCover.getPerformerNickname());
			viewsHolder.textViewTravelnoteTitle
					.setText(liveTravelnoteCover.getTravelnoteTitle());
			viewsHolder.textViewAttentionsCount
					.setText(liveTravelnoteCover.getAttentionsCount() + "");
			
			if (Objects.equals(liveTravelnoteCover.getCoverType(),
					TravelnoteResourceTypes.IMAGE.toString()))
			{
				//load image
				GlideApp.with(context).load(liveTravelnoteCover.getCoverResourceUrl())
						.skipMemoryCache(true).placeholder(R.drawable.ic_image_green)
						.error(R.drawable.ic_broken_image).centerCrop()
						.into(viewsHolder.imageViewTravelnoteCover);
			}
			else if (Objects.equals(liveTravelnoteCover.getCoverType(),
					TravelnoteResourceTypes.VIDEO.toString()))
			{
				//load video
				//估计是用不上了，不想做视频封面了
			}
		}
		else
		{
			viewsHolder.viewNoLiveTravelnote.setVisibility(View.VISIBLE);
		}
		return convertView;
	}
	
	//{{{{{{{{}}}}}}}}}}
	private class ViewsHolder
	{
		AppCompatImageView imageViewTravelnoteCover;
		TextView textViewTravelnoteTitle;
		TextView textViewPerformerName;
		TextView textViewAttentionsCount;
		View viewNoLiveTravelnote;
	}
}
