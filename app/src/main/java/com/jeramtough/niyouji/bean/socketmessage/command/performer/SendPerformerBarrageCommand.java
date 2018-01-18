package com.jeramtough.niyouji.bean.socketmessage.command.performer;

/**
 * @author 11718
 *         on 2018  January 18 Thursday 03:02.
 */

public class SendPerformerBarrageCommand extends PerformerCommand
{
	private int position;
	private String barrageContent;
	
	public int getPosition()
	{
		return position;
	}
	
	public void setPosition(int position)
	{
		this.position = position;
	}
	
	public String getBarrageContent()
	{
		return barrageContent;
	}
	
	public void setBarrageContent(String barrageContent)
	{
		this.barrageContent = barrageContent;
	}
}
