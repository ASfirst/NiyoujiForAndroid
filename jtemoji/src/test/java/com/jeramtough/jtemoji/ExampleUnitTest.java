package com.jeramtough.jtemoji;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
	@Test
	public void addition_isCorrect() throws Exception
	{
		for (int i = 1; i <= 42; i++)
		{
			String a = "jtEmojis.put(\"[a"+i+"]\", new JtEmoji(R.drawable.a"+i+", \"[a"+i+"]\"));";
			System.out.println(a);
		}
	}
	
	@Test
	public void test1() throws Exception
	{
		String emojiContentStr = "dfjksad[a1][a23][a11]";
		
		String regexEmotion = "\\[[a-z][0-9]+\\]";
		Pattern patternEmotion = Pattern.compile(regexEmotion);
		Matcher matcherEmotion = patternEmotion.matcher(emojiContentStr);
		while (matcherEmotion.find())
		{
			// 获取匹配到的具体字符
			String key = matcherEmotion.group();
			// 匹配字符串的开始位置
			int start = matcherEmotion.start();
			System.out.println(key+","+start);
		}
	}
	@Test
	public void test2() throws Exception
	{
		int pageCount=1/18;
		if (42%18>0)
		{
			pageCount=pageCount+1;
		}
		System.out.println(pageCount);
	}
}