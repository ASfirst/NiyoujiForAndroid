package com.jeramtough.niyouji.bean.landr;

/**
 * @author 11718
 *         on 2017  December 30 Saturday 13:23.
 */

public class InputtingLegality
{
	private boolean isPassed;
	private String illegalMessage;
	
	public InputtingLegality()
	{
	}
	
	public InputtingLegality(boolean isPassed, String illegalMessage)
	{
		this.isPassed = isPassed;
		this.illegalMessage = illegalMessage;
	}
	
	public boolean isPassed()
	{
		return isPassed;
	}
	
	public void setPassed(boolean passed)
	{
		isPassed = passed;
	}
	
	public String getIllegalMessage()
	{
		return illegalMessage;
	}
	
	public void setIllegalMessage(String illegalMessage)
	{
		this.illegalMessage = illegalMessage;
	}
	
	@Override
	public String toString()
	{
		return "InputtingLegality{" + "isPassed=" + isPassed + ", illegalMessage='" +
				illegalMessage + '\'' + '}';
	}
}
