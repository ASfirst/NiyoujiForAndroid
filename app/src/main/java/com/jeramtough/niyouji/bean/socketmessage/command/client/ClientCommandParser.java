package com.jeramtough.niyouji.bean.socketmessage.command.client;

import com.alibaba.fastjson.JSON;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import com.jeramtough.niyouji.bean.socketmessage.command.client.CreatePerformingRoomCommand;

/**
 * @author 11718
 * on 2018  January 16 Tuesday 19:04.
 */

public class ClientCommandParser
{
	public static CreatePerformingRoomCommand parseCreatePerformingRoomCommand(
			SocketMessage socketMessage)
	{
		return JSON.parseObject(socketMessage.getCommand(), CreatePerformingRoomCommand.class);
	}
}
