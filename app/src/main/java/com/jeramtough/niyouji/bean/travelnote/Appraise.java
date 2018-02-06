package com.jeramtough.niyouji.bean.travelnote;

public class Appraise
{
	private String appraiseId;
	private String travelnoteId;
	private String nickname;
	private String content;
	private String createTime;
	
	public String getAppraiseId()
	{
		return appraiseId;
	}
	
	public void setAppraiseId(String appraiseId)
	{
		this.appraiseId = appraiseId;
	}
	
	public String getTravelnoteId()
	{
		return travelnoteId;
	}
	
	public void setTravelnoteId(String travelnoteId)
	{
		this.travelnoteId = travelnoteId;
	}
	
	public String getNickname()
	{
		return nickname;
	}
	
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
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
	
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
}
