package com.jeramtough.niyouji.bean.socketmessage.command.performer;

/**
 * @author 11718
 *         on 2018  January 17 Wednesday 20:39.
 */

public class DeletePageCommand extends PerformerCommand
{
	private int position;
	
	public int getPosition()
	{
		return position;
	}
	
	public void setPosition(int position)
	{
		this.position = position;
	}
	
}
