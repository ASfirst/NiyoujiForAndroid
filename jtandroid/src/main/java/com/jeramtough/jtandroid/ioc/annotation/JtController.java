package com.jeramtough.jtandroid.ioc.annotation;

import java.lang.annotation.*;

/**
 * @author 11718
 *         on 2017  December 07 Thursday 21:25.
 */
//允许子类继承父类的注解 the annotation of subclass is able to extend.
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JtController
{
}
