package com.jeramtough.niyouji;

import com.jeramtough.jtandroid.util.CommonValidatorUtil;

/**
 * @author 11718
 *         on 2017  November 21 Tuesday 15:04.
 */

public class Test
{
	@org.junit.Test
	public void test()
	{
		String a="abcde111";
		String b="123456";
		if ((CommonValidatorUtil.isPassword(a) == false) ||(
				CommonValidatorUtil.isPassword(b) == false))
		{
			System.out.println("a");
		}
		else
		{
			System.out.println("b");
		}
	}
	
	@org.junit.Test
	public void test1()
	{
	}
	
	@org.junit.Test
	public void test2()
	{
	}
}
