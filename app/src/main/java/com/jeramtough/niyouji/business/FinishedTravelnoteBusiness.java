package com.jeramtough.niyouji.business;

import com.jeramtough.jtandroid.business.BusinessCaller;

/**
 * @author 11718
 *         on 2018  February 11 Sunday 23:05.
 */

public interface FinishedTravelnoteBusiness
{
	boolean userHasLogined();
	
	void publishAppraise(String travelnoteId,String appraiseContent, BusinessCaller
			businessCaller);
	
	void obtainAppraisesCount(String travelnoteId,BusinessCaller businessCaller);
}
