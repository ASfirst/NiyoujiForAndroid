package com.jeramtough.niyouji.component.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import com.jeramtough.jtandroid.jtlog2.P;
import com.jeramtough.jtandroid.ui.HorizontalListView;
import com.jeramtough.jtandroid.ui.popupdialog.BottomPopupDialog;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.adapter.FiltersAdapter;
import com.jeramtough.niyouji.component.ali.CameraFilter;
import com.jeramtough.niyouji.component.ali.FiltersHandler;

/**
 * @author 11718
 *         on 2017  November 22 Wednesday 19:11.
 */

public class SelectFilterDialog extends BottomPopupDialog implements View.OnClickListener
{
	private FiltersHandler filtersHandler;
	
	private Button btnDone;
	private HorizontalListView horizontalListViewFilters;
	
	public SelectFilterDialog(@NonNull Context context, FiltersHandler filtersHandler)
	{
		super(context);
		this.filtersHandler = filtersHandler;
		
		initView();
	}
	
	protected void initView()
	{
		LinearLayout linearLayout =
				(LinearLayout) getInflater().inflate(R.layout.dialog_select_filter, null);
		
		btnDone = linearLayout.findViewById(R.id.btn_done);
		horizontalListViewFilters = linearLayout.findViewById(R.id.horizontalListView_filters);
		
		btnDone.setOnClickListener(this);
		
		FiltersAdapter adapter =
				new FiltersAdapter(getContext(), filtersHandler.getCameraFilters());
		horizontalListViewFilters.setAdapter(adapter);
		
		setContentView(linearLayout);
	}
	
	@Override
	public void onClick(View v)
	{
	
	}
	
	//{{{{{{{{{{{{{{{}}}}}}}}}}}}}}}}}}}}}
	public interface SelectFilterListener
	{
		void selectedFilter(CameraFilter cameraFilter);
	}
}
