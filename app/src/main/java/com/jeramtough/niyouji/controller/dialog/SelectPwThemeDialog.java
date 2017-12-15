package com.jeramtough.niyouji.controller.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.jeramtough.jtandroid.controller.dialog.BottomPopupDialog;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ui.HorizontalListView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.adapter.PwThemeSurfacesAdapter;
import com.jeramtough.niyouji.component.picandword.PicAndWordResourcesHandler;
import com.jeramtough.niyouji.component.picandword.PicAndWordTheme;
import com.jeramtough.niyouji.component.picandword.PicAndWordThemeImpl;
import com.jeramtough.niyouji.component.picandword.PwResourceCache;
import com.jeramtough.niyouji.component.picandword.PwResourcePosition;
import com.jeramtough.niyouji.component.picandword.PwResourcesCacheManager;

/**
 * @author 11718 on 2017 December 11 Monday 12:05.
 */
public class SelectPwThemeDialog extends BottomPopupDialog implements OnItemClickListener
{
	
	@InjectComponent
	private PicAndWordResourcesHandler picAndWordResourcesHandler;
	
	@InjectComponent
	private PwResourcesCacheManager pwResourcesCacheManager;
	
	private HorizontalListView horizontalListViewPwthemes;
	
	private SelectPwthemeListener selectPwthemeListener;
	
	@IocAutowire
	public SelectPwThemeDialog(Context context)
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
		
		PwThemeSurfacesAdapter pwThemeSurfacesAdapter =
				new PwThemeSurfacesAdapter(this.getContext(),
						picAndWordResourcesHandler.getPwResourcePositions());
		horizontalListViewPwthemes.setAdapter(pwThemeSurfacesAdapter);
		
		horizontalListViewPwthemes.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		
		this.selectTheme(position);
		
		this.cancel();
	}
	
	public void setSelectPwthemeListener(SelectPwthemeListener selectPwthemeListener)
	{
		this.selectPwthemeListener = selectPwthemeListener;
	}
	
	public void selectTheme(int position)
	{
		PwResourcePosition pwResourcePosition =
				picAndWordResourcesHandler.getPwResourcePositions().get(position);
		PwResourceCache pwResourceCache =
				pwResourcesCacheManager.getPwResourceCache(pwResourcePosition);
		PicAndWordTheme picAndWordTheme =
				new PicAndWordThemeImpl(getContext(), pwResourceCache);
		
		if (selectPwthemeListener != null)
		{
			selectPwthemeListener.onSelectedPicAndWordTheme(picAndWordTheme);
		}
	}
	
	// {{{{{{{{}}}}}}}}}}}}}}}}}}}
	public interface SelectPwthemeListener
	{
		void onSelectedPicAndWordTheme(PicAndWordTheme picAndWordTheme);
	}
	
}
