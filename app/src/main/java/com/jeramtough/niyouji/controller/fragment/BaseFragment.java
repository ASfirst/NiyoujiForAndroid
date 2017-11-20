package com.jeramtough.niyouji.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author 11718
 *         on 2017  November 20 Monday 11:41.
 */

public abstract class BaseFragment extends Fragment
{
	
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState)
	{
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
}
