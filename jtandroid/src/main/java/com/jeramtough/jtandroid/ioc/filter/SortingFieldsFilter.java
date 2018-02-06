package com.jeramtough.jtandroid.ioc.filter;

import com.jeramtough.jtandroid.ioc.bean.JtField;
import com.jeramtough.jtandroid.ioc.JtFieldComparator;

import java.util.Arrays;

/**
 * @author 11718
 *         on 2017  December 07 Thursday 15:13.
 */

public class SortingFieldsFilter extends BaseFieldsFilter
{
	private JtFieldComparator jtFieldComparator;
	
	 SortingFieldsFilter()
	{
		this.jtFieldComparator = new JtFieldComparator();
	}
	
	@Override
	public JtField[] filting(JtField[] jtFields)
	{
		Arrays.sort(jtFields, jtFieldComparator);
		return jtFields;
	}
}
