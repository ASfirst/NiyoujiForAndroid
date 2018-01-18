package com.jeramtough.niyouji.bean.socketmessage.command.performer;

/**
 * @author 11718
 *         on 2018  January 18 Thursday 14:42.
 */

public class PerformerCommand
{
	private String performerId;
	
	public PerformerCommand()
	{
	}
	
	public PerformerCommand(String performerId)
	{
		this.performerId = performerId;
	}
	
	public String getPerformerId()
	{
		return performerId;
	}
	
	public void setPerformerId(String performerId)
	{
		this.performerId = performerId;
	}
}
