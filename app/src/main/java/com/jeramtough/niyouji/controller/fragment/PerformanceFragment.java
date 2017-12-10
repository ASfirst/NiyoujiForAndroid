package com.jeramtough.niyouji.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import com.jeramtough.jtandroid.ioc.annotation.InjectView;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.controller.activity.PerformingActivity;
import com.jeramtough.niyouji.controller.activity.TestActivity;

/**
 * @author 11718
 *         on 2017  November 20 Monday 12:00.
 */

public class PerformanceFragment extends AppBaseFragment
{
	@InjectView(R.id.btn_test1)
	private Button btnTest1;
	
	@InjectView(R.id.btn_test2)
	private Button btnTest2;
	
	@InjectView(R.id.btn_test3)
	private Button btnTest3;
	
	
	@Override
	public int loadFragmentLayoutId()
	{
		return R.layout.fragment_performance;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		getIocContainer().injectView(getContext(), this);
		btnTest1.setOnClickListener(this);
		btnTest2.setOnClickListener(this);
		btnTest3.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v, int viewId)
	{
		switch (viewId)
		{
			case R.id.btn_test1:
				IntentUtil.toTheOtherActivity(this.getActivity(), PerformingActivity.class);
				break;
			case R.id.btn_test2:
				IntentUtil.toTheOtherActivity(this.getActivity(), TestActivity.class);
				break;
			case R.id.btn_test3:
				break;
		}
	}
}
