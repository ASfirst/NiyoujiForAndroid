package com.jeramtough.niyouji.bean.socketmessage.command.performer;

/**
 * @author 11718
 *         on 2018  January 18 Thursday 02:59.
 */

public class PageTextChangeCommand extends PerformerCommand
{
	private int position;
	private boolean isAdded;
	private String words;
	private int start;
	
	public int getPosition()
	{
		return position;
	}
	
	public void setPosition(int position)
	{
		this.position = position;
	}
	
	public boolean isAdded()
	{
		return isAdded;
	}
	
	public void setAdded(boolean added)
	{
		isAdded = added;
	}
	
	public String getWords()
	{
		return words;
	}
	
	public void setWords(String words)
	{
		this.words = words;
	}
	
	public int getStart()
	{
		return start;
	}
	
	public void setStart(int start)
	{
		this.start = start;
	}
}
