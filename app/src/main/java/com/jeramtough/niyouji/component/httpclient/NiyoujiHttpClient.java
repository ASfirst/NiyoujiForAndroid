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
	
	public String getLiveTravelnoteCoversBlocking() throws IOException
	{
		String jsonStr = null;
		String url = baseUrl + "getLiveTravelnoteCovers.do";
		Request request = new Request.Builder().url(url).build();
		
		Response response = client.newCall(request).execute();
		jsonStr = response.body().string();
		
		return jsonStr;
	}
	
	public String getFinishedTravelnoteCoversBlocking() throws IOException
	{
		String jsonStr = null;
		String url = baseUrl + "getFinishedTravelnoteCovers.do";
		Request request = new Request.Builder().url(url).build();
		
		Response response = client.newCall(request).execute();
		jsonStr = response.body().string();
		
		return jsonStr;
	}
	
	public String getFinishedTravelnoteCoversFromEndTravelnoteIdBlocking(String endTravelnote)
			throws IOException
	{
		String jsonStr = null;
		String url =
				baseUrl + "getFinishedTravelnoteCovers.do?fromTravelnoteId=" + endTravelnote;
		Request request = new Request.Builder().url(url).build();
		
		Response response = client.newCall(request).execute();
		jsonStr = response.body().string();
		
		return jsonStr;
	}
}
