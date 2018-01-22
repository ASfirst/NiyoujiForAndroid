package com.jeramtough.niyouji.component.websocket;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtlog3.WithLogger;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;

/**
 * @author 11718
 */
public class BaseWebSocketClient extends WebSocketClient implements WithLogger
{
	private boolean isConectionFailed = false;
	private ArrayList<WebSocketClientListener> webSocketClientListeners;
	
	public BaseWebSocketClient(URI serverUri)
	{
		super(serverUri);
		webSocketClientListeners = new ArrayList<>();
	}
	
	@Override
	public void onOpen(ServerHandshake handshakedata)
	{
		getP().info("open a new connection with server");
		if (webSocketClientListeners.size() != 0)
		{
			for (WebSocketClientListener webSocketClientListener : webSocketClientListeners)
			{
				webSocketClientListener.onOpen(handshakedata);
			}
		}
	}
	
	@Override
	public void send(String text) throws NotYetConnectedException
	{
		super.send(text);
		getP().info("client sent a text message [" + text + "] to server");
	}
	
	@Override
	public void onMessage(String message)
	{
		getP().info("accept a message from server [" + message + "]");
		
		SocketMessage socketMessage = JSON.parseObject(message, SocketMessage.class);
		if (webSocketClientListeners.size() != 0)
		{
			for (WebSocketClientListener webSocketClientListener : webSocketClientListeners)
			{
				webSocketClientListener.onMessage(socketMessage);
			}
		}
	}
	
	@Override
	public void onClose(int code, String reason, boolean remote)
	{
		getP().warn("close the connection with server, because " + code + ": " + reason);
		isConectionFailed = true;
		
		if (webSocketClientListeners.size() != 0)
		{
			for (WebSocketClientListener webSocketClientListener : webSocketClientListeners)
			{
				webSocketClientListener.onClose(code, reason, remote);
			}
		}
		
	}
	
	@Override
	public void onError(Exception ex)
	{
		getP().error("have a exception for [" + ex.getMessage() + "]");
		this.close();
		
		if (webSocketClientListeners.size() != 0)
		{
			for (WebSocketClientListener webSocketClientListener : webSocketClientListeners)
			{
				webSocketClientListener.onError(ex);
			}
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
	
	
	public void addWebSocketClientListener(WebSocketClientListener webSocketClientListener)
	{
		this.webSocketClientListeners.add(webSocketClientListener);
	}
	
	protected ArrayList<WebSocketClientListener> getWebSocketClientListeners()
	{
		return webSocketClientListeners;
	}
	
	protected void setWebSocketClientListeners(
			ArrayList<WebSocketClientListener> webSocketClientListeners)
	{
		this.webSocketClientListeners = webSocketClientListeners;
	}
}
