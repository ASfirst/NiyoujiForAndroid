package com.jeramtough.jtandroid.ioc.interpreter;

import java.lang.reflect.Field;

/**
 * @author 11718
 *         on 2017  December 06 Wednesday 21:58.
 */

public interface Interpreter
{
	/**
	 * get instance of injected field.
	 * @param field injected field
	 * @return instance of field
	 */
	Object getFieldObject(Field field);
}
