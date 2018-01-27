package com.jeramtough.niyouji.component.websocket;

import java.net.URI;

/**
 * @author 11718
 *         on 2018  January 27 Saturday 18:56.
 */

public class NiyoujiWebSocketClient
{
	private BaseWebSocketClient baseWebSocketClient;
	
	public NiyoujiWebSocketClient(URI uri)
	{
		baseWebSocketClient=new BaseWebSocketClient(uri);
	}
}
