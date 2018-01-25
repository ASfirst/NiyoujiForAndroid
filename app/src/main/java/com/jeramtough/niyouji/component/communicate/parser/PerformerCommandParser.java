package com.jeramtough.niyouji.component.communicate.parser;

import com.alibaba.fastjson.JSON;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.*;

/**
 * @author 11718
 * on 2018  January 16 Tuesday 19:04.
 */

public class PerformerCommandParser
{
	public static CreatePerformingRoomCommand parseCreatePerformingRoomCommand(
			SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), CreatePerformingRoomCommand.class);
	}
	
	public static SelectPageCommand parseSelectPageCommand(SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), SelectPageCommand.class);
	}
	
	public static AddPageCommand parseAddPageCommand(SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), AddPageCommand.class);
	}
	
	public static DeletePageCommand parseDeletePageCommand(SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), DeletePageCommand.class);
	}
	
	public static PageSetImageCommand parsePageSetImageCommand(SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), PageSetImageCommand.class);
	}
	
	public static PageSetVideoCommand parsePageSetVideoCommand(SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), PageSetVideoCommand.class);
	}
	
	public static PageSetThemeCommand parsePageSetThemeCommand(SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), PageSetThemeCommand.class);
	}
	
	public static PageSetBackgroundMusicCommand parsePageSetBackgroundMusicCommand(
			SocketMessage socketMessage)
	{
		return JSON
				.parseObject(socketMessage.getCommand(), PageSetBackgroundMusicCommand.class);
	}
	
	public static PageTextChangeCommand parsePageTextChangeCommand(SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), PageTextChangeCommand.class);
	}
	
	public static SendPerformerBarrageCommand parseSendPerformerBarrageCommand(
			SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), SendPerformerBarrageCommand.class);
	}
	
	public static TravelnoteEndCommand parseTravelnoteEndCommand(SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), TravelnoteEndCommand.class);
	}
	
	public static PerformerLeaveCommand parsePerformerLeaveCommand(SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), PerformerLeaveCommand.class);
	}
	
	public static PerformerRebackCommand parsePerformerRebackCommand(
			SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), PerformerRebackCommand.class);
	}
}
