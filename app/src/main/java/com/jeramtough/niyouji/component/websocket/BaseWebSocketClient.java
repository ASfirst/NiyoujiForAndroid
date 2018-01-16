package com.jeramtough.niyouji.component.websocket;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtlog3.WithLogger;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @author 11718
 */
public class BaseWebSocketClient extends WebSocketClient implements WithLogger
{
	private boolean isConectionFailed = false;
	private WebSocketClientListener webSocketClientListener;
	
	public BaseWebSocketClient(URI serverUri)
	{
		super(serverUri);
	}
	
	@Override
	public void onOpen(ServerHandshake handshakedata)
	{
		getP().info("open a new connection with server");
		if (webSocketClientListener != null)
		{
			webSocketClientListener.onOpen(handshakedata);
		}
	}
	
	@Override
	public void onMessage(String message)
	{
		getP().info("accept a message from server [" + message + "]");
		
		SocketMessage socketMessage = JSON.parseObject(message, SocketMessage.class);
		if (webSocketClientListener != null)
		{
			webSocketClientListener.onMessage(socketMessage);
		}
	}
	
	@Override
	public void onClose(int code, String reason, boolean remote)
	{
		getP().warn("close the connection with server, because " + code + ": " + reason);
		isConectionFailed = true;
		
		if (webSocketClientListener != null)
		{
			webSocketClientListener.onClose(code, reason, remote);
		}
		
	}
	
	@Override
	public void onError(Exception ex)
	{
		getP().error("have a exception for [" + ex.getMessage() + "]");
		this.close();
		
		if (webSocketClientListener != null)
		{
			webSocketClientListener.onError(ex);
		}
	}
	
	public void sendSocketMessage(SocketMessage socketMessage)
	{
		this.send(JSON.toJSONString(socketMessage));
	}
	
	public boolean isConectionFailed()
	{
		return isConectionFailed;
	}
	
	
	public void setWebSocketClientListener(WebSocketClientListener webSocketClientListener)
	{
		this.webSocketClientListener = webSocketClientListener;
	}
	
}
