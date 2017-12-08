package com.jeramtough.niyouji.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.ali.CameraMusic;
import com.jeramtough.niyouji.component.ali.MusicsHandler;
import com.jeramtough.niyouji.component.ali.MyRecorder;
import com.jeramtough.niyouji.component.ali.RecordTimelineView;
import com.jeramtough.niyouji.controller.dialog.SelectMusicDialog;

/**
 * @author 11718
 */
public class VideoActivityApp extends AliCameraActivityApp
		implements RadioGroup.OnCheckedChangeListener, View.OnTouchListener,
		SelectMusicDialog.SelectMusicListener, MyRecorder.RecorderListener
{
	public static final int VIDEO_RESULT_CODE=0X333;
	public static final String VIDEO_PATH_NAME="videoPath";
	
	private AppCompatImageView viewMusic;
	private AppCompatImageView viewDone;
	private RadioGroup radioGroupSelectRecorderSpeed;
	private RadioButton radioButtonSpeed1;
	private RadioButton radioButtonSpeed2;
	private RadioButton radioButtonSpeed3;
	private RadioButton radioButtonSpeed4;
	private RadioButton radioButtonSpeed5;
	private RecordTimelineView recordTimeLineView;
	private AppCompatImageView viewUndoRecord;
	private AppCompatImageView viewRecorder;
	
	@InjectComponent
	private MusicsHandler musicsHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public int loadLayout()
	{
		return R.layout.activity_video;
	}
	
	@Override
	protected void initResources()
	{
		super.initResources();
		
		Intent intent = this.getIntent();
		VideoActivityApp.this.setResult(VIDEO_RESULT_CODE, intent);
	}
	
	@Override
	protected void initViews()
	{
		super.initViews();
		
		radioGroupSelectRecorderSpeed = findViewById(R.id.radioGroup_select_recorder_speed);
		radioButtonSpeed1 = findViewById(R.id.radioButton_speed_1);
		radioButtonSpeed2 = findViewById(R.id.radioButton_speed_2);
		radioButtonSpeed3 = findViewById(R.id.radioButton_speed_3);
		radioButtonSpeed4 = findViewById(R.id.radioButton_speed_4);
		radioButtonSpeed5 = findViewById(R.id.radioButton_speed_5);
		recordTimeLineView = findViewById(R.id.recordTimeLineView);
		viewMusic = findViewById(R.id.view_music);
		viewDone = findViewById(R.id.view_done);
		viewUndoRecord = findViewById(R.id.view_undo_record);
		viewRecorder = findViewById(R.id.view_recorder);
		
		radioButtonSpeed3.setChecked(true);
		viewUndoRecord.setVisibility(View.INVISIBLE);
		
		recordTimeLineView.setMaxDuration(MyRecorder.MAX_RECORD_TIME);
		recordTimeLineView.setMinDuration(MyRecorder.MIN_RECORD_TIME);
		recordTimeLineView
				.setColor(R.color.colorPrimary, R.color.black, R.color.red, R.color.white);
		
		viewMusic.setOnClickListener(this);
		viewDone.setOnClickListener(this);
		viewUndoRecord.setOnClickListener(this);
		
		radioGroupSelectRecorderSpeed.setOnCheckedChangeListener(this);
		
		viewRecorder.setOnTouchListener(this);
	}
	
	@Override
	protected void initAliRecorder()
	{
		super.initAliRecorder();
		myRecorder.setRecorderListener(this);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		if (v == viewRecorder)
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
		}
		return super.onTouch(v, event);
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		super.onClick(view, viewId);
		switch (viewId)
		{
			case R.id.view_music:
				SelectMusicDialog selectMusicDialog =
						new SelectMusicDialog(this, musicsHandler.getCameraMusics());
				selectMusicDialog.setSelectMusicListener(this);
				selectMusicDialog.show();
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
			
			if (viewUndoRecord.getVisibility() == View.INVISIBLE)
			{
				viewUndoRecord.setVisibility(View.VISIBLE);
			}
			if (viewMusic.getVisibility() == View.VISIBLE)
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
		myRecorder.clearVideoParts();
		Intent intent=getIntent();
		intent.putExtra(VIDEO_PATH_NAME, outputPath);
		setResult(VIDEO_RESULT_CODE, intent);
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
	
}
