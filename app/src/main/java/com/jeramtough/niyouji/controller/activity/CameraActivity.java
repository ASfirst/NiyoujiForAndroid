package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
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
import com.jeramtough.niyouji.component.ali.RecordTimelineView;

/**
 * @author 11718
 */
public class CameraActivity extends BaseActivity
{
	private final int VIDEO_WIDTH=500;
	private final int VIDEO_HEIGHT=800;
	
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
	
	private AliyunIRecorder aliRecorder;
	
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
		viewClose = findViewById(R.id.view_close);
		viewMusic = findViewById(R.id.view_music);
		viewBeautiful = findViewById(R.id.view_beautiful);
		viewTurn = findViewById(R.id.view_turn);
		viewFlash = findViewById(R.id.view_flash);
		viewDone = findViewById(R.id.view_done);
		radioGroupSelectRecorderSpeed = findViewById(R.id.radioGroup_select_recorder_speed);
		radioButtonSpeed1 = findViewById(R.id.radioButton_speed_1);
		radioButtonSpeed2 = findViewById(R.id.radioButton_speed_2);
		radioButtonSpeed3 = findViewById(R.id.radioButton_speed_3);
		radioButtonSpeed4 = findViewById(R.id.radioButton_speed_4);
		radioButtonSpeed5 = findViewById(R.id.radioButton_speed_5);
		recordTimeLineView = findViewById(R.id.recordTimeLineView);
		viewUndoRecord = findViewById(R.id.view_undo_record);
		viewSelectEffects = findViewById(R.id.view_select_effects);
		viewRecorder = findViewById(R.id.view_recorder);
		viewDecals = findViewById(R.id.view_decals);
		
		radioButtonSpeed3.setChecked(true);
	}
	
	protected void initAliRecorder()
	{
		QupaiHttpFinal.getInstance().initOkHttpFinal();
		System.loadLibrary("QuCore-ThirdParty");
		System.loadLibrary("QuCore");
		
		aliRecorder = AliyunRecorderCreator.getRecorderInstance(this);
		aliRecorder.setDisplayView(glSurfaceViewCamera);
		
		//设置视频宽高
		MediaInfo mediaInfo = new MediaInfo();
		mediaInfo.setVideoWidth(VIDEO_WIDTH);
		mediaInfo.setVideoHeight(VIDEO_HEIGHT);
		aliRecorder.setMediaInfo(mediaInfo);
		
		//设置默认用后置摄像头
		aliRecorder.setCamera(CameraType.BACK);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		aliRecorder.startPreview();
	}
}
