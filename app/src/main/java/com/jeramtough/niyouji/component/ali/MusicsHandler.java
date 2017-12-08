package com.jeramtough.niyouji.component.ali;

import android.content.Context;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtandroid.java.Directory;
import com.jeramtough.niyouji.component.app.AppConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 11718
 *         on 2017  November 23 Thursday 16:42.
 */
@JtComponent
public class MusicsHandler
{
	private Context context;
	private List<CameraMusic> cameraMusics;
	
	public MusicsHandler(Context context)
	{
		this.context = context;
		cameraMusics = new ArrayList<>();
		
		initResources();
	}
	
	private void initResources()
	{
		Directory musicsDirectory = new Directory(AppConfig.getMusicsDirectory(context));
		
		File[] files = musicsDirectory.listFiles();
		
		for (File file:files)
		{
			String name=file.getName();
			name=name.split("\\.")[0];
			CameraMusic cameraMusic=new CameraMusic(name,file.getAbsolutePath());
			cameraMusics.add(cameraMusic);
		}
	}
	
	public List<CameraMusic> getCameraMusics()
	{
		return cameraMusics;
	}
}
