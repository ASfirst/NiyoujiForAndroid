package com.jeramtough.niyouji.component.ali.sts;

/**
 * @author 11718
 * on 2018  January 09 Tuesday 20:20.
 */

public class StsManagerFactory
{
	public static StsManager getNiyoujiStsManager()
	{
		String accessKeyId = "LTAIy7ouY87UNtr0";
		String accessKeySecret = "pd4XmI1HgToTas1XMfmdtCt7w8OXlO";
		String roleArn = "acs:ram::1809179353317966:role/niyoujirole";
		StsManager stsManager = new StsManager(accessKeyId, accessKeySecret, roleArn);
		return stsManager;
	}
}
