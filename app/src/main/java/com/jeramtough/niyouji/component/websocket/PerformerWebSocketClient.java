package com.jeramtough.niyouji.component.websocket;

import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.niyouji.component.app.AppConfig;

import java.net.URI;
import java.net.URISyntaxException;

@JtComponent
public class PerformerWebSocketClient extends BaseWebSocketClient implements Cloneable
{
	private static final String socketHandlerUrl =
			"ws://" + AppConfig.SOCKET_SERVER_HOST + "/niyouji/performerHandler" + ".do";
	
	public PerformerWebSocketClient() throws URISyntaxException
	{
		super(new URI(socketHandlerUrl));
	}
	
	/**
	 * 克隆
	 * @return
	 */
	@Override
	public Object clone()
	{
		try
		{
			super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		
		PerformerWebSocketClient client = null;
		try
		{
			client = new PerformerWebSocketClient();
			client.setWebSocketClientListeners(this.getWebSocketClientListeners());
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return client;
	}
}
