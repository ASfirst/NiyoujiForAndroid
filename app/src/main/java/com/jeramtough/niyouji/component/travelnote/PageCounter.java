package com.jeramtough.niyouji.component.travelnote;

import android.content.Context;
import android.content.SharedPreferences;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

/**
 * @author 11718
 *         on 2018  January 30 Tuesday 19:24.
 */
@JtComponent
public class PageCounter
{
	private Context context;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	
	@IocAutowire
	public PageCounter(Context context)
	{
		this.context = context;
		sharedPreferences = context.getSharedPreferences("how many there pages", 0);
		editor = sharedPreferences.edit();
	}
	
	public int getPageCount()
	{
		SharedPreferences sharedPreferences =
				context.getSharedPreferences("how many there pages", 0);
		return sharedPreferences.getInt("pageCount", 0);
	}
	
	public void setPageCount(int count)
	{
		editor.putInt("pageCount", count);
		editor.apply();
	}
}
