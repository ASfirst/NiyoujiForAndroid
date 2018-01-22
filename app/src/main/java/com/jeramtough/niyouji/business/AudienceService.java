package com.jeramtough.niyouji.business;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import com.jeramtough.niyouji.bean.socketmessage.action.PerformerCommandActions;
import com.jeramtough.niyouji.bean.socketmessage.action.ServerCommandActions;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.EnterPerformingRoomCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.*;
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
			BusinessCaller obtainingLiveTravelnoteBusinessCaller)
	{
		audienceWebSocketClient =
				(AudienceWebSocketClient) audienceWebSocketClient.clone();
		
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
							.addWebSocketClientListener(new WebSocketClientListener()
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
	
	@Override
	public void callPerformerActions(String performerId,
			BusinessCaller performerActionsBusinessCaller)
	{
		audienceWebSocketClient.addWebSocketClientListener(new WebSocketClientListener()
		{
			@Override
			public void onMessage(SocketMessage socketMessage)
			{
				int action = socketMessage.getCommandAction();
				switch (action)
				{
					case PerformerCommandActions.ADDED_PAGE:
						performerActionsBusinessCaller.getData()
								.putInt("performerAction", socketMessage.getCommandAction());
						
						AddPageCommand addPageCommand =
								PerformerCommandParser.parseAddPageCommand(socketMessage);
						
						performerActionsBusinessCaller.getData()
								.putSerializable("command", addPageCommand);
						
						performerActionsBusinessCaller.callBusiness();
						break;
					
					case PerformerCommandActions.SELECTED_PAGE:
						performerActionsBusinessCaller.getData()
								.putInt("performerAction", socketMessage.getCommandAction());
						
						SelectPageCommand selectPageCommand =
								PerformerCommandParser.parseSelectPageCommand(socketMessage);
						
						performerActionsBusinessCaller.getData()
								.putSerializable("command", selectPageCommand);
						
						performerActionsBusinessCaller.callBusiness();
						break;
					
					case PerformerCommandActions.DELETED_PAGE:
						performerActionsBusinessCaller.getData()
								.putInt("performerAction", socketMessage.getCommandAction());
						
						DeletePageCommand deletePageCommand =
								PerformerCommandParser.parseDeletePageCommand(socketMessage);
						
						performerActionsBusinessCaller.getData()
								.putSerializable("command", deletePageCommand);
						
						performerActionsBusinessCaller.callBusiness();
						break;
					
					case PerformerCommandActions.PAGE_SET_IMAGE:
						performerActionsBusinessCaller.getData()
								.putInt("performerAction", socketMessage.getCommandAction());
						
						PageSetImageCommand pageSetImageCommand =
								PerformerCommandParser.parsePageSetImageCommand(socketMessage);
						
						performerActionsBusinessCaller.getData()
								.putSerializable("command", pageSetImageCommand);
						
						performerActionsBusinessCaller.callBusiness();
						break;
					
					case PerformerCommandActions.PAGE_SET_VIDEO:
						performerActionsBusinessCaller.getData()
								.putInt("performerAction", socketMessage.getCommandAction());
						
						PageSetVideoCommand pageSetVideoCommand =
								PerformerCommandParser.parsePageSetVideoCommand(socketMessage);
						
						performerActionsBusinessCaller.getData()
								.putSerializable("command", pageSetVideoCommand);
						
						performerActionsBusinessCaller.callBusiness();
						break;
					
					case PerformerCommandActions.PAGE_SET_THEME:
						performerActionsBusinessCaller.getData()
								.putInt("performerAction", socketMessage.getCommandAction());
						
						PageSetThemeCommand pageSetThemeCommand =
								PerformerCommandParser.parsePageSetThemeCommand(socketMessage);
						
						performerActionsBusinessCaller.getData()
								.putSerializable("command", pageSetThemeCommand);
						
						performerActionsBusinessCaller.callBusiness();
						break;
					
					case PerformerCommandActions.PAGE_SET_BACKGROUND_MUSIC:
						performerActionsBusinessCaller.getData()
								.putInt("performerAction", socketMessage.getCommandAction());
						
						PageSetBackgroundMusicCommand pageSetBackgroundMusicCommand =
								PerformerCommandParser
										.parsePageSetBackgroundMusicCommand(socketMessage);
						
						performerActionsBusinessCaller.getData()
								.putSerializable("command", pageSetBackgroundMusicCommand);
						
						performerActionsBusinessCaller.callBusiness();
						break;
					
					case PerformerCommandActions.PAGE_TEXT_CHANGED:
						performerActionsBusinessCaller.getData()
								.putInt("performerAction", socketMessage.getCommandAction());
						
						PageTextChangeCommand pageTextChangeCommand = PerformerCommandParser
								.parsePageTextChangeCommand(socketMessage);
						
						performerActionsBusinessCaller.getData()
								.putSerializable("command", pageTextChangeCommand);
						
						performerActionsBusinessCaller.callBusiness();
						break;
					
					case PerformerCommandActions.SENT_PERFORMER_BARRAGE:
						performerActionsBusinessCaller.getData()
								.putInt("performerAction", socketMessage.getCommandAction());
						
						SendPerformerBarrageCommand sendPerformerBarrageCommand =
								PerformerCommandParser
										.parseSendPerformerBarrageCommand(socketMessage);
						
						performerActionsBusinessCaller.getData()
								.putSerializable("command", sendPerformerBarrageCommand);
						
						performerActionsBusinessCaller.callBusiness();
						break;
					
					case PerformerCommandActions.TRAVELNOTE_END:
						performerActionsBusinessCaller.getData()
								.putInt("performerAction", socketMessage.getCommandAction());
						
						TravelnoteEndCommand travelnoteEndCommand = PerformerCommandParser
								.parseTravelnoteEndCommand(socketMessage);
						
						performerActionsBusinessCaller.getData()
								.putSerializable("command", travelnoteEndCommand);
						
						performerActionsBusinessCaller.callBusiness();
						break;
				}
			}
		});
	}
	
	
}
