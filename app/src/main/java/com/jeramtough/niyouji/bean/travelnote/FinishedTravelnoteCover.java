package com.jeramtough.niyouji.bean.travelnote;

import java.io.Serializable;

/**
 * @author 11718
 */
public class FinishedTravelnoteCover extends LiveTravelnoteCover
{
	private String travelnoteId;
	private TravelnotePage firstTravelnotePage, secondTravelnotePage;
	
	public TravelnotePage getFirstTravelnotePage()
	{
		return firstTravelnotePage;
	}
	
	public void setFirstTravelnotePage(TravelnotePage firstTravelnotePage)
	{
		this.firstTravelnotePage = firstTravelnotePage;
	}
	
	public TravelnotePage getSecondTravelnotePage()
	{
		return secondTravelnotePage;
	}
	
	public void setSecondTravelnotePage(TravelnotePage secondTravelnotePage)
	{
		this.secondTravelnotePage = secondTravelnotePage;
	}
	
	public String getTravelnoteId()
	{
		return travelnoteId;
	}
	
	public void setTravelnoteId(String travelnoteId)
	{
		this.travelnoteId = travelnoteId;
	}
}
