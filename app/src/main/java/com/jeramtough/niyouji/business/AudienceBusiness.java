package com.jeramtough.niyouji.business;

import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.niyouji.bean.travelnote.LiveTravelnoteCover;

/**
 * @author 11718
 *         on 2018  January 20 Saturday 22:23.
 */

public interface AudienceBusiness
{
	void enterPerformingRoom(String performerId, BusinessCaller enterRoomBusinessCaller,
			BusinessCaller obtainingLiveTravelnoteBusinessCaller);
	
	void callPerformerActions(String performerId,
			BusinessCaller performerActionsBusinessCaller);
	
	void callAudienceActions(BusinessCaller audienceActionsBusinessCaller);
	
	void broadcastAudienceSendBarrage(String performerId, int position,
			String broadcastContent);
	
	void broadcastLightAttentionCount(String performerId);
	
	void broadcastAudienceLeave(String performerId);
}
