package com.jeramtough.niyouji.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.jeramtough.jtandroid.adapter.ViewsAdapter;
import com.jeramtough.niyouji.R;
import com.jeramtough.pullrefreshing.PullToRefreshView;

import java.util.ArrayList;

/**
 * @author 11718
 *         on 2017  November 20 Monday 12:00.
 */

public class DiscoverFragment extends AppBaseFragment
		implements PullToRefreshView.OnRefreshListener
{
	private PullToRefreshView pullToRefresh;
	private GridView gridView;
	
	
	@Override
	public int loadFragmentLayoutId()
	{
		return R.layout.fragment_discover;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		pullToRefresh = findViewById(R.id.pull_to_refresh);
		gridView = findViewById(R.id.gridView);
		
		pullToRefresh.setOnRefreshListener(this);
		
		initResources();
	}
	
	protected void initResources()
	{
		ArrayList<View> views = new ArrayList<>();
		for (int i = 0; i < 8; i++)
		{
			ViewGroup viewGroup = (ViewGroup) getActivity().getLayoutInflater()
					.inflate(R.layout.adapter_travelnote_covers, null);
			ImageView imageViewTravelnoteCover =
					viewGroup.findViewById(R.id.imageView_travelnote_cover);
			int random=(int)(Math.random()*3);
			switch (random)
			{
				case 0:
					imageViewTravelnoteCover.setBackgroundResource(R.mipmap.temp);
					break;
				case 1:
					imageViewTravelnoteCover.setBackgroundResource(R.mipmap.temp1);
					break;
				case 2:
					imageViewTravelnoteCover.setBackgroundResource(R.mipmap.temp2);
					break;
			}
			
			views.add(viewGroup);
		}
		ViewsAdapter adapter = new ViewsAdapter(views);
		ArrayAdapter<String> adapter1 =
				new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,
						new String[]{"a", "b", "c", "d", "e", "f"});
		gridView.setAdapter(adapter);
	}
	
	@Override
	public void onRefresh()
	{
		pullToRefresh.postDelayed(() ->
		{
			initResources();
			pullToRefresh.setRefreshing(false);
			
		}, 2000);
	}
	
	//***********************************************
}
