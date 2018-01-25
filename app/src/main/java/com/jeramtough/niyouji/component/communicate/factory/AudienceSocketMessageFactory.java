package com.jeramtough.niyouji.component.communicate.factory;

import com.alibaba.fastjson.JSON;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import com.jeramtough.niyouji.bean.socketmessage.action.AudienceCommandActions;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.AudienceLeaveCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.EnterPerformingRoomCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.LightAttentionCountCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.SendAudienceBarrageCommand;

/**
 * @author 11718
 * on 2018  January 21 Sunday 18:05.
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
	
	public static SocketMessage processSendAudienceBarrageCommandSocketMessage(
			SendAudienceBarrageCommand sendAudienceBarrageCommand)
	{
		SocketMessage socketMessage =
				new SocketMessage(AudienceCommandActions.SEND_AUDIENCE_BARRAGE);
		socketMessage.setCommand(JSON.toJSONString(sendAudienceBarrageCommand));
		
		return socketMessage;
	}
	
	
	public static SocketMessage processLightAttentionCountCommandSocketMessage(
			LightAttentionCountCommand lightAttentionCountCommand)
	{
		SocketMessage socketMessage =
				new SocketMessage(AudienceCommandActions.LIGHT_ATTENTION_COUNT);
		socketMessage.setCommand(JSON.toJSONString(lightAttentionCountCommand));
		
		return socketMessage;
	}
	
	public static SocketMessage processAudienceLeaveCommandSocketMessage(
			AudienceLeaveCommand audienceLeaveCommand)
	{
		SocketMessage socketMessage = new SocketMessage(AudienceCommandActions.AUDIENCE_LEAVE);
		socketMessage.setCommand(JSON.toJSONString(audienceLeaveCommand));
		
		return socketMessage;
	}
}
