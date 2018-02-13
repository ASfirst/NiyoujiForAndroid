package com.jeramtough.niyouji.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.travelnote.LiveTravelnoteCover;
import com.jeramtough.niyouji.business.TravelnoteBusiness;
import com.jeramtough.niyouji.business.TravelnoteService;
import com.jeramtough.niyouji.component.adapter.LiveTravelnoteCoverAdapter;
import com.jeramtough.niyouji.controller.activity.AudienceActivity;
import com.jeramtough.pullrefreshing.PullToRefreshView;

/**
 * @author 11718
 *         on 2017  November 20 Monday 12:00.
 */

public class PerformingFragment extends AppBaseFragment
		implements PullToRefreshView.OnRefreshListener, AdapterView.OnItemClickListener
{
	private static final int BUSINESS_CODE_OBTAIN_TRAVELNOTE_COVERS = 0;
	
	private PullToRefreshView pullToRefresh;
	private GridView gridView;
	private TimedCloseTextView timedCloseTextView;
	
	private boolean hasLiveTravelnote = false;
	
	@InjectService(service = TravelnoteService.class)
	private TravelnoteBusiness travelnoteBusiness;
	
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
		timedCloseTextView = findViewById(R.id.timedCloseTextView);
		
		pullToRefresh.setOnRefreshListener(this);
		gridView.setOnItemClickListener(this);
		
		initResources();
	}
	
	protected void initResources()
	{
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		obtainTravelnoteCovers();
	}
	
	@Override
	public void onRefresh()
	{
		obtainTravelnoteCovers();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		if (hasLiveTravelnote)
		{
			LiveTravelnoteCoverAdapter adapter =
					(LiveTravelnoteCoverAdapter) parent.getAdapter();
			LiveTravelnoteCover liveTravelnoteCover =
					(LiveTravelnoteCover) adapter.getItem(position);
			
			Intent intent = new Intent(getActivity(), AudienceActivity.class);
			intent.putExtra("liveTravelnoteCover", liveTravelnoteCover);
			getActivity().startActivity(intent);
		}
	}
	
	@Override
	public void handleFragmentMessage(Message message)
	{
		switch (message.what)
		{
			case BUSINESS_CODE_OBTAIN_TRAVELNOTE_COVERS:
				boolean isSuccessful = message.getData().getBoolean("isSuccessful");
				if (isSuccessful)
				{
					LiveTravelnoteCover[] liveTravelnoteCovers =
							(LiveTravelnoteCover[]) message.getData()
									.getSerializable("liveTravelnoteCovers");
					if (liveTravelnoteCovers != null)
					{
						LiveTravelnoteCoverAdapter liveTravelnoteCoverAdapter =
								new LiveTravelnoteCoverAdapter(getContext(),
										liveTravelnoteCovers);
						
						if (liveTravelnoteCovers.length == 0)
						{
							gridView.setNumColumns(1);
							hasLiveTravelnote = false;
						}
						else
						{
							gridView.setNumColumns(2);
							hasLiveTravelnote = true;
						}
						
						gridView.setAdapter(liveTravelnoteCoverAdapter);
						pullToRefresh.setRefreshing(false);
					}
				}
				else
				{
					pullToRefresh.setRefreshing(false);
					timedCloseTextView.setErrorMessage("拉取资源失败！请稍后重试");
					timedCloseTextView.closeDelayed(3000);
				}
				
				break;
		}
	}
	
	//********************************
	private void obtainTravelnoteCovers()
	{
		if (travelnoteBusiness.checkTheNetwork(this.getContext()))
		{
			travelnoteBusiness.getLiveTravelnoteCovers(new BusinessCaller(getFragmentHandler(),
					BUSINESS_CODE_OBTAIN_TRAVELNOTE_COVERS));
			pullToRefresh.setRefreshing(true);
		}
		else
		{
			timedCloseTextView.setErrorMessage("目前没有可用网络！");
			timedCloseTextView.closeDelayed(3000);
			pullToRefresh.setRefreshing(false);
		}
	}
	
	
}
