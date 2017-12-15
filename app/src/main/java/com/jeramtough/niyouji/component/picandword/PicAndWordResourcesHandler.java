package com.jeramtough.niyouji.component.picandword;

import android.content.Context;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtandroid.java.Directory;
import com.jeramtough.jtandroid.jtlog2.P;
import com.jeramtough.niyouji.component.app.AppConfig;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 11718 on 2017 December 10 Sunday 18:31.
 */
@JtComponent
public class PicAndWordResourcesHandler {
	private Context context;
	private ArrayList<PwResourcePosition> pwResourcePositions;
	
	@IocAutowire
	public PicAndWordResourcesHandler(Context context) {
		this.context = context;
		pwResourcePositions = new ArrayList<>();

		initResources();
	}

	private void initResources() {
		Directory directory = new Directory(
				AppConfig.getPwThemesDirectory(context));

		File[] files = directory.listFiles();

		for (File file : files) {
			PwResourcePosition pwThemeResourcePosition = new PwResourcePosition();
			pwThemeResourcePosition.setBackgroundPath(file.getAbsolutePath()
					+ File.separator + "background.jpg");
			pwThemeResourcePosition.setDefaultFunctionBtnBackgroundPath(file
					.getAbsolutePath() + File.separator + "btn_default.png");
			pwThemeResourcePosition.setPressedFunctionBtnBackgroundPath(file
					.getAbsolutePath() + File.separator + "btn_pressed.png");
			pwThemeResourcePosition.setDeleteBtnBackgroundPath(file
					.getAbsolutePath() + File.separator + "ic_delete.png");
			pwThemeResourcePosition.setFramePath(file.getAbsolutePath()
					+ File.separator + "frame.png");
			pwThemeResourcePosition.setSurfacePath(file.getAbsolutePath()
					+ File.separator + "surface.png");

			try {
				File configFile = new File(file.getAbsolutePath()
						+ File.separator + "config" + ".json");
				String jsonString = IOUtils.toString(new FileInputStream(
						configFile), "UTF-8");
				JSONObject jsonObject = new JSONObject(jsonString);

				pwThemeResourcePosition.setFontColor(PwResourceColor
						.getColor(jsonObject.getInt("fontColorNumber")));
				
				
				pwThemeResourcePosition.setDefaultFunctionColor(PwResourceColor
						.getColor(jsonObject.getInt("defaultFunctionColor")));
				pwThemeResourcePosition.setPressedFunctionColor(PwResourceColor
						.getColor(jsonObject.getInt("pressedFunctionColor")));

				pwThemeResourcePosition.setThemeName(jsonObject
						.getString("themeName"));
				pwThemeResourcePosition.setVip(jsonObject.getBoolean("isVip"));
			} catch (IOException | JSONException e) {
				e.printStackTrace();
			}

			pwResourcePositions.add(pwThemeResourcePosition);
		}

	}

	public ArrayList<PwResourcePosition> getPwResourcePositions() {
		return pwResourcePositions;
	}
}
