package com.jeramtough.niyouji.bean.travelnote;

import java.util.ArrayList;

public class TravelnotePage
{
	private String pageId;
	private String travelnoteId;
	private String createTime;
	private String pageType;
	private int themePosition;
	private String resourceUrl;
	private String textContent;
	private String backgroundMusicPath;
	
	private ArrayList<Barrage> barrages;
	
	public String getTravelnoteId()
	{
		return travelnoteId;
	}
	
	public void setTravelnoteId(String travelnoteId)
	{
		this.travelnoteId = travelnoteId;
	}
	
	public TravelnotePage()
	{
		barrages = new ArrayList<>();
	}
	
	public String getPageId()
	{
		return pageId;
	}
	
	public void setPageId(String pageId)
	{
		this.pageId = pageId;
	}
	
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
	
	public int getThemePosition()
	{
		return themePosition;
	}
	
	public void setThemePosition(int themePosition)
	{
		this.themePosition = themePosition;
	}
	
	public String getResourceUrl()
	{
		return resourceUrl;
	}
	
	public void setResourceUrl(String resourceUrl)
	{
		this.resourceUrl = resourceUrl;
	}
	
	public String getTextContent()
	{
		return textContent;
	}
	
	public void setTextContent(String textContent)
	{
		this.textContent = textContent;
	}
	
	public String getBackgroundMusicPath()
	{
		return backgroundMusicPath;
	}
	
	public void setBackgroundMusicPath(String backgroundMusicPath)
	{
		this.backgroundMusicPath = backgroundMusicPath;
	}
	
	public void addBarrage(Barrage barrage)
	{
		barrages.add(barrage);
	}
	
}
