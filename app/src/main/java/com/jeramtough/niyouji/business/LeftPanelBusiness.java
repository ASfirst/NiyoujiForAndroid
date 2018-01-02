package com.jeramtough.niyouji.business;

import android.content.Context;
import com.jeramtough.niyouji.bean.user.PrimaryInfoOfUser;

/**
 * @author 11718
 *         on 2018  January 01 Monday 20:56.
 */

public interface LeftPanelBusiness
{
	boolean hasLogined();
	
	String getGoldCount();
	
	String getSurfaceImageUrl();
	
	String getUserNickname();
	
	/**
	 * 清除游记图片和视频缓存
	 */
	void clearTravelnoteCaches(Context context);
}
