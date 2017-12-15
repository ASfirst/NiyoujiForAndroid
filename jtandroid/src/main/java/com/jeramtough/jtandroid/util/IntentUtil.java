package com.jeramtough.jtandroid.util;

import android.app.Activity;
import android.content.Intent;

/**
 * @author 11718
 *         on 2017  November 19 Sunday 20:58.
 */

public class IntentUtil
{
	public static void toTheOtherActivity(Activity currentActivity,
			Class theOtherActivityClass)
	{
		Intent intent = new Intent(currentActivity, theOtherActivityClass);
		currentActivity.startActivity(intent);
	}
	
	public static void toTheOtherActivity(Activity currentActivity,
			Class theOtherActivityClass, int requestCode)
	{
		Intent intent = new Intent(currentActivity, theOtherActivityClass);
		currentActivity.startActivityForResult(intent, requestCode);
	}
}
