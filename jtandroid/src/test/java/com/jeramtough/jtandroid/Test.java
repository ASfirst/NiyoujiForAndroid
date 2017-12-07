package com.jeramtough.jtandroid;

import com.jeramtough.jtandroid.java.ExtractedZip;

import java.io.File;

/**
 * @author 11718
 *         on 2017  November 19 Sunday 20:06.
 */

public class Test
{
	@org.junit.Test
	public void test()
	{
		try
		{
			Class.forName("com.jeramtough.jtandroid.MyInjectedObjects").newInstance();
		}
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}

