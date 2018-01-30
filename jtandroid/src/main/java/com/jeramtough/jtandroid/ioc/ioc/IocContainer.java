package com.jeramtough.jtandroid.ioc.ioc;


import android.content.Context;

/**
 * @author 11718
 *         on 2017  December 05 Tuesday 22:40.
 */

public interface IocContainer
{
	/**
	 * inject the component and service of field values into this object
	 *
	 * @param context instance of the Context class
	 * @param object  who be injected
	 */
	void injectComponentAndService(Context context, Object object);
	
	/**
	 * inject the view field values into this object
	 *
	 * @param context instance of the Context class
	 * @param object  who be injected
	 */
	void injectView(Context context, Object object);
	
	/**
	 * inject the component and  service and view of  field values into this object
	 *
	 * @param context instance of the Context class
	 * @param object  who be injected
	 */
	void injectCSV(Context context, Object object);
	
}
