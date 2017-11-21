package com.jeramtough.niyouji;

/**
 * @author 11718
 *         on 2017  November 21 Tuesday 15:04.
 */

public class Test
{
	@org.junit.Test
	public void test()
	{
	}
	
	@org.junit.Test
	public void test1()
	{
		try
		{
			ZipUtil.unZip(
					"E:\\codes\\AndroidStudentCodes\\NiYouJi\\app\\src\\test\\lalala\\filters.zip",
					"E:\\codes\\AndroidStudentCodes\\NiYouJi\\app\\src\\test\\lalala\\abc");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void test2()
	{
	}
}
