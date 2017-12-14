package com.jeramtough.jtandroid.ioc.filter;

import com.jeramtough.jtandroid.ioc.JtField;

import java.lang.reflect.Field;

/**
 * @author 11718
 *         on 2017  December 07 Thursday 14:04.
 */

public interface FieldsFilter
{
	JtField[] filting(Field[] fields);
	
	JtField[] filting(JtField[] fields);
}
