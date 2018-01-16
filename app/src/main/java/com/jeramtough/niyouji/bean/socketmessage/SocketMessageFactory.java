package com.jeramtough.niyouji.bean.socketmessage;

import com.alibaba.fastjson.JSON;
import com.jeramtough.niyouji.bean.socketmessage.command.ClientCommandActions;
import com.jeramtough.niyouji.bean.socketmessage.command.CreatePerformingRoomCommand;

public class SocketMessageFactory
{
	
	public static SocketMessage processCreatePerformingRoomSocketMessage(
			CreatePerformingRoomCommand createPerformingRoomCommand)
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.setCommandAction(ClientCommandActions.CREATE_PERFORMING_ROOM);
		socketMessage.setCommand(JSON.toJSONString(createPerformingRoomCommand));
		return socketMessage;
	}
	
	
}
