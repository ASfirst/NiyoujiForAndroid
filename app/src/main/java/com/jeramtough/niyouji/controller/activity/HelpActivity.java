package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.jeramtough.niyouji.R;

/**
 * @author 11718
 */
public class HelpActivity extends AppBaseActivity
{
	private AppCompatImageButton imageButtonBack;
	private Button buttonSubmit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		imageButtonBack = findViewById(R.id.imageButton_back);
		buttonSubmit = findViewById(R.id.button_submit);
		
		imageButtonBack.setOnClickListener(this);
		buttonSubmit.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.imageButton_back:
				this.finish();
				break;
			case R.id.button_submit:
				Toast.makeText(this, "反馈以提交", Toast.LENGTH_SHORT).show();
				break;
		}
	}
}
