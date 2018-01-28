package com.jeramtough.niyouji.component.httpclient;

import android.app.Application;
import com.alibaba.fastjson.JSON;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtlog3.P;
import com.jeramtough.jtlog3.WithLogger;
import com.jeramtough.niyouji.bean.landr.*;
import com.jeramtough.niyouji.bean.user.PrimaryInfoOfUser;
import okhttp3.*;

import java.io.IOException;

/**
 * @author 11718
 *         on 2018  January 28 Sunday 20:16.
 */
@JtComponent
public class RandLHttpClient implements WithLogger
{
	private OkHttpClient client;
	private String sessionId;
	private final String baseUrl = "http://192.168.0.117:8666/randl/";
	//	private final String baseUrl = "http://112.74.51.247:8666/randl/";
	
	@IocAutowire
	public RandLHttpClient()
	{
		client = new OkHttpClient();
		sessionId = this.hashCode() + "" + RandLHttpClient.class.hashCode();
	}
	
	public boolean sendVerificationCode(String phoneNumber)
	{
		String url =
				baseUrl + "sendVerificationCode?phoneNumber=" + phoneNumber + "&sessionId=" +
						sessionId;
		//&isTest=true
		Request request = new Request.Builder().url(url).build();
		try
		{
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			P.debug(result);
			if (result.contains("666"))
			{
				getP().info("sent the sms verification code to phone[" + phoneNumber +
						"] successfully.");
				return true;
			}
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		getP().warn("sent the sms verification code to phone[" + phoneNumber + "] failed.");
		return false;
	}
	
	public RegisterResponse registerNewUser(String phoneNumber, String password,
			String smsVerificationCode)
	{
		RegisterResponse registerResponse = new RegisterResponse();
		
		String url = baseUrl + "register/phoneUsers?sessionId=" + sessionId;
		
		RegisterRequest registerRequest = new RegisterRequest();
		RegisterRequestMessage message = new RegisterRequestMessage();
		message.setSmsVerificationCode(smsVerificationCode);
		message.setPassword(password);
		message.setPhoneNumber(phoneNumber);
		registerRequest.setMessage(message);
		
		P.debug(JSON.toJSONString(registerRequest));
		
		RequestBody body = RequestBody.create(MediaType.parse("application/json"),
				JSON.toJSONString(registerRequest));
		
		Request request = new Request.Builder().url(url).post(body).build();
		try
		{
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			
			P.debug(result);
			
			com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result);
			
			int statusCode = jsonObject.getIntValue("statusCode");
			if (statusCode == 666)
			{
				registerResponse.setSuccessful(true);
				getP().info("registered successfully");
				return registerResponse;
			}
			else
			{
				registerResponse.setFailedMessage(jsonObject.getString("message"));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		registerResponse.setSuccessful(false);
		getP().warn("registered failed");
		return registerResponse;
	}
	
	public LoginResponse userLogin(String phoneNumber, String password)
	{
		LoginResponse loginResponse = new LoginResponse();
		String url = baseUrl + "login/users";
		
		LoginRequest loginRequest = new LoginRequest();
		LoginRequestMessage message = new LoginRequestMessage();
		message.setPassword(password);
		message.setUep(phoneNumber);
		loginRequest.setMessage(message);
		
		RequestBody body = RequestBody
				.create(MediaType.parse("application/json"), JSON.toJSONString(loginRequest));
		
		Request request = new Request.Builder().url(url).post(body).build();
		
		try
		{
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			
			com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result);
			
			int statusCode = jsonObject.getIntValue("statusCode");
			if (statusCode == 666)
			{
				
				getP().info("logined successfully");
				PrimaryInfoOfUser primaryInfoOfUser = new PrimaryInfoOfUser();
				
				jsonObject = jsonObject.getJSONObject("message");
				
				primaryInfoOfUser.setUserId(jsonObject.getString("userId"));
				primaryInfoOfUser.setNickname(jsonObject.getString("nickname"));
				primaryInfoOfUser.setPhoneNumber(jsonObject.getString("phoneNumber"));
				primaryInfoOfUser.setSurfaceImageUrl(jsonObject.getString("surfaceImageUrl"));
				
				loginResponse.setSuccessful(true);
				loginResponse.setPrimaryInfoOfUser(primaryInfoOfUser);
				
				return loginResponse;
			}
			else
			{
				loginResponse.setFailedMessage(jsonObject.getString("message"));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		loginResponse.setSuccessful(false);
		getP().warn("logined failed");
		return loginResponse;
	}
}
