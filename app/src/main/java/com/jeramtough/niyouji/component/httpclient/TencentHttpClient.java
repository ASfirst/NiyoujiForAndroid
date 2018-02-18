package com.jeramtough.niyouji.component.httpclient;

import android.widget.TextView;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtlog3.WithLogger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author 11718
 *         on 2018  February 18 Sunday 13:20.
 */
@JtComponent
public class TencentHttpClient implements WithLogger
{
	private OkHttpClient client;
	
	public TencentHttpClient()
	{
		client = new OkHttpClient();
	}
	
	public String getLocation(String latitude, String longitude)
	{
		String url = "http://apis.map.qq.com/ws/geocoder/v1/?location=" + latitude + "," +
				longitude + "&get_poi=0&key=XWZBZ" + "-D4XWO-AEPWQ-S3VQU-ZWX2S-GWFPP";
		
		Request request = new Request.Builder().url(url).build();
		
		getP().info("obtaining the location by the tencent service");
		
		Response response = null;
		String jsonStr = null;
		try
		{
			response = client.newCall(request).execute();
			if (response.body()!=null)
			{
				jsonStr = response.body().string();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		if (jsonStr!=null)
		{
			getP().info("obtained the location successfully");
		}
		else
		{
			getP().warn("obtained the location failed");
		}
		return jsonStr;
	}
}
