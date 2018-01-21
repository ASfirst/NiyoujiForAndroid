package com.jeramtough.niyouji.business;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import com.jeramtough.niyouji.bean.socketmessage.action.PerformerCommandActions;
import com.jeramtough.niyouji.bean.socketmessage.action.ServerCommandActions;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.EnterPerformingRoomCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.AddPageCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.DeletePageCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.PerformerCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.SelectPageCommand;
import com.jeramtough.niyouji.bean.travelnote.LiveTravelnoteCover;
import com.jeramtough.niyouji.bean.travelnote.Travelnote;
import com.jeramtough.niyouji.component.communicate.factory.AudienceSocketMessageFactory;
import com.jeramtough.niyouji.component.communicate.parser.PerformerCommandParser;
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
			BusinessCaller obtainingLiveTravelnoteBusinessCaller,
			BusinessCaller performerActionsBusinessCaller)
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
											obtainingLiveTravelnoteBusinessCaller
													.callBusiness();
											break;
										
										case PerformerCommandActions.ADDED_PAGE:
											performerActionsBusinessCaller.getData()
													.putInt("performerAction",
															socketMessage.getCommandAction());
											
											AddPageCommand addPageCommand =
													PerformerCommandParser.parseAddPageCommand(
															socketMessage);
											
											performerActionsBusinessCaller.getData()
													.putSerializable("command",
															addPageCommand);
											
											performerActionsBusinessCaller.callBusiness();
											break;
										
										case PerformerCommandActions.SELECTED_PAGE:
											performerActionsBusinessCaller.getData()
													.putInt("performerAction",
															socketMessage.getCommandAction());
											
											SelectPageCommand selectPageCommand =
													PerformerCommandParser
															.parseSelectPageCommand(
																	socketMessage);
											
											performerActionsBusinessCaller.getData()
													.putSerializable("command",
															selectPageCommand);
											
											performerActionsBusinessCaller.callBusiness();
											break;
										
										case PerformerCommandActions.DELETED_PAGE:
											performerActionsBusinessCaller.getData()
													.putInt("performerAction",
															socketMessage.getCommandAction());
											
											DeletePageCommand deletePageCommand =
													PerformerCommandParser
															.parseDeletePageCommand(
																	socketMessage);
											
											performerActionsBusinessCaller.getData()
													.putSerializable("command",
															deletePageCommand);
											
											performerActionsBusinessCaller.callBusiness();
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
