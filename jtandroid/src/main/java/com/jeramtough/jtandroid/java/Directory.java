package com.jeramtough.jtandroid.java;

import android.support.annotation.NonNull;

import java.io.File;

/**
 * @author 11718
 *         on 2017  November 21 Tuesday 14:24.
 */

public class Directory extends File
{
	
	public Directory(@NonNull String pathname)
	{
		super(pathname);
	}
	
	@Override
	public boolean exists()
	{
		if (isDirectory() && super.exists())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
