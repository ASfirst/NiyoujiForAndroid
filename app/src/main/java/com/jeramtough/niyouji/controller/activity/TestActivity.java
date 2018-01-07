package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.*;
import com.jeramtough.jtandroid.listener.OnTextChangedListner;
import com.jeramtough.jtemoji.JtEmojiCachesManager;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.ui.DanmakuLayout;

/**
 * @author 11718
 */
public class TestActivity extends AppBaseActivity
{
	private EditText editText1;
	private EditText editText2;
	private Button btn1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		editText1 = findViewById(R.id.editText1);
		editText2 = findViewById(R.id.editText2);
		btn1 = findViewById(R.id.btn1);
		
		btn1.setOnClickListener(this);
		
		editText1.addTextChangedListener(new OnTextChangedListner()
		{
			
			@Override
			public void onAddWords(String words, int start)
			{
				String text = editText2.getText().toString();
				String headText = text.substring(0, start);
				String extremeText = text.substring(start, text.length());
				text = headText + words + extremeText;
				editText2.setText(text);
			}
			
			@Override
			public void onAddWordsToLast(String words)
			{
				editText2.setText(editText2.getText() + words);
			}
			
			@Override
			public void onDeletedWords(String words, int start)
			{
				String text = editText2.getText().toString();
				String headText = text.substring(0, start);
				String extremeText = text.substring(start, text.length());
				extremeText=extremeText.substring(words.length(),extremeText.length());
				
				text=headText+extremeText;
				editText2.setText(text);
			}
			
			@Override
			public void onDeletedWordsFromLast(String words)
			{
				String text = editText2.getText().toString()
						.substring(0, editText2.getText().length() - words.length());
				editText2.setText(text);
			}
			
		});
	}
	
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.btn1:
				SpannableString spannableString = new SpannableString("[abc]def");
				ImageSpan span = new ImageSpan(this,R.drawable.ic_arrow_back);
				spannableString.setSpan(span, 0, 5,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				editText1.setText(spannableString);
				break;
		}
	}
	
}
