package com.jeramtough.niyouji;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.util.AsyncListUtil;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.component.app.AppConfig;
import com.jeramtough.niyouji.component.httpclient.NiyoujiHttpClient;
import com.jeramtough.niyouji.component.websocket.PerformerWebSocketClient;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.WebSocket;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.Socket;

import static com.koushikdutta.async.http.AsyncHttpClient.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ConntectTest
{
	
	@Test
	public void connectTest() throws Exception
	{
		PerformerWebSocketClient webSocketClient;
		
		// Context of the app under test.
		Context appContext = InstrumentationRegistry.getTargetContext();
		
		String socketHandlerUrl =
				"ws://" + AppConfig.SERVER_HOST + "/niyouji/performerHandler" + ".do";
		
		AsyncHttpClient.getDefaultInstance()
				.websocket(socketHandlerUrl, "8080", new WebSocketConnectCallback()
				{
					@Override
					public void onCompleted(Exception ex, WebSocket webSocket)
					{
						ex.printStackTrace();
						P.arrive();
					}
				});
	}
}
