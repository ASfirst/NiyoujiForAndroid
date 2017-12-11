package com.jeramtough.niyouji.component.picandword;

import android.content.Context;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtandroid.java.Directory;
import com.jeramtough.niyouji.component.app.AppConfig;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 11718
 *         on 2017  December 10 Sunday 18:31.
 */
@JtComponent
public class PicAndWordResourcesHandler
{
	private Context context;
	private ArrayList<PicAndWordResource> picAndWordResources;
	
	public PicAndWordResourcesHandler(Context context)
	{
		this.context = context;
		picAndWordResources = new ArrayList<>();
		
		initResources();
	}
	
	private void initResources()
	{
		Directory directory = new Directory(AppConfig.getPwThemesDirectory(context));
		
		File[] files = directory.listFiles();
		
		for (File file : files)
		{
			PicAndWordResource picAndWordResource = new PicAndWordResource();
			picAndWordResource.setBackgroundPath(
					file.getAbsolutePath() + File.separator + "background.jpg");
			picAndWordResource.setDefaultFunctionBtnBackgroundPath(
					file.getAbsolutePath() + File.separator + "btn_default.png");
			picAndWordResource.setPressedFunctionBtnBackgroundPath(
					file.getAbsolutePath() + File.separator + "btn_pressed.png");
			picAndWordResource.setDeleteBtnBackgroundPath(
					file.getAbsolutePath() + File.separator + "ic_delete.png");
			picAndWordResource
					.setFramePath(file.getAbsolutePath() + File.separator + "frame.png");
			picAndWordResource
					.setSurfacePath(file.getAbsolutePath() + File.separator + "surface.png");
			
			try
			{
				File configFile =
						new File(file.getAbsolutePath() + File.separator + "config" + ".json");
				String jsonString = IOUtils.toString(new FileInputStream(configFile), "UTF-8");
				JSONObject jsonObject = new JSONObject(jsonString);
				
				picAndWordResource.setFontColor(
						PwResourceColor.getColor(jsonObject.getInt("fontColorNumber")));
				picAndWordResource.setThemeName(jsonObject.getString("themeName"));
				picAndWordResource.setVip(jsonObject.getBoolean("isVip"));
			}
			catch (IOException | JSONException e)
			{
				e.printStackTrace();
			}
			
			picAndWordResources.add(picAndWordResource);
		}
	}
	
	public ArrayList<PicAndWordResource> getPicAndWordResources()
	{
		return picAndWordResources;
	}
}
