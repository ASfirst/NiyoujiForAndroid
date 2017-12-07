package com.jeramtough.jtandroid.ioc;

import android.content.Context;

/**
 * @author 11718
 *         on 2017  December 05 Tuesday 22:40.
 */

public interface IocContainer
{
	InjectedObjects getInjectedObjects(Context context);
}
