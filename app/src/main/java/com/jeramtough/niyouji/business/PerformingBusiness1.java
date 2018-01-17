package com.jeramtough.niyouji.business;

import com.jeramtough.niyouji.component.travelnote.LiveTravelnotePageView;

/**
 * @author 11718
 *         on 2018  January 17 Wednesday 18:39.
 */

public interface PerformingBusiness1
{
	void spreadTravelnoteSelectedPage(int position);
	
	void spreadTravelnoteAddedPage(LiveTravelnotePageView liveTravelnotePageView);
	
	void spreadTravelnoteDeletedPage(int position);
	
	void spreadPageSetPicture(int position, String imageUrl);
	
	void spreadPageSetVideo(int position, String videoUrl);
	
	void spreadPageContentChanged(int position, boolean isAdded, String words, int start);
	
	void spreadPageSetTheme(int position, int themePosition);
	
	void spreadPageSetBackgroundMusic(int position, String musicPath);
	
	void spreadTravelnoteSentPerformerBarrage(int position, String barrageContent);
	
	void spreadTravelnoteEnd();
}
