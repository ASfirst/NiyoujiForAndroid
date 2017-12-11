package com.jeramtough.jtandroid.controller.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.jeramtough.jtandroid.ioc.IocContainer;
import com.jeramtough.jtandroid.ioc.JtIocContainer;
import com.jeramtough.jtandroid.ioc.annotation.JtController;

import java.util.Date;

/**
 * @author 11718
 *         on 2017  December 11 Monday 13:07.
 */
@JtController
public class JtIocDialog extends Dialog
{
	public JtIocDialog(@NonNull Context context)
	{
		super(context);
		getIocContainer().injectComponentAndService(getContext(), this);
	}
	
	public JtIocDialog(@NonNull Context context, int themeResId)
	{
		super(context, themeResId);
		getIocContainer().injectComponentAndService(getContext(), this);
		
	}
	
	public JtIocDialog(@NonNull Context context, boolean cancelable,
			@Nullable OnCancelListener cancelListener)
	{
		super(context, cancelable, cancelListener);
		getIocContainer().injectComponentAndService(getContext(), this);
	}
	
	public IocContainer getIocContainer()
	{
		return JtIocContainer.getIocContainer();
	}
}
