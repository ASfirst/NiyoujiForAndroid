package com.jeramtough.jtandroid.ioc.injector;

import java.lang.reflect.Field;

/**
 * @author 11718
 *         on 2017  December 07 Thursday 14:54.
 */

public class Injector
{
	private Object toObject;
	
	public Injector(Object toObject)
	{
		this.toObject = toObject;
	}
	
	public void injectObjectToField(Field field, Object fieldValueObject)
	{
		field.setAccessible(true);
		try
		{
			field.set(toObject, fieldValueObject);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
	
	public Object getToObject()
	{
		return toObject;
	}
}
