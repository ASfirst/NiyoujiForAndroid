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
		ExtractedZip extractedZip = new ExtractedZip(new File(
				"E:\\codes\\AndroidStudentCodes\\NiYouJi\\jtandroid\\src\\test\\lalala" +
						"\\musics" + ".zip"));
		extractedZip.extract(
				"E:\\codes\\AndroidStudentCodes\\NiYouJi\\jtandroid\\src\\test\\lalala\\abc");
	}
}

