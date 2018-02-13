package com.jeramtough.niyouji.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.*;
import com.jeramtough.jtandroid.adapter.JtTextItemAdapter;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.listener.OnScreenBottomOrTopListener;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.travelnote.FinishedTravelnoteCover;
import com.jeramtough.niyouji.business.TravelnoteBusiness;
import com.jeramtough.niyouji.business.TravelnoteService;
import com.jeramtough.niyouji.component.adapter.FinishedTravelnoteCoverAdapter;
import com.jeramtough.niyouji.controller.activity.FinishedTravelnoteActivity;
import com.jeramtough.pullrefreshing.PullToRefreshView;

import java.util.ArrayList;

/**
 * @author 11718
 *         on 2017  November 20 Monday 12:00.
 */

public class DiscoverFragment extends AppBaseFragment
		implements PullToRefreshView.OnRefreshListener, AdapterView.OnItemClickListener
{
	private static final int BUSINESS_CODE_OBTAIN_FINISHED_TRAVELNOTE_COVERS = 0;
	private static final int BUSINESS_CODE_OBTAIN_MORE_FINISHED_TRAVELNOTE_COVERS = 1;
	
	private PullToRefreshView pullToRefresh;
	private ListView listViewTravelnotes;
	private TimedCloseTextView timedCloseTextView;
	private LinearLayout layoutProgress;
	private TextView textViewNoMoreTravelnote;
	
	private FinishedTravelnoteCoverAdapter finishedTravelnoteCoverAdapter;
	
	@InjectService(service = TravelnoteService.class)
	private TravelnoteBusiness travelnoteBusiness;
	
	private String endTravelnoteId;
	private boolean isObtaining = false;
	
	@Override
	public int loadFragmentLayoutId()
	{
		return R.layout.fragment_discover;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		//getIocContainer().injectView(getContext(), this);
		pullToRefresh = findViewById(R.id.pull_to_refresh);
		listViewTravelnotes = findViewById(R.id.listView_travelnotes);
		timedCloseTextView = findViewById(R.id.timedCloseTextView);
		layoutProgress = findViewById(R.id.layout_progress);
		textViewNoMoreTravelnote = findViewById(R.id.textView_no_more_travelnote);
		
		layoutProgress.setVisibility(View.GONE);
		textViewNoMoreTravelnote.setVisibility(View.GONE);
		
		pullToRefresh.setOnRefreshListener(this);
		listViewTravelnotes.setOnItemClickListener(this);
		listViewTravelnotes.setOnScrollListener(new MyScreenBottomOrTopListener());
		initResources();
	}
	
	protected void initResources()
	{
		finishedTravelnoteCoverAdapter = new FinishedTravelnoteCoverAdapter(getActivity());
		listViewTravelnotes.setAdapter(finishedTravelnoteCoverAdapter);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		obtainFinishedTravelnoteCovers();
	}
	
	@Override
	public void onRefresh()
	{
		obtainFinishedTravelnoteCovers();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		FinishedTravelnoteCover finishedTravelnoteCover =
				(FinishedTravelnoteCover) finishedTravelnoteCoverAdapter.getItem(position);
		Intent intent = new Intent(this.getContext(), FinishedTravelnoteActivity.class);
		intent.putExtra("finishedTravelnoteCover", finishedTravelnoteCover);
		startActivity(intent);
	}
	
	public class MyScreenBottomOrTopListener extends OnScreenBottomOrTopListener
	{
		@Override
		public void onScreenBottom()
		{
			if (!isObtaining)
			{
				layoutProgress.setVisibility(View.VISIBLE);
				isObtaining = true;
				obtainMoreFinishedTravelnoteCovers();
			}
		}
	}
	
	@Override
	public void handleFragmentMessage(Message message)
	{
		switch (message.what)
		{
			case BUSINESS_CODE_OBTAIN_FINISHED_TRAVELNOTE_COVERS:
				finishedTravelnoteCoverAdapter.clearAllDataSource();
			
			case BUSINESS_CODE_OBTAIN_MORE_FINISHED_TRAVELNOTE_COVERS:
				if (message.getData().getBoolean(BusinessCaller.IS_SUCCESSFUL))
				{
					ArrayList<FinishedTravelnoteCover> finishedTravelnoteCovers =
							(ArrayList<FinishedTravelnoteCover>) message.getData()
									.getSerializable("finishedTravelnoteCovers");
					
					if (finishedTravelnoteCovers.size() > 0)
					{
						endTravelnoteId = finishedTravelnoteCovers
								.get(finishedTravelnoteCovers.size() - 1).getTravelnoteId();
						
						finishedTravelnoteCoverAdapter
								.addFinishedTravelnoteCovers(finishedTravelnoteCovers);
						finishedTravelnoteCoverAdapter.notifyDataSetChanged();
						
					}
					else
					{
						//暂时没有更多了
						textViewNoMoreTravelnote.setVisibility(View.VISIBLE);
						textViewNoMoreTravelnote.postDelayed(() ->
						{
							if (textViewNoMoreTravelnote.getVisibility() == View.VISIBLE)
							{
								textViewNoMoreTravelnote.setVisibility(View.GONE);
							}
						}, 2000);
					}
				}
				else
				{
					timedCloseTextView.setErrorMessage("获取失败！");
					timedCloseTextView.closeDelayed(3000);
				}
				isObtaining = false;
				pullToRefresh.setRefreshing(false);
				layoutProgress.setVisibility(View.GONE);
				
				break;
		}
	}
	
	//********************************
	private void obtainFinishedTravelnoteCovers()
	{
		if (travelnoteBusiness.checkTheNetwork(this.getContext()))
		{
			travelnoteBusiness.getFinishedTravelnoteCovers(
					new BusinessCaller(getFragmentHandler(),
							BUSINESS_CODE_OBTAIN_FINISHED_TRAVELNOTE_COVERS));
			pullToRefresh.setRefreshing(true);
		}
		else
		{
			timedCloseTextView.setErrorMessage("目前没有可用网络！");
			timedCloseTextView.closeDelayed(3000);
			pullToRefresh.setRefreshing(false);
		}
	}
	
	private void obtainMoreFinishedTravelnoteCovers()
	{
		if (travelnoteBusiness.checkTheNetwork(this.getContext()))
		{
			travelnoteBusiness.getMoreFinishedTravelnoteCovers(
					new BusinessCaller(getFragmentHandler(),
							BUSINESS_CODE_OBTAIN_MORE_FINISHED_TRAVELNOTE_COVERS),
					endTravelnoteId);
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
