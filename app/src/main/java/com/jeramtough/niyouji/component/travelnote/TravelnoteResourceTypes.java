package com.jeramtough.niyouji.component.travelnote;

/**
 * @author 11718
 *         on 2018  January 16 Tuesday 19:09.
 */

public enum TravelnoteResourceTypes
{
	IMAGE("image"), VIDEO("video");
	
	private String resourceType;
	
	TravelnoteResourceTypes(String resourceType)
	{
		this.resourceType = resourceType;
	}
	
	@Override
	public String toString()
	{
		return resourceType;
	}
}
