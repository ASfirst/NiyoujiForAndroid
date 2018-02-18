package com.jeramtough.niyouji.bean.socketmessage.command.performer;

/**
 * @author 11718
 */
public class CreatePerformingRoomCommand extends PerformerCommand
{
	private String createTime;
	
	private String coverType;
	
	private String coverResourceUrl;
	
	private String travelnoteTitle;
	
	private String location;
	
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
	
	public String getLocation()
	{
		return location;
	}
	
	public void setLocation(String location)
	{
		this.location = location;
	}
}
