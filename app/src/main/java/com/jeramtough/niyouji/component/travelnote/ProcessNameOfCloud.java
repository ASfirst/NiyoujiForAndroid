package com.jeramtough.niyouji.component.travelnote;

/**
 * @author 11718
 *         on 2018  January 10 Wednesday 22:33.
 */

public class ProcessNameOfCloud
{
	public static String processImageFileName(int travelnoteId,
			LiveTravelnotePageView liveTravelnotePageView)
	{
		String cloudImageFileName =
				travelnoteId + "_" + liveTravelnotePageView.hashCode() + ".jpg ";
		return cloudImageFileName;
	}
	
	
	public static String processVideoFileName(int travelnoteId,
			LiveTravelnotePageView liveTravelnotePageView)
	{
		String ossVideoFileName =
				travelnoteId + "_" + liveTravelnotePageView.hashCode() + ".mp4 ";
		return ossVideoFileName;
	}
	
	public static String processImageFileUrl(String cloudImageFileName)
	{
		return "";
	}
	
	public static String processVideoFileUrl(String cloudVideoFileName)
	{
		return "";
	}
}
