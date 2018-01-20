package com.jeramtough.niyouji.component.websocket;

import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.niyouji.component.app.AppConfig;

import java.net.URI;
import java.net.URISyntaxException;

@JtComponent
public class PerformerWebSocketClient extends BaseWebSocketClient
{
	private static final String socketHandlerUrl =
			"ws://" + AppConfig.SERVER_HOST + "/niyouji/performerHandler" + ".do";
	
	public PerformerWebSocketClient() throws URISyntaxException
	{
		super(new URI(socketHandlerUrl));
	}
}
