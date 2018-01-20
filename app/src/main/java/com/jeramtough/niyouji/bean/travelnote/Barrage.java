package com.jeramtough.niyouji.bean.travelnote;

/**
 * @author 11718
 */
public class Barrage
{
	private String barrageId;
	private String pageId;
	private String travelnoteId;
	private String nickname;
	private boolean isPerformers;
	private String content;
	private String createTime;
	
	public String getBarrageId()
	{
		return barrageId;
	}
	
	public void setBarrageId(String barrageId)
	{
		this.barrageId = barrageId;
	}
	
	public String getNickname()
	{
		return nickname;
	}
	
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
	
	public boolean getIsPerformers()
	{
		return isPerformers;
	}
	
	public void setIsPerformers(boolean isPerformers)
	{
		this.isPerformers = isPerformers;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}
	
	public String getCreateTime()
	{
		return createTime;
	}
	
	public String getPageId()
	{
		return pageId;
	}
	
	public void setPageId(String pageId)
	{
		this.pageId = pageId;
	}
	
	public String getTravelnoteId()
	{
		return travelnoteId;
	}
	
	public void setTravelnoteId(String travelnoteId)
	{
		this.travelnoteId = travelnoteId;
	}
	
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
}
