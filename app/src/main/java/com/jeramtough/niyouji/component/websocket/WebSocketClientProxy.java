package com.jeramtough.niyouji.component.websocket;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

import java.net.URISyntaxException;

/**
 * @author 11718
 *         on 2018  January 31 Wednesday 12:49.
 */
@JtComponent
public class WebSocketClientProxy
{
	private PerformerWebSocketClient performerWebSocketClient;
	private AudienceWebSocketClient audienceWebSocketClient;
	
	@IocAutowire
	public WebSocketClientProxy()
	{
	}
	
	public PerformerWebSocketClient getPerformerWebSocketClient()
	{
		return performerWebSocketClient;
	}
	
	public AudienceWebSocketClient getAudienceWebSocketClient()
	{
		return audienceWebSocketClient;
	}
	
	public void resetPerformerWebSocketClient()
	{
		try
		{
			performerWebSocketClient = new PerformerWebSocketClient();
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
	public void resetgetAudienceWebSocketClient()
	{
		try
		{
			audienceWebSocketClient = new AudienceWebSocketClient();
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
}
