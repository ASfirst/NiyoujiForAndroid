package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.*;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.listener.OnTextChangedListner;
import com.jeramtough.jtemoji.JtEmojiCachesManager;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.business.PerformingBusiness;
import com.jeramtough.niyouji.business.PerformingService;
import com.jeramtough.niyouji.component.app.AppConfig;
import com.jeramtough.niyouji.component.ui.DanmakuLayout;

import java.io.File;

/**
 * @author 11718
 */
public class TestActivity extends AppBaseActivity
{
	private EditText editText1;
	private EditText editText2;
	private Button btn1;
	
	@InjectService(service = PerformingService.class)
	private PerformingBusiness performingBusiness;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		editText1 = findViewById(R.id.editText1);
		editText2 = findViewById(R.id.editText2);
		btn1 = findViewById(R.id.btn1);
		
		btn1.setOnClickListener(this);
		
	}
	
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.btn1:
				String imageFilePath =
						AppConfig.getAppDirecotry() + File.separator + "test.jpg";
				performingBusiness
						.uploadImageFile("test.jpg", imageFilePath,
								getActivityUiHandler());
				break;
		}
	}
	
}
