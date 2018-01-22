package com.jeramtough.niyouji.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import com.jeramtough.jtandroid.listener.OnTextChangedListner;
import com.jeramtough.jtlog3.P;
import com.jeramtough.jtutil.StringUtil;
import com.jeramtough.niyouji.R;

public class Test1Activity extends AppCompatActivity
{
	private EditText textView1;
	private EditText textView2;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test1);
		
		textView1 = findViewById(R.id.textView_1);
		textView2 = findViewById(R.id.textView_2);
		
		textView1.addTextChangedListener(new OnTextChangedListner()
		{
			@Override
			public void onAddWords(String words, int start)
			{
				a(true, start, words);
			}
			
			@Override
			public void onDeletedWords(String words, int start)
			{
				P.debug(start);
				a(false, start, words);
				
			}
		});
	}
	
	private void a(boolean isAdd, int start, String words)
	{
		textView2.setText(StringUtil
				.addOrDeleteWords(textView2.getText().toString(), isAdd, start, words));
	}
}
