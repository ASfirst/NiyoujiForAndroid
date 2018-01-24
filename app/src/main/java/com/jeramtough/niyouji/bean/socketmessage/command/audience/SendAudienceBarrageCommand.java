package com.jeramtough.niyouji.bean.socketmessage.command.audience;

/**
 * @author 11718
 *         on 2018  January 24 Wednesday 22:51.
 */

public class SendAudienceBarrageCommand extends AudienceCommand
{
	private int position;
	private String nickname;
	private boolean isPerformers;
	private String content;
	private String createTime;
	
	public int getPosition()
	{
		return position;
	}
	
	public void setPosition(int position)
	{
		this.position = position;
	}
	
	public String getNickname()
	{
		return nickname;
	}
	
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
	
	public boolean isPerformers()
	{
		return isPerformers;
	}
	
	public void setPerformers(boolean performers)
	{
		isPerformers = performers;
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
