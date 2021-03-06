package com.jeramtough.niyouji.component.travelnote;

/**
 * 编写在线游记事件驱动
 *
 * @author 11718
 *         on 2018  January 06 Saturday 21:48.
 */

public interface LiveTravelnoteEventsCaller
{
	void onTravelnoteSelectedPage(int position);
	
	void onTravelnoteAddedPage(LiveTravelnotePageView liveTravelnotePageView);
	
	void onTravelnoteDeletedPage(int position);
	
	void onPageSetPicture(int position, String imageUrl);
	
	void onPageSetVideo(int position, String videoUrl);
	
	void onPageContentChanged(int position, boolean isAdded, String words, int start);
	
	void onPageSetTheme(int position, int themePosition);
	
	void onPageSetBackgroundMusic(int position, String musicPath);
	
	void onTravelnoteSentPerformerBarrage(int position, String barrageContent);
	
	void onTravelnoteEnd();
}
