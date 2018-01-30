package com.jeramtough.niyouji.component.websocket;

import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.niyouji.component.app.AppConfig;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author 11718
 */
@JtComponent
public class AudienceWebSocketClient extends BaseWebSocketClient implements Cloneable
{
	private static final String socketHandlerUrl =
			"ws://" + AppConfig.SOCKET_SERVER_HOST + "/niyouji/audienceHandler.do";
	
	public AudienceWebSocketClient() throws URISyntaxException
	{
		super(new URI(socketHandlerUrl));
	}
	
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
		
		AudienceWebSocketClient client = null;
		try
		{
			client = new AudienceWebSocketClient();
			client.setWebSocketClientListeners(this.getWebSocketClientListeners());
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return client;
	}
}
