package com.jeramtough.niyouji.bean.socketmessage.command.performer;

/**
 * @author 11718
 * on 2018  January 18 Thursday 03:02.
 */

public class SendPerformerBarrageCommand extends PerformerCommand
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
	
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
}
