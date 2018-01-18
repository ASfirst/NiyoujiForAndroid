package com.jeramtough.niyouji.bean.socketmessage.command.performer;

/**
 * @author 11718
 *         on 2018  January 18 Thursday 02:59.
 */

public class PageSetVideoCommand extends PerformerCommand
{
	private int position;
	private String videoUrl;
	
	public PageSetVideoCommand()
	{
	}
	
	public PageSetVideoCommand(String performerId)
	{
		super(performerId);
	}
	
	public int getPosition()
	{
		return position;
	}
	
	public void setPosition(int position)
	{
		this.position = position;
	}
	
	public String getVideoUrl()
	{
		return videoUrl;
	}
	
	public void setVideoUrl(String videoUrl)
	{
		this.videoUrl = videoUrl;
	}
}
