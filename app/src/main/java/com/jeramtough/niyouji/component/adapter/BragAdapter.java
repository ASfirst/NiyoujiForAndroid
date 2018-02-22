package com.jeramtough.niyouji.component.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.brag.AppBrag;

import java.util.ArrayList;

/**
 * @author 11718
 *         on 2018  February 22 Thursday 16:56.
 */

public class BragAdapter extends PagerAdapter
{
	private Context context;
	private ArrayList<View> views;
	private GoToAppIndexCaller goToAppIndexCaller;
	
	public BragAdapter(Context context)
	{
		this.context = context;
		views = new ArrayList<>();
		
		initResources();
	}
	
	protected void initResources()
	{
		ArrayList<AppBrag> appBrags = new ArrayList<>();
		for (int i = 0; i < 4; i++)
		{
			AppBrag appBrag = new AppBrag();
			switch (i)
			{
				case 0:
					appBrag.setPosition(0);
					appBrag.setImageResId(R.mipmap.brag1);
					break;
				case 1:
					appBrag.setPosition(1);
					appBrag.setImageResId(R.mipmap.brge2);
					break;
				case 2:
					appBrag.setPosition(1);
					appBrag.setImageResId(R.mipmap.brge3);
					break;
				case 3:
					appBrag.setPosition(2);
					appBrag.setImageResId(R.mipmap.brge4);
					break;
			}
			
			ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context)
					.inflate(R.layout.adapter_brags, null);
			
			viewGroup.setBackgroundResource(appBrag.getImageResId());
			
			View viewLeftLine = viewGroup.findViewById(R.id.view_left_line);
			Button buttonStartApp = viewGroup.findViewById(R.id.button_start_app);
			View viewRightLine = viewGroup.findViewById(R.id.view_right_line);
			
			
			if (appBrag.getPosition() == 0)
			{
				viewLeftLine.setVisibility(View.INVISIBLE);
			}
			else if (appBrag.getPosition() == 2)
			{
				viewRightLine.setVisibility(View.INVISIBLE);
				buttonStartApp.setVisibility(View.VISIBLE);
				buttonStartApp.setOnClickListener(v ->
				{
					if (goToAppIndexCaller != null)
					{
						goToAppIndexCaller.goToIndex();
					}
				});
			}
			
			
			views.add(viewGroup);
		}
		
	}
	
	@Override
	public int getCount()
	{
		return views.size();
	}
	
	@Override
	public boolean isViewFromObject(View view, Object object)
	{
		return view == object;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		container.addView(views.get(position));
		return views.get(position);
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		container.removeView(views.get(position));
	}
	
	public void setGoToAppIndexCaller(GoToAppIndexCaller goToAppIndexCaller)
	{
		this.goToAppIndexCaller = goToAppIndexCaller;
	}
	
	//{{{{{{{{{{{{{}}}}}}}}}}}}}}}}}}
	public interface GoToAppIndexCaller
	{
		void goToIndex();
	}
}
