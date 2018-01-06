package com.jeramtough.niyouji.component.travelnote;

/**
 * 编写在线游记事件驱动
 * @author 11718
 * on 2018  January 06 Saturday 21:48.
 */

public interface LiveTravelnoteEventsCaller
{
	void onTravelnoteSelectedPage();
	
	void onTravelnoteAddedPage();
	
	void onTravelnoteDeletedPage();
	
	void onPageSetPicture();
	
	void onPageSetVideo();
	
	void onPageContentChanged();
	
	void onPageSetTheme();
	
	void onPageSetBackgroundMusic();
	
	void onTravelnoteSentPerformerBarrage();
	
	void onTravelnoteEnd();
}