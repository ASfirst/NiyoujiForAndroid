package com.jeramtough.niyouji.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.widget.ProgressBar;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.ali.AliyunVideoGlSurfaceView;

/**
 * @author 11718
 */
public class TakePhotoActivity extends AppCompatActivity
{
	private AliyunVideoGlSurfaceView glSurfaceViewCamera;
	private ProgressBar progressBarWaitRecodingFinished;
	private AppCompatImageView viewClose;
	private AppCompatImageView viewBeautiful;
	private AppCompatImageView viewTurn;
	private AppCompatImageView viewFlash;
	private AppCompatImageView viewDone;
	private AppCompatImageView viewSelectEffects;
	private AppCompatImageView viewTakephoto;
	private AppCompatImageView viewDecals;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_photo);
		
		glSurfaceViewCamera = findViewById(R.id.glSurfaceView_camera);
		progressBarWaitRecodingFinished = findViewById(R.id.progressBar_wait_recoding_finished);
		viewClose = findViewById(R.id.view_close);
		viewBeautiful = findViewById(R.id.view_beautiful);
		viewTurn = findViewById(R.id.view_turn);
		viewFlash = findViewById(R.id.view_flash);
		viewDone = findViewById(R.id.view_done);
		viewSelectEffects = findViewById(R.id.view_select_effects);
		viewTakephoto = findViewById(R.id.view_takephoto);
		viewDecals = findViewById(R.id.view_decals);
	}
}
