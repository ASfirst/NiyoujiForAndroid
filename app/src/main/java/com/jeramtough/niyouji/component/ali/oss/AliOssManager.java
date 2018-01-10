package com.jeramtough.niyouji.component.ali.oss;

import android.content.Context;
import android.util.Log;
import com.alibaba.sdk.android.oss.*;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtandroid.jtlog2.P;

/**
 * @author 11718
 *         on 2018  January 09 Tuesday 21:50.
 */
@JtComponent
public class AliOssManager
{
	private OSS oss;
	private Context context;
	
	@IocAutowire
	public AliOssManager(Context context)
	{
		this.context = context;
	}
	
	public void connect(String stsTokenAccessKeyId, String stsTokenSecretKeyId,
			String stsTokenSecurityToken)
	{
		String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
		// 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考访问控制章节
		// 也可查看sample 中 sts 使用方式了解更多(https://github.com/aliyun/aliyun-oss-android-sdk/tree/master/app/src/main/java/com/alibaba/sdk/android/oss/app)
		OSSCredentialProvider credentialProvider =
				new OSSStsTokenCredentialProvider(stsTokenAccessKeyId, stsTokenSecretKeyId,
						stsTokenSecurityToken);
		//该配置类如果不设置，会有默认配置，具体可看该类
		ClientConfiguration conf = new ClientConfiguration();
		// 连接超时，默认15秒
		conf.setConnectionTimeout(15 * 1000);
		// socket超时，默认15秒
		conf.setSocketTimeout(15 * 1000);
		// 最大并发请求数，默认5个
		conf.setMaxConcurrentRequest(5);
		// 失败后最大重试次数，默认2次
		conf.setMaxErrorRetry(2);
		OSSLog.enableLog();
		oss = new OSSClient(context, endpoint, credentialProvider);
	}
	
	public void updateImageFile(String filename, String imageFilePath)
	{
		PutObjectRequest put = new PutObjectRequest("niyouji", filename, imageFilePath);
		try
		{
			PutObjectResult putResult = oss.putObject(put);
			P.info("PutObject", "UploadSuccess");
			P.info("ETag", putResult.getETag());
			P.info("RequestId", putResult.getRequestId());
		}
		catch (ClientException e)
		{
			// 本地异常如网络异常等
			e.printStackTrace();
		}
		catch (ServiceException e)
		{
			// 服务异常
			P.error("RequestId", e.getRequestId());
			P.error("ErrorCode", e.getErrorCode());
			P.error("HostId", e.getHostId());
			P.error("RawMessage", e.getRawMessage());
		}
	}
}
