package com.jeramtough.jtandroid.ioc.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author 11718
 *         on 2017  December 07 Thursday 14:19.
 */

public class JtField
{
	private Annotation annotation;
	private Field field;
	
	public JtField(Annotation annotation, Field field)
	{
		this.annotation = annotation;
		this.field = field;
	}
	
	public Annotation getAnnotation()
	{
		return annotation;
	}
	
	public void setAnnotation(Annotation annotation)
	{
		this.annotation = annotation;
	}
	
	public Field getField()
	{
		return field;
	}
	
	public void setField(Field field)
	{
		this.field = field;
	}
}
