package com.jeramtough.jtandroid.ioc.filter;

import com.jeramtough.jtandroid.ioc.JtField;

import java.lang.reflect.Field;

/**
 * @author 11718
 *         on 2017  December 07 Thursday 14:27.
 */

public abstract class BaseFieldsFilter implements FieldsFilter
{
	BaseFieldsFilter()
	{}
	
	@Override
	public JtField[] filting(Field[] fields)
	{
		return new JtField[0];
	}
	
	@Override
	public JtField[] filting(JtField[] fields)
	{
		return new JtField[0];
	}
}
