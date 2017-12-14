package com.jeramtough.jtandroid.ioc.exception;

/**
 * @author JeramTough
 *         on 2017  December 15 Friday 00:37.
 */

public class InjectFailedException extends RuntimeException
{
	public InjectFailedException(Class c)
	{
		super("inject ["+c.getName()+"] failed");
	}
}
