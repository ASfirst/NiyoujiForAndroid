package com.jeramtough.jtemoji;

/**
 * @author JeramTough
 *         on 2017  December 20 Wednesday 23:56.
 */

public class JtEmoji
{
	private int imageResId;
	private String placeholder;
	
	public JtEmoji(int imageResId, String placeholder)
	{
		this.imageResId = imageResId;
		this.placeholder = placeholder;
	}
	
	public int getImageResId()
	{
		return imageResId;
	}
	
	public void setImageResId(int imageResId)
	{
		this.imageResId = imageResId;
	}
	
	public String getPlaceholder()
	{
		return placeholder;
	}
	
	public void setPlaceholder(String placeholder)
	{
		this.placeholder = placeholder;
	}
	
}
