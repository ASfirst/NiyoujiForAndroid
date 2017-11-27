package com.jeramtough.niyouji.controller.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import com.jeramtough.jtandroid.jtlog2.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.controller.dialog.SelectTakephotoOrVideoDialog;

/**
 * @author 11718
 */
public class CreateTravelnoteActivity extends BaseActivity
{
	public final static String COVER_PATH_NAME="coverPath";
	public final static String TITLE_NAME="title";
	
	private ImageView imageViewAddTravelnoteCover;
	private ImageView imageViewTravelnoteCover;
	private EditText editTravelnoteTitle;
	private Button btnStartPerform;
	private VideoView videoViewTravelnoteCover;
	
	private String coverPath;
	private String title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_travelnote);
		
		imageViewAddTravelnoteCover = findViewById(R.id.imageView_add_travelnote_cover);
		imageViewTravelnoteCover = findViewById(R.id.imageView_travelnote_cover);
		editTravelnoteTitle = findViewById(R.id.edit_travelnote_title);
		btnStartPerform = findViewById(R.id.btn_start_perform);
		videoViewTravelnoteCover = findViewById(R.id.videoView_travelnote_cover);
		
		btnStartPerform.setVisibility(View.GONE);
		
		imageViewAddTravelnoteCover.setOnClickListener(this);
		btnStartPerform.setOnClickListener(this);
		videoViewTravelnoteCover.setOnCompletionListener(mp ->
		{
			videoViewTravelnoteCover.start();
		});
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
			case R.id.btn_start_perform:
				title = editTravelnoteTitle.getText().toString();
				if (title.length() ==0)
				{
					Toast.makeText(this, "请输入游记标题！", Toast.LENGTH_SHORT).show();
					break;
				}
				if (coverPath.length()==0)
				{
					Toast.makeText(this, "没有拍摄游记封面哦！", Toast.LENGTH_SHORT).show();
					break;
				}
				
				Intent intent = new Intent(this, PerformingActivity.class);
				intent.putExtra(COVER_PATH_NAME,coverPath);
				intent.putExtra(TITLE_NAME,title);
				startActivity(intent);
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
			
			this.coverPath=photoPath;
			
			Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
			imageViewTravelnoteCover.setImageBitmap(bitmap);
			
			editTravelnoteTitle.setBackgroundResource(R.mipmap.title_background);
			editTravelnoteTitle.setTextColor(Color.WHITE);
			editTravelnoteTitle.setGravity(Gravity.BOTTOM | Gravity.CENTER);
			
			btnStartPerform.setVisibility(View.VISIBLE);
		}
		else if (requestCode == resultCode && resultCode == VideoActivity.VIDEO_RESULT_CODE)
		{
			String videoPath = data.getStringExtra(VideoActivity.VIDEO_PATH_NAME);
			this.coverPath=videoPath;
			
			editTravelnoteTitle.setBackgroundResource(R.mipmap.title_background);
			editTravelnoteTitle.setTextColor(Color.WHITE);
			editTravelnoteTitle.setGravity(Gravity.BOTTOM | Gravity.CENTER);
			
			btnStartPerform.setVisibility(View.VISIBLE);
			videoViewTravelnoteCover.setVisibility(View.VISIBLE);
			
			videoViewTravelnoteCover.setVideoPath(videoPath);
			videoViewTravelnoteCover.start();
		}
	}
}
