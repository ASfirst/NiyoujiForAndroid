package com.jeramtough.niyouji.bean.travelnote;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author 11718
 */
public class Travelnote implements Serializable
{
	private String travelnoteId;
	private String performerId;
	private String createTime;
	private String coverType;
	private String coverResourceUrl;
	private String travelnoteTitle;
	private int attentionsCount=0;
	
	private ArrayList<TravelnotePage> travelnotePages;
	
	public Travelnote()
	{
		travelnotePages = new ArrayList<>();
	}
	
	public void addTravelnotePage(TravelnotePage travelnotePage)
	{
		travelnotePages.add(travelnotePage);
	}
	
	public TravelnotePage getTravelnotePage(int position)
	{
		return travelnotePages.get(position);
	}
	
	public void deleteTravelnotePage(int position)
	{
		travelnotePages.remove(position);
	}
	
	public String getTravelnoteId()
	{
		return travelnoteId;
	}
	
	public void setTravelnoteId(String travelnoteId)
	{
		this.travelnoteId = travelnoteId;
	}
	
	public String getCreateTime()
	{
		return createTime;
	}
	
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
	
	public String getCoverType()
	{
		return coverType;
	}
	
	public void setCoverType(String coverType)
	{
		this.coverType = coverType;
	}
	
	public String getCoverResourceUrl()
	{
		return coverResourceUrl;
	}
	
	public void setCoverResourceUrl(String coverResourceUrl)
	{
		this.coverResourceUrl = coverResourceUrl;
	}
	
	public String getTravelnoteTitle()
	{
		return travelnoteTitle;
	}
	
	public void setTravelnoteTitle(String travelnoteTitle)
	{
		this.travelnoteTitle = travelnoteTitle;
	}
	
	public ArrayList<TravelnotePage> getTravelnotePages()
	{
		return travelnotePages;
	}
	
	public String getPerformerId()
	{
		return performerId;
	}
	
	public void setPerformerId(String performerId)
	{
		this.performerId = performerId;
	}
	
	public int getAttentionsCount()
	{
		return attentionsCount;
	}
	
	public void setAttentionsCount(int attentionsCount)
	{
		this.attentionsCount = attentionsCount;
	}
}
