package com.jeramtough.niyouji.bean.socketmessage.command.audience;

/**
 * @author 11718
 */
public class EnterPerformingRoomCommand extends AudienceCommand
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
