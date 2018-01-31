package com.jeramtough.niyouji.component.websocket;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtlog3.P;
import com.jeramtough.jtlog3.WithLogger;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;

/**
 * @author 11718
 */
public class BaseWebSocketClient extends WebSocketClient implements WithLogger
{
	private boolean isConnectionFailed = false;
	private ArrayList<WebSocketClientListener> webSocketClientListeners;
	private boolean isHeartBeating = false;
	private byte[] pingBytes = "0".getBytes();
	private int pingInterval = 1500;
	private int pingPongCount = 0;
	private int maxLostCount = 25;
	
	
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
		isConnectionFailed = true;
		
		stopHeartbeat();
		
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
		
		stopHeartbeat();
		
		if (webSocketClientListeners.size() != 0)
		{
			for (WebSocketClientListener webSocketClientListener : webSocketClientListeners)
			{
				webSocketClientListener.onError(ex);
			}
		}
	}
	
	@Override
	public void onMessage(ByteBuffer bytes)
	{
		pingPongCount--;
		if (webSocketClientListeners.size() != 0)
		{
			for (WebSocketClientListener webSocketClientListener : webSocketClientListeners)
			{
				webSocketClientListener.onPong();
			}
		}
	}
	
	public void sendSocketMessage(SocketMessage socketMessage)
	{
		this.send(JSON.toJSONString(socketMessage));
	}
	
	public void startHeartbeat()
	{
		isHeartBeating = true;
		
		new Thread()
		{
			@Override
			public void run()
			{
				while (isHeartBeating)
				{
					if (pingPongCount < maxLostCount)
					{
						P.debug(pingPongCount);
						pingPongCount++;
						myPing();
						try
						{
							Thread.sleep(pingInterval);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
					
				}
			}
		}.start();
		
	}
	
	public void myPing()
	{
		send(pingBytes);
	}
	
	
	public void stopHeartbeat()
	{
		isHeartBeating = false;
	}
	
	public boolean isConnectionFailed()
	{
		return isConnectionFailed;
	}
	
	
	public void addWebSocketClientListener(WebSocketClientListener webSocketClientListener)
	{
		this.webSocketClientListeners.add(webSocketClientListener);
	}
	
	public void removeWebSocketClientListener(WebSocketClientListener webSocketClientListener)
	{
		this.webSocketClientListeners.remove(webSocketClientListener);
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
