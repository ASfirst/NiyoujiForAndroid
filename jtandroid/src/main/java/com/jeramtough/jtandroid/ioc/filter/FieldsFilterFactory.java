package com.jeramtough.jtandroid.ioc.filter;

/**
 * @author 11718
 *         on 2017  December 07 Thursday 14:11.
 */

public class FieldsFilterFactory
{
	public static InjectionAnnotationFieldsFilter getInjectionAnnotaionFieldsFilter()
	{
		return new InjectionAnnotationFieldsFilter();
	}
	
	public static InjectionMandSFieldsFilter getInjectionMandSFieldsFilter()
	{
		return new InjectionMandSFieldsFilter();
	}
	
	public static InjectionViewFieldsFilter getInjectionViewFieldsFilter()
	{
		return new InjectionViewFieldsFilter();
	}
	
	public static SortingFieldsFilter getSortingFieldsFilter()
	{
		return new SortingFieldsFilter();
	}
}
