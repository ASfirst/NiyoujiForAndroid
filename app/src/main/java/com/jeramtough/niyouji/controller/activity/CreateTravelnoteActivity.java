package com.jeramtough.niyouji.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.jeramtough.jtandroid.jtlog2.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.controller.dialog.SelectTakephotoOrVideoDialog;

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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == resultCode &&
				resultCode == TakePhotoActivity.TAKE_PHOTO_RESULT_CODE)
		{
			String photoPath = data.getStringExtra(TakePhotoActivity.PHOTO_PATH_NAME);
			P.debug(photoPath);
		}
		else if (requestCode == resultCode && resultCode == VideoActivity.VIDEO_RESULT_CODE)
		{
			String videoPath = data.getStringExtra(VideoActivity.VIDEO_PATH_NAME);
			P.debug(videoPath);
		}
	}
}
