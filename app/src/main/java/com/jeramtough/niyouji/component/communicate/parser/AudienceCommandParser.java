package com.jeramtough.niyouji.component.communicate.parser;

import com.alibaba.fastjson.JSON;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.AudienceLeaveCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.EnterPerformingRoomCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.LightAttentionCountCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.SendAudienceBarrageCommand;

public class AudienceCommandParser
{
	public static EnterPerformingRoomCommand parseEnterPerformingRoomCommand(
			SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), EnterPerformingRoomCommand.class);
	}
	
	public static SendAudienceBarrageCommand parseSendAudienceBarrageCommand(
			SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), SendAudienceBarrageCommand.class);
	}
	
	public static LightAttentionCountCommand parseLightAttentionCountCommand(
			SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), LightAttentionCountCommand.class);
	}
	
	public static AudienceLeaveCommand parseAudienceLeaveCommand(SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), AudienceLeaveCommand.class);
	}
}
