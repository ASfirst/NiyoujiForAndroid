package com.jeramtough.jtandroid.ioc.interpreter;

import android.app.Activity;
import android.view.ViewGroup;
import com.jeramtough.jtandroid.ioc.annotation.InjectView;

import java.lang.reflect.Field;

/**
 * @author 11718
 *         on 2017  December 06 Wednesday 23:10.
 */

public class ViewInterpreter implements Interpreter
{
	
	private ViewGroup viewGroup;
	
	public ViewInterpreter(ViewGroup viewGroup)
	{
		this.viewGroup = viewGroup;
	}
	
	public ViewInterpreter(Activity activity)
	{
		viewGroup = (ViewGroup) activity.getWindow().getDecorView();
	}
	
	@Override
	public Object getFieldValueObject(Field field)
	{
		InjectView annotation=field.getAnnotation(InjectView.class);
		return viewGroup.findViewById(annotation.value());
	}
	
	
}
