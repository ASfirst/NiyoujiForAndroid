package com.jeramtough.niyouji.business;

import android.content.Context;
import com.jeramtough.jtandroid.business.BusinessCaller;

/**
 * @author 11718
 *         on 2018  January 09 Tuesday 21:33.
 */

public interface PerformingBusiness
{
	String getUserId();
	
	String getNicknameOfPerformer();
	
	void uploadImageFile(Context context, String filename, String imageFilePath,
			BusinessCaller businessCaller);
	
	void uploadVideoFile(Context context, String filename, String videoFilePath,
			BusinessCaller businessCaller);
	
	void saveCurrentPageCount(Context context, int count);
	
	int getPageCountBefore(Context context);
}
