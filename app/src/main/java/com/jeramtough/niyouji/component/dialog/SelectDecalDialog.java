package com.jeramtough.niyouji.component.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import com.jeramtough.jtandroid.ui.popupdialog.BottomPopupDialog;
import com.jeramtough.niyouji.R;

/**
 * @author 11718
 *         on 2017  November 23 Thursday 01:32.
 */

public class SelectDecalDialog extends BottomPopupDialog
{
	public SelectDecalDialog(@NonNull Context context)
	{
		super(context);
	}
	
	@Override
	public View loadView(LayoutInflater inflater)
	{
		View view = inflater.inflate(R.layout.dialog_select_decals, null);
		return view;
	}
}
