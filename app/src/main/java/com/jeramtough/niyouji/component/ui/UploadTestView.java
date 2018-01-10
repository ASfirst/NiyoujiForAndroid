package com.jeramtough.niyouji.component.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * @author 11718
 *         on 2018  January 10 Wednesday 19:13.
 */

public class UploadTestView extends android.support.v7.widget.AppCompatTextView
{
	private DecimalFormat df;
	
	public UploadTestView(Context context)
	{
		super(context);
		initResources();
	}
	
	public UploadTestView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
		initResources();
	}
	
	protected void initResources()
	{
		this.setBackgroundColor(0x85000000);
		this.setTextColor(Color.WHITE);
		this.setGravity(Gravity.CENTER);
		df = new DecimalFormat("######0.0");
	}
	
	public void setProcessOfUpdatingImage(float percent)
	{
		setText("正在上传图片\n" + df.format(percent * 100) + "%");
		if (percent == 1.0f)
		{
			this.setVisibility(View.INVISIBLE);
		}
		else
		{
			if (this.getVisibility() == View.INVISIBLE)
			{
				this.setVisibility(View.VISIBLE);
			}
		}
	}
	
	public void setProcessOfUpdatingVideo(float percent)
	{
		setText("正在上传视频\n" + df.format(percent * 100) + "%");
		if (percent == 1.0f)
		{
			this.setVisibility(View.INVISIBLE);
		}
		else
		{
			if (this.getVisibility() == View.INVISIBLE)
			{
				this.setVisibility(View.VISIBLE);
			}
		}
	}
}
