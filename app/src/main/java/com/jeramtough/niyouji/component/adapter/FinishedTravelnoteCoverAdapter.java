package com.jeramtough.niyouji.component.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.travelnote.FinishedTravelnoteCover;
import com.jeramtough.niyouji.bean.travelnote.Travelnote;
import com.jeramtough.niyouji.component.app.GlideApp;
import com.jeramtough.niyouji.component.travelnote.TravelnotePageType;

import java.util.ArrayList;

/**
 * @author 11718
 *         on 2018  January 20 Saturday 17:59.
 */

public class FinishedTravelnoteCoverAdapter extends BaseAdapter
{
	private Context context;
	private ArrayList<FinishedTravelnoteCover> finishedTravelnoteCovers;
	
	public FinishedTravelnoteCoverAdapter(Context context)
	{
		this.context = context;
		this.finishedTravelnoteCovers = new ArrayList<>();
	}
	
	@Override
	public int getCount()
	{
		return finishedTravelnoteCovers.size();
	}
	
	@Override
	public Object getItem(int position)
	{
		return finishedTravelnoteCovers.get(position);
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
					.inflate(R.layout.adapter_finished_travelnote_covers, null);
			
			viewsHolder.textViewDate = convertView.findViewById(R.id.textView_date);
			viewsHolder.textViewTravelnoteTitle =
					convertView.findViewById(R.id.textView_travelnote_title);
			viewsHolder.imageViewFirstPage =
					convertView.findViewById(R.id.imageView_first_page);
			viewsHolder.textViewFirstPageTime =
					convertView.findViewById(R.id.textView_first_page_time);
			viewsHolder.textViewFirstPageContent =
					convertView.findViewById(R.id.textView_first_page_content);
			viewsHolder.imageViewSecond = convertView.findViewById(R.id.imageView_second);
			viewsHolder.textViewSecondPageTime =
					convertView.findViewById(R.id.textView_second_page_time);
			viewsHolder.textViewSecondPageContent =
					convertView.findViewById(R.id.textView_second_page_content);
			viewsHolder.textViewPerformerName =
					convertView.findViewById(R.id.textView_performer_name);
			viewsHolder.textViewAttentionsCount =
					convertView.findViewById(R.id.textView_attentions_count);
			viewsHolder.imageViewTravelnoteCover =
					convertView.findViewById(R.id.imageView_travelnote_cover);
			
			convertView.setTag(viewsHolder);
		}
		else
		{
			viewsHolder = (ViewsHolder) convertView.getTag();
		}
		
		FinishedTravelnoteCover finishedTravelnoteCover =
				finishedTravelnoteCovers.get(position);
		viewsHolder.textViewAttentionsCount
				.setText(finishedTravelnoteCover.getAttentionsCount() + "");
		viewsHolder.textViewDate
				.setText(finishedTravelnoteCover.getCreateTime().substring(0, 10));
		viewsHolder.textViewTravelnoteTitle
				.setText(finishedTravelnoteCover.getTravelnoteTitle());
		viewsHolder.textViewPerformerName
				.setText(finishedTravelnoteCover.getPerformerNickname());
		
		GlideApp.with(context).load(finishedTravelnoteCover.getCoverResourceUrl())
				.skipMemoryCache(true).placeholder(R.drawable.ic_image_green)
				.error(R.drawable.ic_broken_image).centerCrop()
				.into(viewsHolder.imageViewTravelnoteCover);
		
		if (finishedTravelnoteCover.getFirstTravelnotePage() != null)
		{
			viewsHolder.textViewFirstPageTime.setText(
					finishedTravelnoteCover.getFirstTravelnotePage().getCreateTime()
							.substring(10, 16));
			viewsHolder.textViewFirstPageContent.setText(
					finishedTravelnoteCover.getFirstTravelnotePage().getTextContent());
			
			if (finishedTravelnoteCover.getFirstTravelnotePage().getPageType()
					.equals(TravelnotePageType.PICANDWORD.toString()))
			{
				GlideApp.with(context).load(finishedTravelnoteCover.getFirstTravelnotePage()
						.getResourceUrl()).skipMemoryCache(true)
						.placeholder(R.drawable.ic_image_green)
						.error(R.drawable.ic_broken_image).centerCrop()
						.into(viewsHolder.imageViewFirstPage);
			}
			else
			{
				GlideApp.with(context).asGif().load(R.drawable.video_instance)
						.skipMemoryCache(true).placeholder(R.drawable.ic_image_green)
						.error(R.drawable.ic_broken_image).centerCrop()
						.into(viewsHolder.imageViewFirstPage);
			}
			
		}
		if (finishedTravelnoteCover.getSecondTravelnotePage() != null)
		{
			viewsHolder.textViewSecondPageTime.setText(
					finishedTravelnoteCover.getSecondTravelnotePage().getCreateTime()
							.substring(10, 16));
			viewsHolder.textViewSecondPageContent.setText(
					finishedTravelnoteCover.getSecondTravelnotePage().getTextContent());
			
			if (finishedTravelnoteCover.getSecondTravelnotePage().getPageType()
					.equals(TravelnotePageType.PICANDWORD.toString()))
			{
				GlideApp.with(context).load(finishedTravelnoteCover.getSecondTravelnotePage()
						.getResourceUrl()).skipMemoryCache(true)
						.placeholder(R.drawable.ic_image_green)
						.error(R.drawable.ic_broken_image).centerCrop()
						.into(viewsHolder.imageViewSecond);
			}
			else
			{
				GlideApp.with(context).asGif().load(R.drawable.video_instance)
						.skipMemoryCache(true).placeholder(R.drawable.ic_image_green)
						.error(R.drawable.ic_broken_image).centerCrop()
						.into(viewsHolder.imageViewSecond);
			}
		}
		return convertView;
	}
	
	
	public void addFinishedTravelnoteCovers(
			ArrayList<FinishedTravelnoteCover> finishedTravelnoteCovers)
	{
		this.finishedTravelnoteCovers.addAll(finishedTravelnoteCovers);
	}
	
	public void clearAllDataSource()
	{
		finishedTravelnoteCovers.clear();
	}
	
	//{{{{{{{{}}}}}}}}}}
	private class ViewsHolder
	{
		TextView textViewDate;
		TextView textViewTravelnoteTitle;
		AppCompatImageView imageViewFirstPage;
		TextView textViewFirstPageTime;
		TextView textViewFirstPageContent;
		AppCompatImageView imageViewSecond;
		TextView textViewSecondPageTime;
		TextView textViewSecondPageContent;
		TextView textViewPerformerName;
		TextView textViewAttentionsCount;
		AppCompatImageView imageViewTravelnoteCover;
	}
}
