package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.dialog.SelectTakephotoOrVideoDialog;

/**
 * @author 11718
 */
public class CreateTravelnoteActivity extends BaseActivity
{
	private ImageView imageViewAddTravelnoteCover;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_travelnote);
		
		imageViewAddTravelnoteCover = findViewById(R.id.imageView_add_travelnote_cover);
		imageViewAddTravelnoteCover.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.imageView_add_travelnote_cover:
				SelectTakephotoOrVideoDialog dialog = new SelectTakephotoOrVideoDialog(this);
				dialog.show();
				break;
		}
	}
}