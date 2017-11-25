package com.jeramtough.niyouji.controller.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.jeramtough.jtandroid.jtlog2.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.ali.AliyunVideoGlSurfaceView;
import com.jeramtough.niyouji.component.ali.MyRecorder;

/**
 * @author 11718
 */
public class TakePhotoActivity extends AliCameraActivity
		implements MyRecorder.TakephotoListener
{
	private AppCompatImageView viewDone;
	private AppCompatImageView viewTakephoto;
	private AppCompatImageView viewDeletePhoto;
	private ImageView imageViewPhoto;
	
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
				imageViewPhoto.setImageResource(R.color.black);
				imageViewPhoto.setVisibility(View.INVISIBLE );
				break;
		}
	}
	
	
	@Override
	public void onPictureBack(Bitmap bitmap)
	{
		runOnUiThread(() ->
		{
			imageViewPhoto.setImageBitmap(bitmap);
			progressBarWaitRecodingFinished.setVisibility(View.INVISIBLE);
			viewDeletePhoto.setClickable(true);
		});
		
	}
	
	@Override
	public void onPictureDataBack(byte[] bytes)
	{
	
	}

}
