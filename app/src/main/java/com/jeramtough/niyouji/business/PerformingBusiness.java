package com.jeramtough.niyouji.business;

import android.os.Handler;

/**
 * @author 11718
 *         on 2018  January 09 Tuesday 21:33.
 */

public interface PerformingBusiness
{
	void uploadImageFile(String filename, String imageFilePath, Handler handler);
}
