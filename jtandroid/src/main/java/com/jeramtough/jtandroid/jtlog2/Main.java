package com.jeramtough.jtandroid.jtlog2;

/**
 * Created by 11718
 * on 2017  October 15 Sunday 15:18.
 */

public class Main
{
	
	public static void main(String[] arg)
	{
		new Main().b();
	}
	
	private void c()
	{
		b();
	}
	
	private void b()
	{
		a();
	}
	
	private void a()
	{
		int length=((new Exception()).getStackTrace()).length;
		System.out.println(length);
	}
}
