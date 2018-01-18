package com.jeramtough.niyouji.bean.socketmessage.command.performer;

/**
 * @author 11718
 *         on 2018  January 18 Thursday 02:58.
 */

public class PageSetImageCommand extends PerformerCommand
{
	private int position;
	private String imageUrl;
	
	public int getPosition()
	{
		return position;
	}
	
	public void setPosition(int position)
	{
		this.position = position;
	}
	
	public String getImageUrl()
	{
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}
}
