package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
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
 */
public class VideoActivity extends BaseActivity
		implements RadioGroup.OnCheckedChangeListener, View.OnTouchListener,
		SelectFilterDialog.SelectFilterListener, SelectMusicDialog.SelectMusicListener,
		MyRecorder.RecorderListener
{
	private AliyunVideoGlSurfaceView glSurfaceViewCamera;
	private AppCompatImageView viewClose;
	private AppCompatImageView viewMusic;
	private AppCompatImageView viewBeautiful;
	private AppCompatImageView viewTurn;
	private AppCompatImageView viewFlash;
	private AppCompatImageView viewDone;
	private RadioGroup radioGroupSelectRecorderSpeed;
	private RadioButton radioButtonSpeed1;
	private RadioButton radioButtonSpeed2;
	private RadioButton radioButtonSpeed3;
	private RadioButton radioButtonSpeed4;
	private RadioButton radioButtonSpeed5;
	private RecordTimelineView recordTimeLineView;
	private AppCompatImageView viewUndoRecord;
	private AppCompatImageView viewSelectEffects;
	private AppCompatImageView viewRecorder;
	private AppCompatImageView viewDecals;
	private ProgressBar progressBarWaitRecodingFinished;
	
	private MyRecorder myRecorder;
	
	private FiltersHandler filtersHandler;
	private MusicsHandler musicsHandler;
	
	private SelectFilterDialog selectFilterDialog;
	private SelectMusicDialog selectMusicDialog;
	
	private OrientationDetector orientationDetector;
	private int rotation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_video);
		
		this.initResources();
		this.initViews();
		this.initAliRecorder();
	}
	
	protected void initResources()
	{
		filtersHandler = getMyInjectedObjects().getFiltersHandler();
		musicsHandler = getMyInjectedObjects().getMusicsHandler();
		
		selectFilterDialog = new SelectFilterDialog(this, filtersHandler);
		selectFilterDialog.setSelectFilterListener(this);
	}
	
	protected void initViews()
	{
		//去掉信息栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		glSurfaceViewCamera = findViewById(R.id.glSurfaceView_camera);
		radioGroupSelectRecorderSpeed = findViewById(R.id.radioGroup_select_recorder_speed);
		radioButtonSpeed1 = findViewById(R.id.radioButton_speed_1);
		radioButtonSpeed2 = findViewById(R.id.radioButton_speed_2);
		radioButtonSpeed3 = findViewById(R.id.radioButton_speed_3);
		radioButtonSpeed4 = findViewById(R.id.radioButton_speed_4);
		radioButtonSpeed5 = findViewById(R.id.radioButton_speed_5);
		recordTimeLineView = findViewById(R.id.recordTimeLineView);
		viewClose = findViewById(R.id.view_close);
		viewMusic = findViewById(R.id.view_music);
		viewBeautiful = findViewById(R.id.view_beautiful);
		viewTurn = findViewById(R.id.view_turn);
		viewFlash = findViewById(R.id.view_flash);
		viewDone = findViewById(R.id.view_done);
		viewUndoRecord = findViewById(R.id.view_undo_record);
		viewSelectEffects = findViewById(R.id.view_select_effects);
		viewRecorder = findViewById(R.id.view_recorder);
		viewDecals = findViewById(R.id.view_decals);
		progressBarWaitRecodingFinished =
				findViewById(R.id.progressBar_wait_recoding_finished);
		
		radioButtonSpeed3.setChecked(true);
		viewUndoRecord.setVisibility(View.INVISIBLE);
		
		recordTimeLineView.setMaxDuration(MyRecorder.MAX_RECORD_TIME);
		recordTimeLineView.setMinDuration(MyRecorder.MIN_RECORD_TIME);
		recordTimeLineView
				.setColor(R.color.colorPrimary, R.color.black, R.color.red, R.color.white);
		
		viewClose.setOnClickListener(this);
		viewMusic.setOnClickListener(this);
		viewBeautiful.setOnClickListener(this);
		viewTurn.setOnClickListener(this);
		viewFlash.setOnClickListener(this);
		viewDone.setOnClickListener(this);
		viewUndoRecord.setOnClickListener(this);
		viewSelectEffects.setOnClickListener(this);
		viewDecals.setOnClickListener(this);
		
		radioGroupSelectRecorderSpeed.setOnCheckedChangeListener(this);
		
		viewRecorder.setOnTouchListener(this);
	}
	
	protected void initAliRecorder()
	{
		AliyunIRecorder aliRecorder = AliyunRecorderCreator.getRecorderInstance(this);
		aliRecorder.setDisplayView(glSurfaceViewCamera);
		
		//设置AliRecorder代理对象
		myRecorder = new MyRecorder(aliRecorder);
		
		myRecorder.setRecorderListener(this);
		
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
	public boolean onTouch(View v, MotionEvent event)
	{
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				this.uiOfPressRecorderButton();
				myRecorder.startRecoding();
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				this.stopRecoding();
				break;
		}
		return true;
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.view_close:
				this.finish();
				break;
			case R.id.view_music:
				selectMusicDialog =
						new SelectMusicDialog(this, musicsHandler.getCameraMusics());
				selectMusicDialog.setSelectMusicListener(this);
				selectMusicDialog.show();
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
			case R.id.view_done:
				if (!myRecorder.isArriveMinRecodingTime())
				{
					Toast.makeText(this, "录制时间太短-不足3秒！!", Toast.LENGTH_SHORT).show();
				}
				else
				{
					this.uiOfWaitingRecodingFinished();
					myRecorder.finishRecoding();
				}
				break;
			case R.id.view_undo_record:
				viewRecorder.setVisibility(View.VISIBLE);
				
				recordTimeLineView.deleteLast();
				
				myRecorder.deleteLastPart();
				
				if (myRecorder.getCountOfRecorderPart() == 0)
				{
					viewUndoRecord.setVisibility(View.INVISIBLE);
					viewMusic.setVisibility(View.VISIBLE);
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
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		switch (checkedId)
		{
			case R.id.radioButton_speed_1:
				myRecorder.getAliRecorder().setRate(2f);
				break;
			case R.id.radioButton_speed_2:
				myRecorder.getAliRecorder().setRate(1.5f);
				break;
			case R.id.radioButton_speed_3:
				myRecorder.getAliRecorder().setRate(1f);
				break;
			case R.id.radioButton_speed_4:
				myRecorder.getAliRecorder().setRate(0.75f);
				break;
			case R.id.radioButton_speed_5:
				myRecorder.getAliRecorder().setRate(0.5f);
				break;
		}
	}
	
	@Override
	public void selectedFilter(CameraFilter cameraFilter)
	{
		myRecorder.getAliRecorder().applyFilter(cameraFilter);
	}
	
	@Override
	public void selectMusic(CameraMusic cameraMusic)
	{
		myRecorder.applyMusic(cameraMusic);
	}
	
	@Override
	public void onProgress(int duration)
	{
		if (!myRecorder.isArriveMaxRecodingTime())
		{
			recordTimeLineView.setDuration(duration);
		}
	}
	
	@Override
	public void onAPartComplete(boolean isValidClip, long clipDuration)
	{
		
		if (isValidClip)
		{
			recordTimeLineView.setDuration((int) clipDuration);
			recordTimeLineView.clipComplete();
			
			if (viewUndoRecord.getVisibility()==View.INVISIBLE)
			{
				viewUndoRecord.setVisibility(View.VISIBLE);
			}
			if (viewMusic.getVisibility()==View.VISIBLE)
			{
				viewMusic.setVisibility(View.INVISIBLE);
			}
			
			if (myRecorder.isArriveMaxRecodingTime())
			{
				Toast.makeText(this, "到达最大，停止录制!", Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			recordTimeLineView.setDuration(0);
		}
	}
	
	@Override
	public void onRecodingFinished(String outputPath)
	{
		myRecorder.clear();
		this.finish();
	}
	
	//*************************************************************
	private void uiOfPressRecorderButton()
	{
		viewRecorder.setAlpha(0.7f);
		radioGroupSelectRecorderSpeed.setVisibility(View.INVISIBLE);
		viewClose.setVisibility(View.INVISIBLE);
		viewMusic.setVisibility(View.INVISIBLE);
		viewBeautiful.setVisibility(View.INVISIBLE);
		viewTurn.setVisibility(View.INVISIBLE);
		viewFlash.setVisibility(View.INVISIBLE);
		viewDone.setVisibility(View.INVISIBLE);
		viewUndoRecord.setVisibility(View.INVISIBLE);
		viewSelectEffects.setVisibility(View.INVISIBLE);
		viewDecals.setVisibility(View.INVISIBLE);
		
	}
	
	private void uiOfRelaxRecorderButton()
	{
		viewRecorder.setAlpha(1f);
		
		radioGroupSelectRecorderSpeed.setVisibility(View.VISIBLE);
		viewClose.setVisibility(View.VISIBLE);
		viewMusic.setVisibility(View.VISIBLE);
		viewBeautiful.setVisibility(View.VISIBLE);
		viewTurn.setVisibility(View.VISIBLE);
		viewFlash.setVisibility(View.VISIBLE);
		viewUndoRecord.setVisibility(View.VISIBLE);
		viewSelectEffects.setVisibility(View.VISIBLE);
		viewDecals.setVisibility(View.VISIBLE);
		viewDone.setVisibility(View.VISIBLE);
	}
	
	private void uiOfWaitingRecodingFinished()
	{
		radioGroupSelectRecorderSpeed.setVisibility(View.INVISIBLE);
		viewClose.setVisibility(View.INVISIBLE);
		viewMusic.setVisibility(View.INVISIBLE);
		viewBeautiful.setVisibility(View.INVISIBLE);
		viewTurn.setVisibility(View.INVISIBLE);
		viewFlash.setVisibility(View.INVISIBLE);
		viewDone.setVisibility(View.INVISIBLE);
		viewUndoRecord.setVisibility(View.INVISIBLE);
		viewSelectEffects.setVisibility(View.INVISIBLE);
		viewDecals.setVisibility(View.INVISIBLE);
		viewRecorder.setVisibility(View.INVISIBLE);
		recordTimeLineView.setVisibility(View.INVISIBLE);
		
		progressBarWaitRecodingFinished.setVisibility(View.VISIBLE);
	}
	
	private void stopRecoding()
	{
		myRecorder.getAliRecorder().stopRecording();
		uiOfRelaxRecorderButton();
	}
	
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
