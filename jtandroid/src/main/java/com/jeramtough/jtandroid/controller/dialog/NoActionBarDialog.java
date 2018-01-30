package com.jeramtough.jtandroid.controller.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import com.jeramtough.jtandroid.R;

/**
 * @author 11718
 *         on 2018  January 30 Tuesday 20:32.
 */

public class NoActionBarDialog extends Dialog
{
	public NoActionBarDialog(@NonNull Context context)
	{
		super(context, R.style.NoActionBarDialog);
	}
	
}
