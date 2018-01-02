package com.jeramtough.jtandroid;

import com.jeramtough.jtandroid.java.ExtractedZip;
import com.jeramtough.jtandroid.util.CommonValidatorUtil;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 11718
 *         on 2017  November 19 Sunday 20:06.
 */

public class Test
{
	@org.junit.Test
	public void test()
	{
		String text = "aaa第三方的说法";
		
		System.out.println(CommonValidatorUtil.isPassword(text));
	}
}

