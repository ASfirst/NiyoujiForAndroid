package com.jeramtough.niyouji.bean.socketmessage.command.audience;

import java.io.Serializable;

/**
 * @author 11718
 *         on 2018  January 24 Wednesday 22:59.
 */

public class AudienceCommand implements Serializable
{
	private String performerId;
	
	
	public String getPerformerId()
	{
		return performerId;
	}
	
	public void setPerformerId(String performerId)
	{
		this.performerId = performerId;
	}
}
