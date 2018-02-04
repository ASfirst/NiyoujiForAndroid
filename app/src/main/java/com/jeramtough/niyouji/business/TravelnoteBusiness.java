package com.jeramtough.niyouji.business;

import android.content.Context;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.niyouji.bean.travelnote.LiveTravelnoteCover;

/**
 * @author 11718
 *         on 2018  January 20 Saturday 17:21.
 */

public interface TravelnoteBusiness
{
	boolean checkTheNetwork(Context context);
	
	void getTravelnoteCovers(BusinessCaller businessCaller);
	
	void getFinishedTravelnoteCovers(BusinessCaller businessCaller);
	
	void getMoreFinishedTravelnoteCovers(BusinessCaller businessCaller,int endTravelnoteId);
}
