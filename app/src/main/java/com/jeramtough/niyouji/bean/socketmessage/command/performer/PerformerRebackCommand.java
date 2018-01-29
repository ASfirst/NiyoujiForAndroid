package com.jeramtough.niyouji.bean.socketmessage.command.performer;

public class PerformerRebackCommand extends PerformerCommand
{
	private int attentionsCount=0;
	private int audiencesCount=0;
	
	public int getAttentionsCount()
	{
		return attentionsCount;
	}
	
	public void setAttentionsCount(int attentionsCount)
	{
		this.attentionsCount = attentionsCount;
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
