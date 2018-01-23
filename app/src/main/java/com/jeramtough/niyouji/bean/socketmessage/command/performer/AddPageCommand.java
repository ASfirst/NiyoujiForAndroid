package com.jeramtough.niyouji.bean.socketmessage.command.performer;

import java.io.Serializable;

/**
 * @author 11718
 *         on 2018  January 17 Wednesday 20:33.
 */

public class AddPageCommand extends PerformerCommand
{
	private String createTime;
	private String pageType;
	
	public String getCreateTime()
	{
		return createTime;
	}
	
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
	
	public String getPageType()
	{
		return pageType;
	}
	
	public void setPageType(String pageType)
	{
		this.pageType = pageType;
	}
}
