package com.jeramtough.niyouji.component.travelnote;

/**
 * @author 11718
 *         on 2018  January 10 Wednesday 22:33.
 */

public class ProcessNameOfCloud
{
	public static String processImageCoverName()
	{
		return "cover_"+System.currentTimeMillis()+".jpg";
	}
	
	public static String processVideoCoverName()
	{
		return "cover_"+System.currentTimeMillis()+".mp4";
	}
	
	public static String processImageFileName(String userId,
			LiveTravelnotePageView liveTravelnotePageView)
	{
		String cloudImageFileName =
				"img_" + userId + "_" + liveTravelnotePageView.hashCode() + ".jpg";
		return cloudImageFileName;
	}
	
	
	public static String processVideoFileName(String userId,
			LiveTravelnotePageView liveTravelnotePageView)
	{
		String ossVideoFileName =
				"vdo_" + userId + "_" + liveTravelnotePageView.hashCode() + ".mp4";
		return ossVideoFileName;
	}
	
	public static String processImageFileUrl(String cloudImageFileName)
	{
		String url =
				"http://niyouji.oss-cn-shenzhen.aliyuncs.com/images/" + cloudImageFileName;
		return url;
	}
	
	public static String processVideoFileUrl(String cloudVideoFileName)
	{
		String url =
				"http://niyouji.oss-cn-shenzhen.aliyuncs.com/videos/" + cloudVideoFileName;
		return url;
	}
}
