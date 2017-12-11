package com.jeramtough.niyouji.controller.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jeramtough.jtandroid.controller.dialog.BottomPopupDialog;
import com.jeramtough.jtandroid.ioc.JtIocContainer;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.JtController;
import com.jeramtough.jtandroid.jtlog2.P;
import com.jeramtough.jtandroid.ui.HorizontalListView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.adapter.PwThemeSurfacesAdapter;
import com.jeramtough.niyouji.component.picandword.PicAndWordResourcesHandler;

/**
 * @author 11718
 *         on 2017  December 11 Monday 12:05.
 */
public class SelectPwThemeDialog extends BottomPopupDialog
{
	@InjectComponent
	private PicAndWordResourcesHandler picAndWordResourcesHandler;
	
	private HorizontalListView horizontalListViewPwthemes;
	
	public SelectPwThemeDialog(@NonNull Context context)
	{
		super(context);
	}
	
	@Override
	public View loadView(LayoutInflater inflater)
	{
		ViewGroup viewGroup =
				(ViewGroup) inflater.inflate(R.layout.dialog_select_pwtheme, null);
		return viewGroup;
	}
	
	@Override
	public void onViewIsCreated(View view)
	{
		horizontalListViewPwthemes = view.findViewById(R.id.horizontalListView_pwthemes);
		
		PwThemeSurfacesAdapter pwThemeSurfacesAdapter=new PwThemeSurfacesAdapter(this
				.getContext(),picAndWordResourcesHandler.getPicAndWordResources());
		horizontalListViewPwthemes.setAdapter(pwThemeSurfacesAdapter);
	}
}
