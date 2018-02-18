package com.jeramtough.niyouji;

import android.content.Context;
import android.location.Location;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.alibaba.fastjson.JSON;
import com.jeramtough.jtandroid.function.LocationHolder;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.bean.locaiton.TencentLocationServiceResponse;
import com.jeramtough.niyouji.component.httpclient.NiyoujiHttpClient;
import com.jeramtough.niyouji.component.httpclient.TencentHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author 11718
 *         on 2018  February 18 Sunday 12:55.
 */
@RunWith(AndroidJUnit4.class)
public class LocationTest
{
	@Test
	public void useAppContext() throws Exception
	{
		// Context of the app under test.
		Context appContext = InstrumentationRegistry.getTargetContext();
		
		LocationHolder locationHolder = new LocationHolder(appContext);
		Location location = locationHolder.getLocation();
		
		TencentHttpClient tencentHttpClient = new TencentHttpClient();
		String locationJs = tencentHttpClient
				.getLocation(location.getLatitude() + "", location.getLongitude() + "");
		P.debug(locationJs);
		TencentLocationServiceResponse tencentLocationServiceResponse =
				JSON.parseObject(locationJs, TencentLocationServiceResponse.class);
		P.debug(tencentLocationServiceResponse.getResult().getAddress());
	}
}
