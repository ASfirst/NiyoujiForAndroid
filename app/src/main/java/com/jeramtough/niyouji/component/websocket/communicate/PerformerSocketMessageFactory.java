package com.jeramtough.niyouji.component.websocket.communicate;

import com.alibaba.fastjson.JSON;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.AddPageCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.DeletePageCommand;
import com.jeramtough.niyouji.bean.socketmessage.action.PerformerCommandActions;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.CreatePerformingRoomCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.SelectPageCommand;

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
	
	public static SocketMessage processSelectPageSocketMessage(SelectPageCommand selectPageCommand)
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
	
	public static SocketMessage processDeletedPageSocketMessage(DeletePageCommand deletePageCommand)
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.setCommandAction(PerformerCommandActions.ADDED_PAGE);
		
		socketMessage.setCommand(JSON.toJSONString(deletePageCommand));
		
		return socketMessage;
	}
}
