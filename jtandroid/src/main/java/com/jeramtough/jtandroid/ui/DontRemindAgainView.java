package com.jeramtough.jtandroid.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author 11718
 *         on 2018  March 01 Thursday 21:09.
 */

public class DontRemindAgainView extends LinearLayout
{
	private TextView textView;
	private CheckBox checkBox;
	
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	
	private String tagKey;
	
	public DontRemindAgainView(Context context)
	{
		super(context);
		this.initViews();
		this.initResources();
	}
	
	public DontRemindAgainView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
		this.initViews();
		this.initResources();
	}
	
	protected void initViews()
	{
		this.setGravity(Gravity.CENTER);
		this.setOrientation(LinearLayout.HORIZONTAL);
		
		textView = new TextView(getContext());
		checkBox = new CheckBox(getContext());
		
		textView.setText("下次不再提示");
		
		this.addView(textView);
		this.addView(checkBox);
	}
	
	protected void initResources()
	{
		if (getTag() == null)
		{
			throw new RuntimeException("no the tag attribute as key");
		}
		else
		{
			tagKey = getTag().toString();
		}
		
		sharedPreferences =
				getContext().getSharedPreferences(this.getClass().getSimpleName(), 0);
		editor = sharedPreferences.edit();
		
		checkBox.setChecked(isDontRemind());
		
		textView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				checkBox.setChecked(!checkBox.isChecked());
			}
		});
		
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				editor.putBoolean(tagKey, isChecked);
				editor.apply();
			}
		});
	}
	
	public boolean isDontRemind()
	{
		return sharedPreferences.getBoolean(tagKey, false);
	}
	
	public TextView getTextView()
	{
		return textView;
	}
	
	public CheckBox getCheckBox()
	{
		return checkBox;
	}
	
	public void setOnCheckedChangeListener(
			CompoundButton.OnCheckedChangeListener onCheckedChangeListener)
	{
		checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
	}
}
