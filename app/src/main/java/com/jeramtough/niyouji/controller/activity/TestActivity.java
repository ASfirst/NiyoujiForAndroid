package com.jeramtough.niyouji.controller.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.jeramtough.jtandroid.ui.FullScreenVideoView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.picandword.PicAndWordTheme;
import com.jeramtough.niyouji.controller.dialog.SelectPwThemeDialog;
import com.jeramtough.niyouji.controller.dialog.SelectTakephotoOrVideoDialog;

/**
 * @author 11718
 */
public class TestActivity extends AppBaseActivity
{
	private TextView textViewTjyjfm;
	private ImageView imageViewAddTravelnoteCover;
	private EditText editTravelnoteTitle;
	private Button btnStartPerforming;
	private FullScreenVideoView videoViewTravelnoteCover;
	private ImageView imageViewTravelnoteCover;
	
	private String coverPath;
	private String title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		textViewTjyjfm = findViewById(R.id.textView_tjyjfm);
		imageViewAddTravelnoteCover = findViewById(R.id.imageView_add_travelnote_cover);
		editTravelnoteTitle = findViewById(R.id.edit_travelnote_title);
		btnStartPerforming = findViewById(R.id.btn_start_performing);
		videoViewTravelnoteCover = findViewById(R.id.videoView_travelnote_cover);
		imageViewTravelnoteCover = findViewById(R.id.imageView_travelnote_cover);
		
		videoViewTravelnoteCover.setClickable(false);
		imageViewTravelnoteCover.setClickable(false);
		
		imageViewAddTravelnoteCover.setOnClickListener(this);
		imageViewTravelnoteCover.setOnClickListener(this);
		videoViewTravelnoteCover.setOnClickListener(this);
		btnStartPerforming.setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.videoView_travelnote_cover:
			case R.id.imageView_travelnote_cover:
			case R.id.imageView_add_travelnote_cover:
				SelectTakephotoOrVideoDialog dialog = new SelectTakephotoOrVideoDialog(this);
				dialog.show();
				break;
			case R.id.btn_start_performing:
				title = editTravelnoteTitle.getText().toString();
				if (title.length() == 0)
				{
					Toast.makeText(this, "请输入游记标题！", Toast.LENGTH_SHORT).show();
					break;
				}
				if (coverPath.length() == 0)
				{
					Toast.makeText(this, "没有拍摄游记封面哦！", Toast.LENGTH_SHORT).show();
					break;
				}
				
				Intent intent = new Intent(this, PerformingActivity.class);
				startActivity(intent);
				break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == resultCode &&
				resultCode == TakePhotoActivityApp.TAKE_PHOTO_RESULT_CODE)
		{
			String photoPath = data.getStringExtra(TakePhotoActivityApp.PHOTO_PATH_NAME);
			
			this.coverPath = photoPath;
			if (coverPath != null)
			{
				Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
				imageViewTravelnoteCover.setImageBitmap(bitmap);
				imageViewTravelnoteCover.setVisibility(View.VISIBLE);
				
				textViewTjyjfm.setVisibility(View.INVISIBLE);
				imageViewAddTravelnoteCover.setVisibility(View.INVISIBLE);
				imageViewTravelnoteCover.setClickable(true);
			}
		}
		else if (requestCode == resultCode && resultCode == VideoActivityApp.VIDEO_RESULT_CODE)
		{
			String videoPath = data.getStringExtra(VideoActivityApp.VIDEO_PATH_NAME);
			if (videoPath != null)
			{
				this.coverPath = videoPath;
				videoViewTravelnoteCover.setVisibility(View.VISIBLE);
				videoViewTravelnoteCover.setVideoPath(videoPath);
				videoViewTravelnoteCover.start();
				
				textViewTjyjfm.setVisibility(View.INVISIBLE);
				imageViewAddTravelnoteCover.setVisibility(View.INVISIBLE);
				videoViewTravelnoteCover.setClickable(true);
			}
		}
	}
}
