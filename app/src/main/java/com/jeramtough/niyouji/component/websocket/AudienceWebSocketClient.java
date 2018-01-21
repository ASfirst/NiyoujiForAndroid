package com.jeramtough.niyouji.component.websocket;

import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.niyouji.component.app.AppConfig;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author 11718
 */
@JtComponent
public class AudienceWebSocketClient extends BaseWebSocketClient
{
	private static final String socketHandlerUrl =
			"ws://"+ AppConfig.SERVER_HOST +"/niyouji/audienceHandler.do";
	
	public AudienceWebSocketClient() throws URISyntaxException
	{
		super(new URI(socketHandlerUrl));
	}
}
