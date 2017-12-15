package com.jeramtough.jtandroid.controller.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author 11718
 *         on 2017  November 20 Monday 11:41.
 */

public abstract class JtBaseFragment extends Fragment implements View.OnClickListener
{
	private View view;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState)
	{
		view = inflater.inflate(loadFragmentLayoutId(), null);
		return view;
	}
	
	public abstract int loadFragmentLayoutId();
	
	public final <T extends View> T findViewById(@IdRes int viewId) {
		return view.findViewById(viewId);
	}
	
	@Deprecated
	@Override
	public void onClick(View v)
	{
		onClick(v, v.getId());
	}
	
	public void onClick(View v, int viewId)
	{
	
	}
}
