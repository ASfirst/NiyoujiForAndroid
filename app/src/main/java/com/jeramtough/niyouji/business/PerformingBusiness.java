package com.jeramtough.niyouji.business;

import android.content.Context;
import android.os.Handler;
import com.jeramtough.jtandroid.business.BusinessCaller;

/**
 * @author 11718
 *         on 2018  January 09 Tuesday 21:33.
 */

public interface PerformingBusiness
{
	int getTravelnoteId();
	
	void uploadImageFile(Context context,String filename, String imageFilePath, BusinessCaller
			businessCaller);
	
	void uploadVideoFile(String filename, String videoFilePath, BusinessCaller businessCaller);
	
}
