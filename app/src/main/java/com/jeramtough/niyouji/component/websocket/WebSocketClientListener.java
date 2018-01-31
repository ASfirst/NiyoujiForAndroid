package com.jeramtough.niyouji.component.websocket;

import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import org.java_websocket.handshake.ServerHandshake;

/**
 * @author 11718
 *         on 2018  January 16 Tuesday 19:39.
 */

public abstract class WebSocketClientListener
{
	void onOpen(ServerHandshake serverHandshake)
	{
	}
	
	public void onMessage(SocketMessage socketMessage)
	{
	}
	
	public void onClose(int code, String reason, boolean remote)
	{
	}
	
	public void onError(Exception e)
	{
	}
	
	public void onPong()
	{}
}
