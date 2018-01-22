package com.jeramtough.niyouji.component.travelnote;

/**
 * @author 11718
 *         on 2017  November 30 Thursday 16:16.
 */

public enum TravelnotePageType
{
	/**
	 * 图文页
	 */
	PICANDWORD("picture_and_word"),
	
	/**
	 * 视屏页
	 */
	VIDEO("video");
	
	private String pageType;
	
	TravelnotePageType(String pageType)
	{
		this.pageType = pageType;
	}
	
	@Override
	public String toString()
	{
		return pageType;
	}
	
	public static TravelnotePageType toTravelnotePageType(String travelnotePageTypeStr)
	{
		if (travelnotePageTypeStr.equals(TravelnotePageType.PICANDWORD.toString()))
		{
			return TravelnotePageType.PICANDWORD;
		}
		else
		{
			return TravelnotePageType.VIDEO;
		}
		
	}
	
}
