package com.jeramtough.niyouji.component.httpclient;

import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.niyouji.component.app.AppConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author 11718
 *         on 2018  January 20 Saturday 16:44.
 */
@JtComponent
public class NiyoujiHttpClient
{
	private OkHttpClient client;
	private final String baseUrl = "http://" + AppConfig.NIYOUJI_SERVER_HOST + "/niyouji/";
	
	public NiyoujiHttpClient()
	{
		client = new OkHttpClient();
	}
	
	public String getLiveTravelnotesBlocking() throws IOException
	{
		String jsonStr = null;
		String url = baseUrl + "getLiveTravelnotes.do";
		Request request = new Request.Builder().url(url).build();
		
			Response response = client.newCall(request).execute();
			jsonStr = response.body().string();
		
		return jsonStr;
	}
}
