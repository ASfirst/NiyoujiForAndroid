package com.jeramtough.jtandroid.ioc.interpreter;

import android.content.Context;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * @author 11718
 *         on 2017  December 06 Wednesday 22:03.
 */

public class ComponentInterpreter implements Interpreter
{
	
	private Context context;
	
	public ComponentInterpreter(Context context)
	{
		this.context = context;
	}
	
	@Override
	public Object getFieldValueObject(Field field)
	{
		return this.instanceFieldObject(field.getType());
	}
	
	public Object instanceFieldObject(Class c)
	{
		Object fieldObject = null;
		for (Annotation annotation : c.getAnnotations())
		{
			if (annotation instanceof JtComponent)
			{
				try
				{
					for (Constructor constructor : c.getConstructors())
					{
						if (constructor.getParameterTypes().length == 0)
						{
							fieldObject = constructor.newInstance();
							break;
						}
						else
						{
							if (constructor.getAnnotation(IocAutowire.class) != null ||
									constructor.getParameterTypes().length == 0)
							{
								ArrayList<Object> contructorParameter = new ArrayList<>();
								for (Class<?> c1 : constructor.getParameterTypes())
								{
									if (c1.isAssignableFrom(context.getClass()))
									{
										contructorParameter.add(context);
									}
								}
								fieldObject = constructor.newInstance(contructorParameter
										.toArray(new Object[contructorParameter.size()]));
								break;
							}
						}
					}
				}
				catch (InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e)
				{
					e.printStackTrace();
				}
			}
		}
		return fieldObject;
	}
	
	
}
