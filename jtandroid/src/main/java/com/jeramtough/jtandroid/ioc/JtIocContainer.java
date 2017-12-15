package com.jeramtough.jtandroid.ioc;

import android.app.Activity;
import android.content.Context;
import com.jeramtough.jtandroid.ioc.annotation.*;
import com.jeramtough.jtandroid.ioc.exception.InjectFailedException;
import com.jeramtough.jtandroid.ioc.filter.FieldsFilter;
import com.jeramtough.jtandroid.ioc.filter.FieldsFilterFactory;
import com.jeramtough.jtandroid.ioc.injector.Injector;
import com.jeramtough.jtandroid.ioc.interpreter.ComponentInterpreter;
import com.jeramtough.jtandroid.ioc.interpreter.ServiceInterpreter;
import com.jeramtough.jtandroid.ioc.interpreter.ViewInterpreter;
import com.jeramtough.jtandroid.jtlog2.JtLog2Config;
import com.jeramtough.jtandroid.jtlog2.P;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 11718 on 2017 December 05 Tuesday 22:42.
 */

public class JtIocContainer implements IocContainer, ServiceInterpreter.NeededComponentCaller
{
	public static final String JTLOG2_TAG_NAME = "JtIocContainer";
	
	private static volatile IocContainer iocContainer;
	
	private Map<String, Object> injectedComponents;
	private Map<String, Object> injectedServices;
	
	private final boolean hasLog=false;
	
	private JtIocContainer()
	{
		injectedComponents = new HashMap<>();
		injectedServices = new HashMap<>();
		
		if (!hasLog)
		{
			JtLog2Config.getJtLog2Config().addTagFilter(JTLOG2_TAG_NAME);
		}
		
	}
	
	@Override
	public void injectComponentAndService(Context context, Object object)
	{
		//be used to inject value of field to object.
		Injector injector = new Injector(object);
		
		//traverse all the JtController
		ArrayList<Class> classes = new ArrayList<>();
		Class c = object.getClass();
		while (c.getSuperclass() != null)
		{
			if (c.getAnnotation(JtController.class) != null)
			{
				classes.add(c);
			}
			c = c.getSuperclass();
		}
		Collections.reverse(classes);
		
		//inject fields to all jtObjects
		for (Class jtClass : classes)
		{
			//filting fields by ranking and sorting.
			JtField[] jtFields = FieldsFilterFactory.getInjectionMandSFieldsFilter()
					.filting(jtClass.getDeclaredFields());
			jtFields = FieldsFilterFactory.getSortingFieldsFilter().filting(jtFields);
			
			injectFieldsIntoObject(context, injector, jtFields);
		}
	}
	
	@Override
	public void injectView(Context context, Object object)
	{
		//be used to inject value of field to object.
		Injector injector = new Injector(object);
		
		//get the jtClass with views
		Class jtClass = object.getClass();
		
		//filting
		FieldsFilter fieldsFilter = FieldsFilterFactory.getInjectionViewFieldsFilter();
		JtField[] jtFields = fieldsFilter.filting(jtClass.getDeclaredFields());
		
		//inject
		injectFieldsIntoObject(context, injector, jtFields);
	}
	
	@Override
	public void injectCSV(Context context, Object object)
	{
		//be used to inject value of field to object.
		Injector injector = new Injector(object);
		
		//traverse all the JtController
		ArrayList<Class> classes = new ArrayList<>();
		Class c = object.getClass();
		while (c.getSuperclass() != null)
		{
			if (c.getAnnotation(JtController.class) != null)
			{
				classes.add(c);
			}
			c = c.getSuperclass();
		}
		Collections.reverse(classes);
		
		//inject fields to all jtObjects
		for (Class jtClass : classes)
		{
			//filting fields by ranking and sorting.
			JtField[] jtFields = FieldsFilterFactory.getInjectionAnnotaionFieldsFilter()
					.filting(jtClass.getDeclaredFields());
			jtFields = FieldsFilterFactory.getSortingFieldsFilter().filting(jtFields);
			
			injectFieldsIntoObject(context, injector, jtFields);
		}
	}
	
	public static IocContainer getIocContainer()
	{
		if (iocContainer == null)
		{
			synchronized (JtIocContainer.class)
			{
				if (iocContainer == null)
				{
					iocContainer = new JtIocContainer();
				}
			}
		}
		return iocContainer;
	}
	
	@Override
	public Object needComponent(Context context, Class c)
	{
		ComponentInterpreter componentInterpreter = new ComponentInterpreter(context);
		Object fieldObject = componentInterpreter.instanceFieldObject(c);
		injectedComponents.put(IocUtil.processKeyName(c), fieldObject);
		return fieldObject;
	}
	
	// *****************************
	
	private void injectFieldsIntoObject(Context context, Injector injector, JtField[] jtFields)
	{
		int newInjectedObjectsCount = 0;
		int newInjectedViewsCount = 0;
		for (JtField jtField : jtFields)
		{
			String fieldKeyName = IocUtil.processKeyName(jtField.getField());
			Object filedValueObject = null;
			if (jtField.getAnnotation() instanceof InjectComponent)
			{
				filedValueObject = injectedComponents.get(fieldKeyName);
				if (filedValueObject == null)
				{
					ComponentInterpreter componentInterpreter =
							new ComponentInterpreter(context);
					filedValueObject =
							componentInterpreter.getFieldValueObject(jtField.getField());
					if (filedValueObject != null)
					{
						JtComponent jtComponentAnnotation =
								filedValueObject.getClass().getAnnotation(JtComponent.class);
						
						if (jtComponentAnnotation.pattern() == JtObjectType.Singleton)
						{
							injectedComponents.put(fieldKeyName, filedValueObject);
						}
						else if (jtComponentAnnotation.pattern() == JtObjectType.Prototype)
						{
							newInjectedObjectsCount--;
						}
					}
				}
				else
				{
					newInjectedObjectsCount--;
				}
			}
			else if (jtField.getAnnotation() instanceof InjectView)
			{
				ViewInterpreter viewInterpreter = new ViewInterpreter((Activity) context);
				filedValueObject = viewInterpreter.getFieldValueObject(jtField.getField());
				if (filedValueObject != null)
				{
					newInjectedViewsCount++;
					newInjectedObjectsCount--;
				}
			}
			else if (jtField.getAnnotation() instanceof InjectService)
			{
				filedValueObject = injectedServices.get(fieldKeyName);
				if (filedValueObject == null)
				{
					ServiceInterpreter serviceInterpreter =
							new ServiceInterpreter(context, injectedComponents);
					serviceInterpreter.setNeededComponentCaller(this);
					filedValueObject =
							serviceInterpreter.getFieldValueObject(jtField.getField());
					if (filedValueObject != null)
					{
						JtService jtServiceAnnotation =
								filedValueObject.getClass().getAnnotation(JtService.class);
						if (jtServiceAnnotation.pattern() == JtObjectType.Singleton)
						{
							injectedServices.put(fieldKeyName, filedValueObject);
						}
						else if (jtServiceAnnotation.pattern() == JtObjectType.Prototype)
						{
							newInjectedObjectsCount--;
						}
					}
					
				}
				else
				{
					newInjectedObjectsCount--;
				}
			}
			
			if (filedValueObject != null)
			{
				injector.injectObjectToField(jtField.getField(), filedValueObject);
				newInjectedObjectsCount++;
			}
			else
			{
				throw new InjectFailedException(jtField.getField().getType());
			}
		}
		String text = "The [" + injector.getToObject().getClass().getName() +
				"] was successfully injected " + "into " +
				+(jtFields.length - newInjectedViewsCount) + " the CSField and " +
				newInjectedViewsCount + " ViewField .Now " + newInjectedObjectsCount +
				" new Field objects inject to the IocContainer.";
		P.info(JTLOG2_TAG_NAME, text);
	}
	
}
