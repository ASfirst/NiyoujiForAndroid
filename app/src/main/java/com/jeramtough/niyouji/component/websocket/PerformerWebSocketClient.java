package com.jeramtough.niyouji.component.websocket;

import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

import java.net.URI;
import java.net.URISyntaxException;

@JtComponent
public class PerformerWebSocketClient extends BaseWebSocketClient
{
	private static final String socketHandlerUrl =
			"ws://192.168.0.117:8080/niyouji/performerHandler" + ".do";
	
	public PerformerWebSocketClient() throws URISyntaxException
	{
		super(new URI(socketHandlerUrl));
	}
}
