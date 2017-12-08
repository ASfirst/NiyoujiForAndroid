package com.jeramtough.jtandroid.ioc.interpreter;

import android.content.Context;
import com.jeramtough.jtandroid.ioc.IocUtil;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ioc.annotation.JtService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author 11718
 *         on 2017  December 06 Wednesday 23:04.
 */

public class ServiceInterpreter implements Interpreter
{
	private Context context;
	private Map<String, Object> injectedComponents;
	private NeededComponentCaller neededComponentCaller;
	
	public ServiceInterpreter(Context context, Map<String, Object> injectedComponents)
	{
		this.context = context;
		this.injectedComponents = injectedComponents;
	}
	
	@Override
	public Object getFieldObject(Field field)
	{
		Object fieldObject = null;
		
		InjectService injectService = field.getAnnotation(InjectService.class);
		Class serverClass = injectService.service();
		for (Annotation annotation : serverClass.getDeclaredAnnotations())
		{
			if (annotation instanceof JtService)
			{
				try
				{
					for (Constructor constructor : serverClass.getConstructors())
					{
						ArrayList<Object> constructorParameters = new ArrayList<>();
						for (Class<?> c : constructor.getParameterTypes())
						{
							String fieldKeyName = IocUtil.processKeyName(c);
							Object constructorParameter = injectedComponents.get(fieldKeyName);
							if (constructorParameter == null && neededComponentCaller != null)
							{
								constructorParameter =
										neededComponentCaller.needComponent(context, c);
							}
							
							if (constructorParameter == null)
							{
								throw new IllegalStateException(
										"fail to interpreted the " + "[" +
												serverClass.getSimpleName() +
												"] instance because the parameter[" +
												fieldKeyName + "] " + " of constructor");
							}
							
							constructorParameters.add(constructorParameter);
						}
						fieldObject = constructor.newInstance(constructorParameters
								.toArray(new Object[constructorParameters.size()]));
						if (fieldObject != null)
						{
							break;
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
	
	public void setNeededComponentCaller(NeededComponentCaller neededComponentCaller)
	{
		this.neededComponentCaller = neededComponentCaller;
	}
	
	//{{{{{{{{{{{{{{{}}}}}}}}}}}}}}}}}}}}}}}}
	public interface NeededComponentCaller
	{
		Object needComponent(Context context, Class c);
	}
}
