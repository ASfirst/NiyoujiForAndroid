package com.jeramtough.niyouji.bean.travelnote;

/**
 * @author 11718
 */
public class FinishedTravelnoteCover extends LiveTravelnoteCover
{
	private String travelnoteId;
	private TravelnotePage firstTravelnotePage, secondTravelnotePage;
	private int appraiseCount;
	
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
	
	public int getAppraiseCount()
	{
		return appraiseCount;
	}
	
	public void setAppraiseCount(int appraiseCount)
	{
		this.appraiseCount = appraiseCount;
	}
}
