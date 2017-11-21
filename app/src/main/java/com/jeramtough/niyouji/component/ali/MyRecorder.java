package com.jeramtough.niyouji.component.ali;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aliyun.recorder.supply.AliyunIRecorder;
import com.aliyun.struct.recorder.CameraType;
import com.aliyun.struct.recorder.FlashType;
import com.aliyun.struct.recorder.MediaInfo;
import com.jeramtough.jtandroid.jtlog2.P;

/**
 * @author 11718
 *         on 2017  November 21 Tuesday 18:21.
 */

public class MyRecorder
{
	private final static int VIDEO_WIDTH = 500;
	private final static int VIDEO_HEIGHT = 800;
	
	private AliyunIRecorder aliRecorder;
	
	private boolean isBeautyStatus = false;
	private boolean isBright = false;
	
	public MyRecorder(AliyunIRecorder aliRecorder)
	{
		this.aliRecorder = aliRecorder;
		
		//设置视频宽高
		MediaInfo mediaInfo = new MediaInfo();
		mediaInfo.setVideoWidth(VIDEO_WIDTH);
		mediaInfo.setVideoHeight(VIDEO_HEIGHT);
		aliRecorder.setMediaInfo(mediaInfo);
		
		//设置摄像机的默认值
		aliRecorder.setCamera(CameraType.BACK);
		aliRecorder.setBeautyLevel(90);
		aliRecorder.setBeautyStatus(isBeautyStatus);
	}
	
	public void switchCameraDirection()
	{
		aliRecorder.switchCamera();
	}
	
	public void switchBeautyStatus()
	{
		aliRecorder.setBeautyStatus(!isBeautyStatus);
		isBeautyStatus = !isBeautyStatus;
	}
	
	public void switchLightMode()
	{
		if (!isBright)
		{
			aliRecorder.setLight(FlashType.TORCH);
			isBright = true;
		}
		else
		{
			aliRecorder.setLight(FlashType.OFF);
			isBright = false;
		}
	}
	//GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
	
	public boolean isBeautyStatus()
	{
		return isBeautyStatus;
	}
	
	public AliyunIRecorder getAliRecorder()
	{
		return aliRecorder;
	}
	
	public boolean isBright()
	{
		return isBright;
	}
}
