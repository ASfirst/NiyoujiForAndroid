package com.jeramtough.jtandroid.ioc;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ioc.annotation.InjectView;
import com.jeramtough.jtandroid.ioc.bean.JtField;

import java.lang.annotation.Annotation;
import java.util.Comparator;

/**
 * @author 11718
 *         on 2017  December 07 Thursday 15:15.
 */

public class JtFieldComparator implements Comparator<JtField>
{
	@Override
	public int compare(JtField jtField1, JtField jtField2)
	{
		int a, b = 0;
		a = processComparedNumber(jtField1);
		b = processComparedNumber(jtField2);
		if (a > b)
		{
			return 1;
		}
		else if (a == b)
		{
			return 0;
		}
		else if (a < b)
		{
			return -1;
		}
		return 0;
	}
	
	//******************
	private int processComparedNumber(JtField jtField)
	{
		Annotation annotation = jtField.getAnnotation();
		
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
		return 0;
	}
}
