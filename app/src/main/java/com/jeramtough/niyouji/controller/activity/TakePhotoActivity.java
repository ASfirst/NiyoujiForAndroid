package com.jeramtough.niyouji.controller.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.jeramtough.jtandroid.util.BitmapUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.ali.camera.MyRecorder;
import com.jeramtough.niyouji.component.app.AppConfig;

import java.io.File;

/**
 * @author 11718
 */
public class TakePhotoActivity extends AliCameraActivity
		implements MyRecorder.TakephotoListener
{
	public final static int TAKE_PHOTO_RESULT_CODE = 0x8879;
	public final static String PHOTO_PATH_NAME = "photoPath";
	
	private AppCompatImageView viewDone;
	private AppCompatImageView viewTakephoto;
	private AppCompatImageView viewDeletePhoto;
	private ImageView imageViewPhoto;
	
	private Bitmap bitmapOfPhoto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public int loadLayout()
	{
		return R.layout.activity_take_photo;
	}
	
	
	
	@Override
	protected void initViews()
	{
		super.initViews();
		viewDone = findViewById(R.id.view_done);
		viewTakephoto = findViewById(R.id.view_takephoto);
		viewDeletePhoto = findViewById(R.id.view_delete_photo);
		imageViewPhoto = findViewById(R.id.imageView_photo);
		
		viewDone.setOnClickListener(this);
		viewTakephoto.setOnClickListener(this);
		viewDeletePhoto.setOnClickListener(this);
	}
	
	@Override
	protected void initResources()
	{
		super.initResources();
		Intent intent = this.getIntent();
		TakePhotoActivity.this.setResult(TAKE_PHOTO_RESULT_CODE, intent);
	}
	
	@Override
	protected void initAliRecorder()
	{
		super.initAliRecorder();
		myRecorder.setTakephotoListener(this);
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		super.onClick(view, viewId);
		
		switch (viewId)
		{
			case R.id.view_done:
				if (bitmapOfPhoto == null)
				{
					Toast.makeText(this, "您没有拍照哦~", Toast.LENGTH_SHORT).show();
				}
				else
				{
					String photoName = "JPEG_" + System.currentTimeMillis() + ".jpg";
					File file = new File(
							AppConfig.getImagesDirectory() + File.separator + photoName);
					BitmapUtil.saveBitmapToLocal(bitmapOfPhoto, file, 40);
					
					Intent intent = this.getIntent();
					intent.putExtra(PHOTO_PATH_NAME, file.getAbsolutePath());
					TakePhotoActivity.this.setResult(TAKE_PHOTO_RESULT_CODE, intent);
					
					this.finish();
				}
				
				break;
			case R.id.view_takephoto:
				myRecorder.getAliRecorder().takePhoto(true);
				
				viewTakephoto.setVisibility(View.INVISIBLE);
				viewDeletePhoto.setVisibility(View.VISIBLE);
				imageViewPhoto.setVisibility(View.VISIBLE);
				
				viewDeletePhoto.setClickable(false);
				progressBarWaitRecodingFinished.setVisibility(View.VISIBLE);
				break;
			case R.id.view_delete_photo:
				viewTakephoto.setVisibility(View.VISIBLE);
				viewDeletePhoto.setVisibility(View.INVISIBLE);
				imageViewPhoto.setBackgroundColor(Color.BLACK);
				imageViewPhoto.setVisibility(View.INVISIBLE);
				
				bitmapOfPhoto = null;
				break;
		}
	}
	
	
	@Override
	public void onPictureBack(Bitmap bitmap)
	{
		bitmapOfPhoto = bitmap;
		
		//更新UI
		runOnUiThread(() ->
		{
			imageViewPhoto.setImageBitmap(bitmap);
			
			progressBarWaitRecodingFinished.setVisibility(View.INVISIBLE);
			viewDeletePhoto.setClickable(true);
		});
		
	}
	
	//**********************************
	
}
