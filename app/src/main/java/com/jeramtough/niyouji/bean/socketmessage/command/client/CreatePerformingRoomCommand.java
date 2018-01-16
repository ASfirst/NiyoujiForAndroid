package com.jeramtough.niyouji.bean.socketmessage.command.client;

/**
 * @author 11718
 */
public class CreatePerformingRoomCommand
{
	private String roomId;
	
	private String createTime;
	
	private String ownerId;
	
	private String coverType;
	
	private String coverResourceUrl;
	
	private String travelnoteTitle;
	
	
	public String getRoomId()
	{
		return roomId;
	}
	
	public void setRoomId(String roomId)
	{
		this.roomId = roomId;
	}
	
	public String getCreateTime()
	{
		return createTime;
	}
	
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
	
	public String getOwnerId()
	{
		return ownerId;
	}
	
	public void setOwnerId(String ownerId)
	{
		this.ownerId = ownerId;
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
	
	@Override
	public String toString()
	{
		return "CreatePerformingRoomCommand{" + "roomId='" + roomId + '\'' + ", createTime='" +
				createTime + '\'' + ", ownerId='" + ownerId + '\'' + ", coverType='" +
				coverType + '\'' + ", coverResourceUrl='" + coverResourceUrl + '\'' +
				", travelnoteTitle='" + travelnoteTitle + '\'' + '}';
	}
}
