package com.jeramtough.jtandroid.ui.popupdialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.*;
import android.widget.FrameLayout;
import com.jeramtough.jtandroid.R;
import com.jeramtough.jtandroid.function.ScreenHandler;
import com.jeramtough.jtandroid.jtlog2.P;

/**
 * @author 11718
 *         on 2017  November 20 Monday 20:08.
 */

public abstract class BottomPopupDialog extends Dialog
{
	private LayoutInflater inflater;
	private View view;
	
	public BottomPopupDialog(@NonNull Context context)
	{
		super(context, R.style.BottomPopupDialog);
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		Window window = this.getWindow();
		if (window != null)
		{
			//设置dialog的布局样式 让其位于底部
			window.setGravity(Gravity.BOTTOM);
			WindowManager.LayoutParams params = window.getAttributes();
			window.getDecorView().setPadding(30, 0, 30, 0);
			params.width = ViewGroup.LayoutParams.MATCH_PARENT;
			params.y = 30;
			// 一定要重新设置, 才能生效
			window.setAttributes(params);
		}
		
		view = loadView(inflater);
		setContentView(view);
		onViewCreated(view);
	}
	
	public abstract View loadView(LayoutInflater inflater);
	
	public void onViewCreated(View view)
	{
	
	}
}
