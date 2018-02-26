package com.jeramtough.niyouji.business;

import android.os.Bundle;
import com.jeramtough.jtandroid.business.BusinessCaller;

/**
 * @author 11718
 *         on 2018  January 15 Monday 16:22.
 */

public interface CreateTravelnoteBusiness
{
	void createTravelnote(String travelnoteTitle, String coverResourcePath,
			BusinessCaller createBusinessCaller, BusinessCaller connectBusinessCaller,
			BusinessCaller uploadBusinessCaller,BusinessCaller obtainLocationBusinessCaller);
	
	Bundle checkCustomCoverImage(String imagePath);
}
