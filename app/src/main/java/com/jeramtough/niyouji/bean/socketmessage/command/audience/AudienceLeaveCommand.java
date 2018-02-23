package com.jeramtough.niyouji.bean.socketmessage.command.audience;

public class AudienceLeaveCommand extends AudienceCommand
{
	private String audienceNickname;
	
	public String getAudienceNickname()
	{
		return audienceNickname;
	}
	
	public void setAudienceNickname(String audienceNickname)
	{
		this.audienceNickname = audienceNickname;
	}
}
