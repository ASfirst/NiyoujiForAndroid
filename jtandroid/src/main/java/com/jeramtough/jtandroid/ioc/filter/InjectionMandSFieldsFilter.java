package com.jeramtough.jtandroid.ioc.filter;

import com.jeramtough.jtandroid.ioc.bean.JtField;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author 11718
 *         on 2017  December 07 Thursday 14:08.
 */

public class InjectionMandSFieldsFilter extends BaseFieldsFilter
{
	@Override
	public JtField[] filting(Field[] fields)
	{
		ArrayList<JtField> jtFields = new ArrayList<>();
		for (Field field : fields)
		{
			for (Annotation annotation : field.getDeclaredAnnotations())
			{
				if (annotation instanceof InjectComponent ||
						annotation instanceof InjectService)
				{
					JtField jtField = new JtField(annotation, field);
					jtFields.add(jtField);
					break;
				}
			}
		}
		return jtFields.toArray(new JtField[jtFields.size()]);
	}
}
