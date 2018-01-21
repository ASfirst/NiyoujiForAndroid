package com.jeramtough.niyouji.component.communicate.parser;

import com.alibaba.fastjson.JSON;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.EnterPerformingRoomCommand;

public class AudienceCommandParser
{
	public static EnterPerformingRoomCommand parseEnterPerformingRoomCommand(
			SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), EnterPerformingRoomCommand.class);
	}
}
