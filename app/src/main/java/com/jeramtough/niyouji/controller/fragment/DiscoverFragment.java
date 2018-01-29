package com.jeramtough.niyouji.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import com.jeramtough.jtandroid.adapter.ViewsAdapter;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectView;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.jtlog3.P;
import com.jeramtough.jtutil.DateTimeUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.CreatePerformingRoomCommand;
import com.jeramtough.niyouji.component.app.AppUser;
import com.jeramtough.niyouji.component.communicate.factory.PerformerSocketMessageFactory;
import com.jeramtough.niyouji.component.travelnote.TravelnoteResourceTypes;
import com.jeramtough.niyouji.component.websocket.PerformerWebSocketClient;
import com.jeramtough.niyouji.controller.activity.PerformingActivity;
import com.jeramtough.niyouji.controller.activity.Test1Activity;
import com.jeramtough.niyouji.controller.activity.TestActivity;
import com.jeramtough.pullrefreshing.PullToRefreshView;

import java.util.ArrayList;

/**
 * @author 11718
 *         on 2017  November 20 Monday 12:00.
 */

public class DiscoverFragment extends AppBaseFragment
{
	@InjectView(R.id.btn_test1)
	private Button btnTest1;
	
	@InjectView(R.id.btn_test2)
	private Button btnTest2;
	
	@InjectView(R.id.btn_test3)
	private Button btnTest3;
	
	@InjectView(R.id.btn_test4)
	private Button btnTest4;
	
	@InjectComponent
	private AppUser appUser;
	@InjectComponent
	private PerformerWebSocketClient performerWebSocketClient;
	
	@Override
	public int loadFragmentLayoutId()
	{
		return R.layout.fragment_discover;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		getIocContainer().injectView(getContext(), this);
		btnTest1.setOnClickListener(this);
		btnTest2.setOnClickListener(this);
		btnTest3.setOnClickListener(this);
		btnTest4.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v, int viewId)
	{
		switch (viewId)
		{
			case R.id.btn_test1:
				new Thread()
				{
					@Override
					public void run()
					{
						//模拟创建房间操作
						try
						{
							/*performerWebSocketClient =
									(PerformerWebSocketClient) performerWebSocketClient
											.clone();*/
							performerWebSocketClient.reconnectBlocking();
							
							CreatePerformingRoomCommand createPerformingRoomCommand =
									new CreatePerformingRoomCommand();
							createPerformingRoomCommand.setCoverResourceUrl(
									"http://niyouji.oss-cn-shenzhen.aliyuncs.com/images/cover_1516105481681.jpg");
							createPerformingRoomCommand
									.setCoverType(TravelnoteResourceTypes.IMAGE.toString());
							createPerformingRoomCommand
									.setCreateTime(DateTimeUtil.getCurrentDateTime());
							createPerformingRoomCommand.setPerformerId(appUser.getUserId());
							createPerformingRoomCommand.setTravelnoteTitle("这是测试游记");
							SocketMessage socketMessage = PerformerSocketMessageFactory
									.processCreatePerformingRoomSocketMessage(
											createPerformingRoomCommand);
							performerWebSocketClient.sendSocketMessage(socketMessage);
							Thread.sleep(500);
							IntentUtil.toTheOtherActivity(getActivity(),
									PerformingActivity.class);
							
							//							Thread.sleep(5000);
							//							performerWebSocketClient.closeBlocking();
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}.start();
				
				break;
			case R.id.btn_test2:
				IntentUtil.toTheOtherActivity(this.getActivity(), TestActivity.class);
				break;
			case R.id.btn_test3:
				IntentUtil.toTheOtherActivity(this.getActivity(), Test1Activity.class);
				break;
			case R.id.btn_test4:
				new Thread()
				{
					@Override
					public void run()
					{
					}
				}.start();
				
				break;
		}
	}
	//***********************************************
}
