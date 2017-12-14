package com.jeramtough.jtandroid.ioc;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ioc.annotation.InjectView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Comparator;

/**
 * @author 11718
 *         on 2017  December 07 Thursday 02:23.
 */

public class FieldComparator implements Comparator<Field>
{
	@Override
	public int compare(Field field1, Field field2)
	{
		int a,b=0;
		a=processComparedNumber(field1);
		b=processComparedNumber(field2);
		if (a>b)
		{
			return 1;
		}
		else if(a==b)
		{
			return 0;
		}
		else if(a<b)
		{
			return -1;
		}
		return 0;
	}
	private int processComparedNumber(Field field)
	{
		for (Annotation annotation : field.getDeclaredAnnotations())
		{
			if (annotation instanceof InjectView)
			{
				return 1;
			}
			else if (annotation instanceof InjectComponent)
			{
				return 2;
			}
			else if (annotation instanceof InjectService)
			{
				return 3;
			}
		}
		return 0;
	}
}
