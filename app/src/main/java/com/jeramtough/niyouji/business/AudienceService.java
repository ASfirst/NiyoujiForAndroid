package com.jeramtough.niyouji.business;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.jtlog3.P;
import com.jeramtough.jtutil.DateTimeUtil;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import com.jeramtough.niyouji.bean.socketmessage.action.AudienceCommandActions;
import com.jeramtough.niyouji.bean.socketmessage.action.PerformerCommandActions;
import com.jeramtough.niyouji.bean.socketmessage.action.ServerCommandActions;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.AudienceLeaveCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.EnterPerformingRoomCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.LightAttentionCountCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.SendAudienceBarrageCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.*;
import com.jeramtough.niyouji.bean.travelnote.Travelnote;
import com.jeramtough.niyouji.component.app.AppUser;
import com.jeramtough.niyouji.component.communicate.factory.AudienceSocketMessageFactory;
import com.jeramtough.niyouji.component.communicate.parser.AudienceCommandParser;
import com.jeramtough.niyouji.component.communicate.parser.PerformerCommandParser;
import com.jeramtough.niyouji.component.websocket.WebSocketClientListener;
import com.jeramtough.niyouji.component.websocket.WebSocketClientProxy;

import java.util.concurrent.*;

/**
 * @author 11718
 *         on 2018  January 20 Saturday 22:24.
 */
@JtService
public class AudienceService implements AudienceBusiness
{
	private WebSocketClientProxy webSocketClientProxy;
	private ExecutorService executorService;
	private AppUser appUser;
	
	@IocAutowire
	public AudienceService(WebSocketClientProxy webSocketClientProxy, AppUser appUser)
	{
		this.webSocketClientProxy = webSocketClientProxy;
		this.appUser = appUser;
		
		executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());
	}
	
	@Override
	public void enterPerformingRoom(String performerId, BusinessCaller enterRoomBusinessCaller,
			BusinessCaller obtainingLiveTravelnoteBusinessCaller)
	{
		executorService.submit(() ->
		{
			try
			{
				//初始化WebSocket客户端对象
				webSocketClientProxy.resetgetAudienceWebSocketClient();
				
				boolean connectSuccessfully =
						webSocketClientProxy.getAudienceWebSocketClient().connectBlocking();
				enterRoomBusinessCaller.getData()
						.putBoolean("connectSuccessfully", connectSuccessfully);
				enterRoomBusinessCaller.callBusiness();
				P.debug(connectSuccessfully);
				if (connectSuccessfully)
				{
					WebSocketClientListener webSocketClientListener =
							new WebSocketClientListener()
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
							};
					
					webSocketClientProxy.getAudienceWebSocketClient()
							.addWebSocketClientListener(webSocketClientListener);
					
					//发送进入房间命令，并且等待游记资源返回
					EnterPerformingRoomCommand enterPerformingRoomCommand =
							new EnterPerformingRoomCommand();
					enterPerformingRoomCommand.setPerformerId(performerId);
					SocketMessage socketMessage = AudienceSocketMessageFactory
							.processEnterPerformingRoomSocketMessage(
									enterPerformingRoomCommand);
					webSocketClientProxy.getAudienceWebSocketClient()
							.sendSocketMessage(socketMessage);
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
		
		WebSocketClientListener webSocketClientListener1 = new WebSocketClientListener()
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
					case PerformerCommandActions.PERFORMER_LEAVE:
						performerActionsBusinessCaller.getData()
								.putInt("performerAction", socketMessage.getCommandAction());
						
						PerformerLeaveCommand performerLeaveCommand = PerformerCommandParser
								.parsePerformerLeaveCommand(socketMessage);
						
						performerActionsBusinessCaller.getData()
								.putSerializable("command", performerLeaveCommand);
						
						performerActionsBusinessCaller.callBusiness();
						break;
					
					case PerformerCommandActions.PERFORMER_REBACK:
						performerActionsBusinessCaller.getData()
								.putInt("performerAction", socketMessage.getCommandAction());
						
						PerformerRebackCommand performerRebackCommand = PerformerCommandParser
								.parsePerformerRebackCommand(socketMessage);
						
						performerActionsBusinessCaller.getData()
								.putSerializable("command", performerRebackCommand);
						
						performerActionsBusinessCaller.callBusiness();
						break;
				}
			}
		};
		webSocketClientProxy.getAudienceWebSocketClient()
				.addWebSocketClientListener(webSocketClientListener1);
	}
	
	@Override
	public void callAudienceActions(BusinessCaller audienceActionsBusinessCaller)
	{
		webSocketClientProxy.getAudienceWebSocketClient()
				.addWebSocketClientListener(new WebSocketClientListener()
				{
					@Override
					public void onMessage(SocketMessage socketMessage)
					{
						int action = socketMessage.getCommandAction();
						switch (action)
						{
							case AudienceCommandActions.ENTER_PERFORMING_ROOM:
								audienceActionsBusinessCaller.getData()
										.putInt("audienceAction",
												socketMessage.getCommandAction());
								
								EnterPerformingRoomCommand enterPerformingRoomCommand =
										AudienceCommandParser.parseEnterPerformingRoomCommand(
												socketMessage);
								
								audienceActionsBusinessCaller.getData()
										.putSerializable("command",
												enterPerformingRoomCommand);
								
								audienceActionsBusinessCaller.callBusiness();
								break;
							
							case AudienceCommandActions.SEND_AUDIENCE_BARRAGE:
								audienceActionsBusinessCaller.getData()
										.putInt("audienceAction",
												socketMessage.getCommandAction());
								
								SendAudienceBarrageCommand sendAudienceBarrageCommand =
										AudienceCommandParser.parseSendAudienceBarrageCommand(
												socketMessage);
								
								audienceActionsBusinessCaller.getData()
										.putSerializable("command",
												sendAudienceBarrageCommand);
								
								audienceActionsBusinessCaller.callBusiness();
								break;
							case AudienceCommandActions.LIGHT_ATTENTION_COUNT:
								audienceActionsBusinessCaller.getData()
										.putInt("audienceAction",
												socketMessage.getCommandAction());
								
								LightAttentionCountCommand lightAttentionCountCommand =
										AudienceCommandParser.parseLightAttentionCountCommand(
												socketMessage);
								
								audienceActionsBusinessCaller.getData()
										.putSerializable("command",
												lightAttentionCountCommand);
								
								audienceActionsBusinessCaller.callBusiness();
								break;
							case AudienceCommandActions.AUDIENCE_LEAVE:
								audienceActionsBusinessCaller.getData()
										.putInt("audienceAction",
												socketMessage.getCommandAction());
								
								AudienceLeaveCommand audienceLeaveCommand =
										AudienceCommandParser
												.parseAudienceLeaveCommand(socketMessage);
								
								audienceActionsBusinessCaller.getData()
										.putSerializable("command", audienceLeaveCommand);
								
								audienceActionsBusinessCaller.callBusiness();
								break;
						}
					}
				});
	}
	
	@Override
	public void broadcastAudienceSendBarrage(String performerId, int position,
			String broadcastContent)
	{
		
		executorService.submit(() ->
		{
			SendAudienceBarrageCommand sendAudienceBarrageCommand =
					new SendAudienceBarrageCommand();
			sendAudienceBarrageCommand.setPosition(position);
			sendAudienceBarrageCommand.setContent(broadcastContent);
			if (appUser.getHasLogined())
			{
				sendAudienceBarrageCommand.setNickname(appUser.getNickname());
			}
			else
			{
				sendAudienceBarrageCommand.setNickname("观众");
			}
			sendAudienceBarrageCommand.setPerformers(false);
			sendAudienceBarrageCommand.setPerformerId(performerId);
			sendAudienceBarrageCommand.setCreateTime(DateTimeUtil.getCurrentDateTime());
			
			SocketMessage socketMessage = AudienceSocketMessageFactory
					.processSendAudienceBarrageCommandSocketMessage(
							sendAudienceBarrageCommand);
			
			webSocketClientProxy.getAudienceWebSocketClient().sendSocketMessage(socketMessage);
		});
		
	}
	
	@Override
	public void broadcastLightAttentionCount(String performerId)
	{
		executorService.submit(() ->
		{
			LightAttentionCountCommand lightAttentionCountCommand =
					new LightAttentionCountCommand();
			lightAttentionCountCommand.setPerformerId(performerId);
			
			SocketMessage socketMessage = AudienceSocketMessageFactory
					.processLightAttentionCountCommandSocketMessage(
							lightAttentionCountCommand);
			
			webSocketClientProxy.getAudienceWebSocketClient().sendSocketMessage(socketMessage);
		});
	}
	
	@Override
	public void broadcastAudienceLeave(String performerId)
	{
		executorService.submit(() ->
		{
			AudienceLeaveCommand audienceLeaveCommand = new AudienceLeaveCommand();
			audienceLeaveCommand.setPerformerId(performerId);
			
			SocketMessage socketMessage = AudienceSocketMessageFactory
					.processAudienceLeaveCommandSocketMessage(audienceLeaveCommand);
			
			webSocketClientProxy.getAudienceWebSocketClient().sendSocketMessage(socketMessage);
			
			//发送完退出房间信息再关闭session
			try
			{
				webSocketClientProxy.getAudienceWebSocketClient().closeBlocking();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		});
	}
	
	
}
