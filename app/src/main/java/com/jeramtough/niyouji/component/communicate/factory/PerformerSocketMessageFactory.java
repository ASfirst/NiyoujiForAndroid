package com.jeramtough.niyouji.component.communicate.factory;

import com.alibaba.fastjson.JSON;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.*;
import com.jeramtough.niyouji.bean.socketmessage.action.PerformerCommandActions;

/**
 * @author 11718
 */
public class PerformerSocketMessageFactory
{
	public static SocketMessage processCreatePerformingRoomSocketMessage(
			CreatePerformingRoomCommand createPerformingRoomCommand)
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.setCommandAction(PerformerCommandActions.CREATE_PERFORMING_ROOM);
		socketMessage.setCommand(JSON.toJSONString(createPerformingRoomCommand));
		return socketMessage;
	}
	
	public static SocketMessage processSelectPageSocketMessage(
			SelectPageCommand selectPageCommand)
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.setCommandAction(PerformerCommandActions.SELECTED_PAGE);
		
		socketMessage.setCommand(JSON.toJSONString(selectPageCommand));
		
		return socketMessage;
	}
	
	public static SocketMessage processAddedPageSocketMessage(AddPageCommand addPageCommand)
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.setCommandAction(PerformerCommandActions.ADDED_PAGE);
		
		socketMessage.setCommand(JSON.toJSONString(addPageCommand));
		
		return socketMessage;
	}
	
	public static SocketMessage processDeletedPageSocketMessage(
			DeletePageCommand deletePageCommand)
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.setCommandAction(PerformerCommandActions.DELETED_PAGE);
		
		socketMessage.setCommand(JSON.toJSONString(deletePageCommand));
		
		return socketMessage;
	}
	
	public static SocketMessage processPageSetImageSocketMessage(
			PageSetImageCommand pageSetImageCommand)
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.setCommandAction(PerformerCommandActions.PAGE_SET_IMAGE);
		
		socketMessage.setCommand(JSON.toJSONString(pageSetImageCommand));
		
		return socketMessage;
	}
	
	public static SocketMessage processPageSetVideoSocketMessage(
			PageSetVideoCommand pageSetVideoCommand)
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.setCommandAction(PerformerCommandActions.PAGE_SET_VIDEO);
		
		socketMessage.setCommand(JSON.toJSONString(pageSetVideoCommand));
		return socketMessage;
	}
	
	public static SocketMessage processPageSetThemeCommandSSocketMessage(
			PageSetThemeCommand pageSetThemeCommand)
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.setCommandAction(PerformerCommandActions.PAGE_SET_THEME);
		
		socketMessage.setCommand(JSON.toJSONString(pageSetThemeCommand));
		return socketMessage;
	}
	
	public static SocketMessage processPageSetBackgroundMusicCommandSocketMessage(
			PageSetBackgroundMusicCommand pageSetBackgroundMusicCommand)
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.setCommandAction(PerformerCommandActions.PAGE_SET_BACKGROUND_MUSIC);
		
		socketMessage.setCommand(JSON.toJSONString(pageSetBackgroundMusicCommand));
		return socketMessage;
	}
	
	public static SocketMessage processSendPerformerBarrageCommandSocketMessage(
			SendPerformerBarrageCommand sendPerformerBarrageCommand)
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.setCommandAction(PerformerCommandActions.SENT_PERFORMER_BARRAGE);
		
		socketMessage.setCommand(JSON.toJSONString(sendPerformerBarrageCommand));
		return socketMessage;
	}
	
	public static SocketMessage processPageTextChangeCommandSocketMessage(
			PageTextChangeCommand pageSetVideoCommand)
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.setCommandAction(PerformerCommandActions.PAGE_TEXT_CHANGED);
		
		socketMessage.setCommand(JSON.toJSONString(pageSetVideoCommand));
		return socketMessage;
	}
	
	public static SocketMessage processTravelnoteEndCommandSocketMessage(
			TravelnoteEndCommand travelnoteEndCommand)
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.setCommandAction(PerformerCommandActions.TRAVELNOTE_END);
		
		socketMessage.setCommand(JSON.toJSONString(travelnoteEndCommand));
		return socketMessage;
	}
	
	public static SocketMessage processPerformerLeaveCommandSocketMessage(
			PerformerLeaveCommand performerLeaveCommand)
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.setCommandAction(PerformerCommandActions.PERFORMER_LEAVE);
		
		socketMessage.setCommand(JSON.toJSONString(performerLeaveCommand));
		return socketMessage;
	}
	
	public static SocketMessage processPerformerRebackCommandSocketMessage(
			PerformerRebackCommand performerRebackCommand)
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.setCommandAction(PerformerCommandActions.PERFORMER_REBACK);
		
		socketMessage.setCommand(JSON.toJSONString(performerRebackCommand));
		return socketMessage;
	}
	
	
}
