package com.jeramtough.jtandroid.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jeramtough.jtandroid.ioc.ioc.IocContainer;
import com.jeramtough.jtandroid.ioc.ioc.JtIocContainer;
import com.jeramtough.jtandroid.ioc.annotation.JtController;

/**
 * @author 11718
 *         on 2017  December 09 Saturday 20:03.
 */
@JtController
public abstract class JtIocFragment extends JtBaseFragment
{
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState)
	{
		getIocContainer().injectComponentAndService(getContext(), this);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public IocContainer getIocContainer()
	{
		return JtIocContainer.getIocContainer();
	}
}
