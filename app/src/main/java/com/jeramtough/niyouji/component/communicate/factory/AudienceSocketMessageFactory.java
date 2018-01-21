package com.jeramtough.niyouji.component.communicate.factory;

import com.alibaba.fastjson.JSON;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import com.jeramtough.niyouji.bean.socketmessage.action.AudienceCommandActions;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.EnterPerformingRoomCommand;

/**
 * @author 11718
 *         on 2018  January 21 Sunday 18:05.
 */

public class AudienceSocketMessageFactory
{
	public static SocketMessage processEnterPerformingRoomSocketMessage(
			EnterPerformingRoomCommand enterPerformingRoomCommand)
	{
		SocketMessage socketMessage =
				new SocketMessage(AudienceCommandActions.ENTER_PERFORMING_ROOM);
		socketMessage.setCommand(JSON.toJSONString(enterPerformingRoomCommand));
		
		return socketMessage;
	}
}
