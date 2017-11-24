package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.aliyun.recorder.AliyunRecorderCreator;
import com.aliyun.recorder.supply.AliyunIRecorder;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.ali.*;
import com.jeramtough.niyouji.controller.dialog.SelectDecalDialog;
import com.jeramtough.niyouji.controller.dialog.SelectFilterDialog;
import com.jeramtough.niyouji.controller.dialog.SelectMusicDialog;

/**
 * @author 11718
 *         on 2017  November 24 Friday 22:04.
 */

public abstract class AliCameraActivity extends BaseActivity
		implements SelectFilterDialog.SelectFilterListener
{
	
	protected AliyunVideoGlSurfaceView glSurfaceViewCamera;
	protected AppCompatImageView viewClose;
	protected AppCompatImageView viewBeautiful;
	protected AppCompatImageView viewTurn;
	protected AppCompatImageView viewFlash;
	protected AppCompatImageView viewSelectEffects;
	protected AppCompatImageView viewDecals;
	protected ProgressBar progressBarWaitRecodingFinished;
	
	protected MyRecorder myRecorder;
	
	protected FiltersHandler filtersHandler;
	
	protected SelectFilterDialog selectFilterDialog;
	protected SelectMusicDialog selectMusicDialog;
	
	protected OrientationDetector orientationDetector;
	protected int rotation;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(loadLayout());
		
		this.initResources();
		this.initViews();
		this.initAliRecorder();
	}
	
	
	public abstract int loadLayout();
	
	protected void initResources()
	{
		filtersHandler = getMyInjectedObjects().getFiltersHandler();
		
		selectFilterDialog = new SelectFilterDialog(this, filtersHandler);
		selectFilterDialog.setSelectFilterListener(this);
	}
	
	protected void initViews()
	{
		//去掉信息栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		glSurfaceViewCamera = findViewById(R.id.glSurfaceView_camera);
		viewClose = findViewById(R.id.view_close);
		viewBeautiful = findViewById(R.id.view_beautiful);
		viewTurn = findViewById(R.id.view_turn);
		viewFlash = findViewById(R.id.view_flash);
		viewSelectEffects = findViewById(R.id.view_select_effects);
		viewDecals = findViewById(R.id.view_decals);
		progressBarWaitRecodingFinished =
				findViewById(R.id.progressBar_wait_recoding_finished);
		
		viewClose.setOnClickListener(this);
		viewBeautiful.setOnClickListener(this);
		viewTurn.setOnClickListener(this);
		viewFlash.setOnClickListener(this);
		viewSelectEffects.setOnClickListener(this);
		viewDecals.setOnClickListener(this);
	}
	
	protected void initAliRecorder()
	{
		AliyunIRecorder aliRecorder = AliyunRecorderCreator.getRecorderInstance(this);
		aliRecorder.setDisplayView(glSurfaceViewCamera);
		
		//设置AliRecorder代理对象
		myRecorder = new MyRecorder(aliRecorder);
		
		//设置摄像机方向
		orientationDetector = new OrientationDetector(getApplicationContext());
		orientationDetector.setOrientationChangedListener(() ->
		{
			rotation = getPictureRotation();
			myRecorder.getAliRecorder().setRotation(rotation);
		});
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		myRecorder.getAliRecorder().startPreview();
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		AliyunRecorderCreator.destroyRecorderInstance();
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		super.onClick(view,viewId);
		switch (viewId)
		{
			case R.id.view_close:
				this.finish();
				break;
			case R.id.view_beautiful:
				myRecorder.switchBeautyStatus();
				if (myRecorder.isBeautyStatus())
				{
					viewBeautiful.setImageResource(R.drawable.ic_selected_beautiful);
				}
				else
				{
					viewBeautiful.setImageResource(R.drawable.ic_beautiful);
				}
				break;
			case R.id.view_turn:
				myRecorder.switchCameraDirection();
				break;
			case R.id.view_flash:
				if (myRecorder.getCameraDirection()==MyRecorder.CAMERA_DIRECTION_BACK)
				{
					myRecorder.switchLightMode();
					if (myRecorder.isBright())
					{
						viewFlash.setImageResource(R.drawable.ic_selected_flash);
					}
					else
					{
						viewFlash.setImageResource(R.drawable.ic_flash);
					}
				}
				else
				{
					Toast.makeText(this,"前置摄像头无法开灯！",Toast.LENGTH_SHORT).show();
				}
				
				break;
			case R.id.view_select_effects:
				selectFilterDialog.show();
				break;
			case R.id.view_decals:
				SelectDecalDialog selectDecalDialog = new SelectDecalDialog(this);
				selectDecalDialog.show();
				break;
			default:
		}
	}
	
	@Override
	public void selectedFilter(CameraFilter cameraFilter)
	{
		myRecorder.getAliRecorder().applyFilter(cameraFilter);
	}
	
	
	//***************************************
	private int getPictureRotation()
	{
		int orientation = orientationDetector.getOrientation();
		int rotation = 90;
		if ((orientation >= 45) && (orientation < 135))
		{
			rotation = 180;
		}
		if ((orientation >= 135) && (orientation < 225))
		{
			rotation = 270;
		}
		if ((orientation >= 225) && (orientation < 315))
		{
			rotation = 0;
		}
		if (rotation != 0)
		{
			rotation = 360 - rotation;
		}
		return rotation;
	}
}
