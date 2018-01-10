package com.jeramtough.niyouji.component.ali.sts;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;

/**
 * @author 11718
 * on 2018  January 09 Tuesday 20:14.
 */

public class StsManager
{
	// 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
	public static final String REGION_CN_HANGZHOU = "cn-hangzhou";
	// 当前 STS API 版本
	public static final String STS_API_VERSION = "2015-04-01";
	
	private AssumeRoleResponse.Credentials credentials;
	/**
	 * 只有 RAM用户（子账号）才能调用 AssumeRole 接口
	 * 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
	 * 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
	 */
	private String accessKeyId;
	private String accessKeySecret;
	
	/**
	 * RoleArn 需要在 RAM 控制台上获取
	 */
	private String roleArn;
	
	private long dueTime;
	
	public StsManager(String accessKeyId, String accessKeySecret, String roleArn)
	{
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.roleArn = roleArn;
	}
	
	/**
	 * 凭证对象有效时间为3600秒
	 */
	public AssumeRoleResponse.Credentials getCredentials() throws ClientException
	{
		if (credentials != null && !areCredentialsOverdue())
		{
			return credentials;
		}
		else
		{
			String roleSessionName = "alice-001";
			ProtocolType protocolType = ProtocolType.HTTPS;
			final AssumeRoleResponse response =
					assumeRole(accessKeyId, accessKeySecret, roleArn, roleSessionName, null,
							protocolType);
			AssumeRoleResponse.Credentials credentials = response.getCredentials();
			dueTime = System.currentTimeMillis() + 3000 * 1000;
			return credentials;
		}
	}
	
	//*************
	private AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret,
			String roleArn, String roleSessionName, String policy, ProtocolType protocolType)
			throws ClientException
	{
		try
		{
			// 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
			IClientProfile profile = DefaultProfile
					.getProfile(REGION_CN_HANGZHOU, accessKeyId, accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);
			// 创建一个 AssumeRoleRequest 并设置请求参数
			final AssumeRoleRequest request = new AssumeRoleRequest();
			request.setVersion(STS_API_VERSION);
			request.setMethod(MethodType.POST);
			request.setProtocol(protocolType);
			request.setRoleArn(roleArn);
			request.setRoleSessionName(roleSessionName);
			request.setPolicy(policy);
			// 发起请求，并得到response
			final AssumeRoleResponse response = client.getAcsResponse(request);
			return response;
		}
		catch (ClientException e)
		{
			throw e;
		}
	}
	
	private boolean areCredentialsOverdue()
	{
		if (System.currentTimeMillis() > dueTime)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
