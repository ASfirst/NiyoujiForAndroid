package com.jeramtough.niyouji.component.ali;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aliyun.recorder.supply.AliyunIClipManager;
import com.aliyun.recorder.supply.AliyunIRecorder;
import com.aliyun.recorder.supply.RecordCallback;
import com.aliyun.struct.recorder.CameraType;
import com.aliyun.struct.recorder.FlashType;
import com.aliyun.struct.recorder.MediaInfo;
import com.jeramtough.jtandroid.jtlog2.P;
import com.jeramtough.niyouji.component.config.AppConfig;

import java.io.File;

/**
 * @author 11718
 *         on 2017  November 21 Tuesday 18:21.
 */

public class MyRecorder implements RecordCallback
{
	public static final int CAMERA_DIRECTION_BACK = 0;
	public static final int CAMERA_DIRECTION_FRONT = 1;
	
	public static final int MIN_RECORD_TIME = 3 * 1000;
	public static final int MAX_RECORD_TIME = 10 * 1000;
	
	private final static int VIDEO_WIDTH = 500;
	private final static int VIDEO_HEIGHT = 800;
	
	private AliyunIRecorder aliRecorder;
	private RecorderListener recorderListener;
	private TakephotoListener takephotoListener;
	
	private boolean isBeautyStatus = false;
	private boolean isBright = false;
	
	private int currentCameraDirection = CAMERA_DIRECTION_BACK;
	
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
		
		aliRecorder.getClipManager().setMaxDuration(MAX_RECORD_TIME);
		aliRecorder.getClipManager().setMinDuration(MIN_RECORD_TIME);
		
		aliRecorder.setRecordCallback(this);
	}
	
	public void switchCameraDirection()
	{
		aliRecorder.switchCamera();
		currentCameraDirection = currentCameraDirection == 1 ? 0 : 1;
	}
	
	public int getCameraDirection()
	{
		return currentCameraDirection;
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
	
	public void applyMusic(CameraMusic cameraMusic)
	{
		aliRecorder.setMusic(cameraMusic.getPath(), 0, MAX_RECORD_TIME);
	}
	
	public void startRecoding()
	{
		String videoPath =
				AppConfig.getVideosDirectory() + File.separator + System.currentTimeMillis() +
						"" + ".mp4";
		aliRecorder.setOutputPath(videoPath);
		aliRecorder.startRecording();
	}
	
	public int getCountOfRecorderPart()
	{
		return aliRecorder.getClipManager().getPartCount();
	}
	
	public void deleteLastPart()
	{
		aliRecorder.getClipManager().deletePart();
	}
	
	public void setRecorderListener(RecorderListener recorderListener)
	{
		this.recorderListener = recorderListener;
	}
	
	public void setTakephotoListener(TakephotoListener takephotoListener)
	{
		this.takephotoListener = takephotoListener;
	}
	
	public boolean isArriveMaxRecodingTime()
	{
		if (aliRecorder.getClipManager().getDuration() >=
				aliRecorder.getClipManager().getMaxDuration())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isArriveMinRecodingTime()
	{
		if (aliRecorder.getClipManager().getDuration() >=
				aliRecorder.getClipManager().getMinDuration())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void finishRecoding()
	{
		new AsyncTask()
		{
			@Override
			protected Object doInBackground(Object[] params)
			{
				aliRecorder.finishRecording();
				return null;
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
	
	public void clear()
	{
		aliRecorder.stopPreview();
		aliRecorder.getClipManager().deleteAllPart();
	}
	
	@Override
	public void onComplete(boolean isValidClip, long clipDuration)
	{
		if (recorderListener != null)
		{
			recorderListener.onAPartComplete(isValidClip, clipDuration);
		}
	}
	
	@Override
	public void onFinish(String outputPath)
	{
		if (recorderListener != null)
		{
			recorderListener.onRecodingFinished(outputPath);
		}
	}
	
	@Override
	public void onProgress(long duration)
	{
		if (recorderListener != null)
		{
			recorderListener.onProgress((int) duration);
		}
		
	}
	
	@Override
	public void onMaxDuration()
	{
	
	}
	
	@Override
	public void onError(int i)
	{
	
	}
	
	@Override
	public void onInitReady()
	{
	
	}
	
	@Override
	public void onDrawReady()
	{
	
	}
	
	@Override
	public void onPictureBack(Bitmap bitmap)
	{
		if (takephotoListener != null)
		{
			takephotoListener.onPictureBack(bitmap);
		}
	}
	
	@Override
	public void onPictureDataBack(byte[] bytes)
	{
		if (takephotoListener != null)
		{
			takephotoListener.onPictureDataBack(bytes);
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
	
	//{{{{{{{{{{{{{{{}}}}}}}}}}}}}}}}}
	public interface RecorderListener
	{
		void onProgress(int duration);
		
		void onAPartComplete(boolean isValidClip, long clipDuration);
		
		void onRecodingFinished(String outputPath);
	}
	
	public interface TakephotoListener
	{
		void onPictureBack(Bitmap bitmap);
		
		void onPictureDataBack(byte[] bytes);
	}
}
