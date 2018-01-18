package com.jeramtough.niyouji.bean.socketmessage.command.performer;

/**
 * @author 11718
 *         on 2018  January 18 Thursday 03:01.
 */

public class PageSetThemeCommand extends PerformerCommand
{
	private int position;
	private int themePosition;
	
	public int getPosition()
	{
		return position;
	}
	
	public void setPosition(int position)
	{
		this.position = position;
	}
	
	public int getThemePosition()
	{
		return themePosition;
	}
	
	public void setThemePosition(int themePosition)
	{
		this.themePosition = themePosition;
	}
}
