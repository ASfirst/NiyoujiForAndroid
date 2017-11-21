package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.aliyun.common.httpfinal.QupaiHttpFinal;
import com.aliyun.recorder.AliyunRecorderCreator;
import com.aliyun.recorder.supply.AliyunIRecorder;
import com.aliyun.struct.recorder.CameraType;
import com.aliyun.struct.recorder.MediaInfo;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.ali.AliyunVideoGlSurfaceView;
import com.jeramtough.niyouji.component.ali.MyRecorder;
import com.jeramtough.niyouji.component.ali.RecordTimelineView;

/**
 * @author 11718
 */
public class CameraActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener
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
	
	private MyRecorder myRecorder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_camera);
		
		this.initViews();
		this.initAliRecorder();
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
		
		radioButtonSpeed3.setChecked(true);
		
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
	}
	
	protected void initAliRecorder()
	{
		QupaiHttpFinal.getInstance().initOkHttpFinal();
		System.loadLibrary("QuCore-ThirdParty");
		System.loadLibrary("QuCore");
		
		AliyunIRecorder aliRecorder = AliyunRecorderCreator.getRecorderInstance(this);
		aliRecorder.setDisplayView(glSurfaceViewCamera);
		
		//设置AliRecorder代理对象
		myRecorder = new MyRecorder(aliRecorder);
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
		switch (viewId)
		{
			case R.id.view_close:
				this.finish();
				break;
			case R.id.view_music:
				break;
			case R.id.view_beautiful:
				myRecorder.switchBeautyStatus();
				break;
			case R.id.view_turn:
				myRecorder.switchCameraDirection();
				break;
			case R.id.view_flash:
				myRecorder.switchLightMode();
				break;
			case R.id.view_done:
				break;
			case R.id.view_undo_record:
				break;
			case R.id.view_select_effects:
				break;
			case R.id.view_decals:
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
				myRecorder.getAliRecorder().setRate(0.5f);
				break;
			case R.id.radioButton_speed_4:
				myRecorder.getAliRecorder().setRate(0.75f);
				break;
			case R.id.radioButton_speed_5:
				myRecorder.getAliRecorder().setRate(0.5f);
				break;
		}
	}
}
