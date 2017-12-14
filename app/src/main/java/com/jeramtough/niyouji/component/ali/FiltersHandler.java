package com.jeramtough.niyouji.component.ali;

import android.content.Context;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.niyouji.component.app.AppConfig;

import java.io.File;
import java.util.ArrayList;

/**
 * @author 11718
 *         on 2017  November 22 Wednesday 19:47.
 */
@JtComponent
public class FiltersHandler
{
	private Context context;
	
	private ArrayList<CameraFilter> cameraFilters;
	
	@IocAutowire
	public FiltersHandler(Context context)
	{
		this.context = context;
		this.cameraFilters = new ArrayList<>();
		
		this.addAFilter("wu");
		this.addAFilter("chihuang");
		this.addAFilter("fentao");
		this.addAFilter("hailan");
		this.addAFilter("hongrun");
		this.addAFilter("huibai");
		this.addAFilter("jingdian");
		this.addAFilter("maicha");
		this.addAFilter("nonglie");
		this.addAFilter("rourou");
		this.addAFilter("shanyao");
		this.addAFilter("xianguo");
		this.addAFilter("xueli");
		this.addAFilter("yangguang");
		this.addAFilter("youya");
		this.addAFilter("zhaoyang");
	}
	
	public ArrayList<CameraFilter> getCameraFilters()
	{
		return cameraFilters;
	}
	
	//*************************
	private void addAFilter(String pathName)
	{
		String filtersDirectory = AppConfig.getFiltersDirectory(context);
		String path = filtersDirectory + File.separator + pathName;
		
//		P.debug(path);
		
		CameraFilter cameraFilter = new CameraFilter(path);
		cameraFilters.add(cameraFilter);
	}
}
