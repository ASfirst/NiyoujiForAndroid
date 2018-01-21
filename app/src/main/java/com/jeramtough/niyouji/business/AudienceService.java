package com.jeramtough.niyouji.business;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import com.jeramtough.niyouji.bean.socketmessage.action.ServerCommandActions;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.EnterPerformingRoomCommand;
import com.jeramtough.niyouji.bean.travelnote.LiveTravelnoteCover;
import com.jeramtough.niyouji.bean.travelnote.Travelnote;
import com.jeramtough.niyouji.component.communicate.factory.AudienceSocketMessageFactory;
import com.jeramtough.niyouji.component.websocket.AudienceWebSocketClient;
import com.jeramtough.niyouji.component.websocket.WebSocketClientListener;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 11718
 *         on 2018  January 20 Saturday 22:24.
 */
@JtService
public class AudienceService implements AudienceBusiness
{
	private AudienceWebSocketClient audienceWebSocketClient;
	private ExecutorService executorService;
	
	@IocAutowire
	public AudienceService(AudienceWebSocketClient audienceWebSocketClient)
	{
		this.audienceWebSocketClient = audienceWebSocketClient;
		
		executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
	}
	
	@Override
	public void enterPerformingRoom(String performerId, BusinessCaller enterRoomBusinessCaller,
			BusinessCaller obtainingLiveTravelnoteBusinessCaller)
	{
		if (audienceWebSocketClient.isConectionFailed())
		{
			try
			{
				audienceWebSocketClient = new AudienceWebSocketClient();
			}
			catch (URISyntaxException e)
			{
				e.printStackTrace();
			}
		}
		
		executorService.submit(() ->
		{
			try
			{
				boolean connectSuccessfully = audienceWebSocketClient.connectBlocking();
				enterRoomBusinessCaller.getData()
						.putBoolean("connectSuccessfully", connectSuccessfully);
				enterRoomBusinessCaller.callBusiness();
				
				if (connectSuccessfully)
				{
					audienceWebSocketClient
							.setWebSocketClientListener(new WebSocketClientListener()
							{
								@Override
								public void onMessage(SocketMessage socketMessage)
								{
									int action = socketMessage.getCommandAction();
									switch (action)
									{
										case ServerCommandActions.RETURN_LIVE_TRAVELNOTE:
											Travelnote travelnote = JSON.parseObject(
													socketMessage.getCommand(),
													Travelnote.class);
											obtainingLiveTravelnoteBusinessCaller.getData()
													.putSerializable("travelnote", travelnote);
											obtainingLiveTravelnoteBusinessCaller.callBusiness();
											break;
									}
								}
							});
					
					//发送进入房间命令，并且等待游记资源返回
					EnterPerformingRoomCommand enterPerformingRoomCommand =
							new EnterPerformingRoomCommand();
					enterPerformingRoomCommand.setPerformerId(performerId);
					SocketMessage socketMessage = AudienceSocketMessageFactory
							.processEnterPerformingRoomSocketMessage(
									enterPerformingRoomCommand);
					audienceWebSocketClient.sendSocketMessage(socketMessage);
				}
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			
		});
	}
	
	
}
