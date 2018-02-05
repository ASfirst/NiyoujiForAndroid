package com.jeramtough.niyouji;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jeramtough.niyouji.component.httpclient.NiyoujiHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AndroidTest
{
	
	@Test
	public void useAppContext() throws Exception
	{
		// Context of the app under test.
		Context appContext = InstrumentationRegistry.getTargetContext();
		
		NiyoujiHttpClient niyoujiHttpClient=new NiyoujiHttpClient();
		niyoujiHttpClient.getLiveTravelnoteCoversBlocking();

	}
}
