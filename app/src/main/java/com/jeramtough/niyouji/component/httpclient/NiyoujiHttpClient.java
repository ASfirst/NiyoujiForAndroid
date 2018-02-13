package com.jeramtough.niyouji.component.httpclient;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtlog3.WithLogger;
import com.jeramtough.niyouji.bean.landr.RegisterRequest;
import com.jeramtough.niyouji.bean.landr.RegisterRequestMessage;
import com.jeramtough.niyouji.bean.landr.RegisterResponse;
import com.jeramtough.niyouji.bean.travelnote.Appraise;
import com.jeramtough.niyouji.component.app.AppConfig;
import okhttp3.*;

import java.io.IOException;

/**
 * @author 11718
 *         on 2018  January 20 Saturday 16:44.
 */
@JtComponent
public class NiyoujiHttpClient implements WithLogger
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
		
		getP().info("obtaining the cover of live travelnote");
		
		Response response = client.newCall(request).execute();
		jsonStr = response.body().string();
		
		return jsonStr;
	}
	
	public String getFinishedTravelnoteCoversBlocking() throws IOException
	{
		String jsonStr = null;
		String url = baseUrl + "getFinishedTravelnoteCovers.do";
		Request request = new Request.Builder().url(url).build();
		
		getP().info("obtaining the cover of finished travelnote");
		
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
	
	public boolean publishAppraise(Appraise appraise)
	{
		RegisterResponse registerResponse = new RegisterResponse();
		
		String url = baseUrl + "publishAppraise.do";
		
		RequestBody body = RequestBody
				.create(MediaType.parse("application/json"), JSON.toJSONString(appraise));
		
		Request request = new Request.Builder().url(url).post(body).build();
		try
		{
			Response response = client.newCall(request).execute();
			boolean isSuccessful;
			if (response.body() != null && response.body().string().equals("666"))
			{
				isSuccessful = true;
				getP().info("publish appraise succeed");
			}
			else
			{
				isSuccessful = false;
				getP().warn("publish appraise failed");
			}
			
			return isSuccessful;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public String getAppraisesCount(String travelnoteId) throws IOException
	{
		String result = null;
		String url = baseUrl + "getAppraisesCount.do?travelnoteId=" + travelnoteId;
		Request request = new Request.Builder().url(url).build();
		
		Response response = client.newCall(request).execute();
		if (response.body()!=null)
		{
			result = response.body().string();
		}
		
		return result;
	}
}
