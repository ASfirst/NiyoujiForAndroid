package com.jeramtough.niyouji.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.controller.activity.PerformingActivity;

/**
 * @author 11718
 *         on 2017  November 20 Monday 12:00.
 */

public class PerformanceFragment extends BaseFragment
{
	private Button btnTest;
	
	
	@Override
	public int loadFragmentLayoutId()
	{
		return R.layout.fragment_performance;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		btnTest = (Button) findViewById(R.id.btn_test);
		btnTest.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v, int viewId)
	{
		switch (viewId)
		{
			case R.id.btn_test:
				IntentUtil.toTheOtherActivity(this.getActivity(), PerformingActivity.class);
				break;
		}
	}
}
