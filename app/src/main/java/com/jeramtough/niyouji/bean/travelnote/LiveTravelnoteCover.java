package com.jeramtough.niyouji.bean.travelnote;

import java.io.Serializable;

/**
 * @author 11718
 */
public class LiveTravelnoteCover implements Serializable
{
	private String performerId;
	private String createTime;
	private String coverType;
	private String coverResourceUrl;
	private String travelnoteTitle;
	private int attentionsCount = 0;
	private int audiencesCount;
	private String performerNickname;
	
	public String getPerformerId()
	{
		return performerId;
	}
	
	public void setPerformerId(String performerId)
	{
		this.performerId = performerId;
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
	
	public int getAttentionsCount()
	{
		return attentionsCount;
	}
	
	public void setAttentionsCount(int attentionsCount)
	{
		this.attentionsCount = attentionsCount;
	}
	
	public String getPerformerNickname()
	{
		return performerNickname;
	}
	
	public void setPerformerNickname(String performerNickname)
	{
		this.performerNickname = performerNickname;
	}
	
	public int getAudiencesCount()
	{
		return audiencesCount;
	}
	
	public void setAudiencesCount(int audiencesCount)
	{
		this.audiencesCount = audiencesCount;
	}
}
