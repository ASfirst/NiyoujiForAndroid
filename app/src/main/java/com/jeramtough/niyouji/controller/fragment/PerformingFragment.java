package com.jeramtough.niyouji.controller.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.travelnote.LiveTravelnoteCover;
import com.jeramtough.niyouji.business.PerformingBusiness2;
import com.jeramtough.niyouji.business.PerformingService2;
import com.jeramtough.niyouji.component.adapter.LiveTravelnoteCoverAdapter;
import com.jeramtough.pullrefreshing.PullToRefreshView;

/**
 * @author 11718
 *         on 2017  November 20 Monday 12:00.
 */

public class PerformingFragment extends AppBaseFragment
		implements PullToRefreshView.OnRefreshListener
{
	private static final int BUSINESS_CODE_OBTAIN_TRAVELNOTE_COVERS = 0;
	
	private PullToRefreshView pullToRefresh;
	private GridView gridView;
	
	@InjectService(service = PerformingService2.class)
	private PerformingBusiness2 performingBusiness2;
	
	@Override
	public int loadFragmentLayoutId()
	{
		return R.layout.fragment_performing;
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
		obtainTravelnoteCovers();
		pullToRefresh.setRefreshing(true);
	}
	
	@Override
	public void onRefresh()
	{
		obtainTravelnoteCovers();
	}
	
	@Override
	public void handleFragmentMessage(Message message)
	{
		switch (message.what)
		{
			case BUSINESS_CODE_OBTAIN_TRAVELNOTE_COVERS:
				LiveTravelnoteCover[] liveTravelnoteCovers =
						(LiveTravelnoteCover[]) message.getData()
								.getSerializable("liveTravelnoteCovers");
				if (liveTravelnoteCovers != null)
				{
					LiveTravelnoteCoverAdapter liveTravelnoteCoverAdapter =
							new LiveTravelnoteCoverAdapter(getContext(), liveTravelnoteCovers);
					
					if (liveTravelnoteCovers.length==0)
					{
						gridView.setNumColumns(1);
					}
					else
					{
						gridView.setNumColumns(2);
					}
					
					gridView.setAdapter(liveTravelnoteCoverAdapter);
					pullToRefresh.setRefreshing(false);
				}
				
				break;
		}
	}
	
	//********************************
	private void obtainTravelnoteCovers()
	{
		performingBusiness2.getTravelnoteCovers(new BusinessCaller(getFragmentHandler(),
				BUSINESS_CODE_OBTAIN_TRAVELNOTE_COVERS));
	}
	
}
