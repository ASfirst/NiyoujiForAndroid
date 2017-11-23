package com.jeramtough.niyouji.controller.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class SelectFilterDialog extends BottomPopupDialog
		implements AdapterView.OnItemClickListener
{
	private FiltersHandler filtersHandler;
	private SelectFilterListener selectFilterListener;
	
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
		
		horizontalListViewFilters = linearLayout.findViewById(R.id.horizontalListView_filters);
		
		FiltersAdapter adapter =
				new FiltersAdapter(getContext(), filtersHandler.getCameraFilters());
		horizontalListViewFilters.setAdapter(adapter);
		
		horizontalListViewFilters.setOnItemClickListener(this);
		
		setContentView(linearLayout);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		if (selectFilterListener!=null)
		{
			if (position!=0)
			{
				selectFilterListener.selectedFilter(filtersHandler.getCameraFilters().get(position));
			}
			else
			{
				selectFilterListener.selectedFilter(new CameraFilter(null));
			}
			this.cancel();
		}
	}
	
	public void setSelectFilterListener(SelectFilterListener selectFilterListener)
	{
		this.selectFilterListener = selectFilterListener;
	}
	
	//{{{{{{{{{{{{{{{}}}}}}}}}}}}}}}}}}}}}
	public interface SelectFilterListener
	{
		void selectedFilter(CameraFilter cameraFilter);
	}
}
