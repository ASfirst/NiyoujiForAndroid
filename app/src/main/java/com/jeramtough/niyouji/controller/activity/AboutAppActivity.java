package com.jeramtough.niyouji.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.niyouji.R;

/**
 * @author 11718
 */
public class AboutAppActivity extends AppBaseActivity
{
	
	private TextView textViewCheckUpdate;
	private TimedCloseTextView timedCloseTextView;
	private AppCompatImageButton imageButtonBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_app);
		
		textViewCheckUpdate = findViewById(R.id.textView_check_update);
		timedCloseTextView = findViewById(R.id.timedCloseTextView);
		imageButtonBack = findViewById(R.id.imageButton_back);
		
		textViewCheckUpdate.setOnClickListener(this);
		imageButtonBack.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.textView_check_update:
				timedCloseTextView.setPrimaryMessage("检查更新中。。。");
				timedCloseTextView.visible();
				timedCloseTextView.postDelayed(() ->
				{
					timedCloseTextView.invisible();
					Toast.makeText(this, "已是最新版", Toast.LENGTH_SHORT).show();
				}, 3000);
				break;
			case R.id.imageButton_back:
				this.finish();
				break;
		}
	}
}
