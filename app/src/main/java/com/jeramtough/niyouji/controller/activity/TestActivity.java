package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.jtlog2.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.picandword.PicAndWordResourcesHandler;
import com.jeramtough.niyouji.controller.dialog.SelectPwThemeDialog;

/**
 * @author 11718
 */
public class TestActivity extends AppBaseActivity
{
	private ImageButton btnDeletePage;
	@InjectComponent
	private PicAndWordResourcesHandler picAndWordResourcesHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		btnDeletePage = findViewById(R.id.btn_delete_page);
		btnDeletePage.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.btn_delete_page:
				SelectPwThemeDialog dialog = new SelectPwThemeDialog(this);
				dialog.show();
				break;
		}
	}
}
