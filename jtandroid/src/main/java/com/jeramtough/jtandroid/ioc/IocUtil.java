package com.jeramtough.jtandroid.ioc;

import java.lang.reflect.Field;

/**
 * @author 11718
 *         on 2017  December 07 Thursday 01:46.
 */

public class IocUtil
{
	public static String processKeyName(Field field)
	{
		String fieldName=field.getType().getSimpleName();
		fieldName=toLowerCaseFirstOne(fieldName);
		return fieldName;
	}
	
	public static String processKeyName(Class c)
	{
		String fieldName=c.getSimpleName();
		fieldName=toLowerCaseFirstOne(fieldName);
		return fieldName;
	}
	
	public static String toLowerCaseFirstOne(String s)
	{
		if (Character.isLowerCase(s.charAt(0)))
		{
			return s;
		}
		else
		{
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0)))
					.append(s.substring(1)).toString();
		}
	}
}
