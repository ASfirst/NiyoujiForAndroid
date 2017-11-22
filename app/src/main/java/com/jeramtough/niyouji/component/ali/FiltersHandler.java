package com.jeramtough.niyouji.component.ali;

import com.jeramtough.niyouji.component.config.AppConfig;

import java.io.File;
import java.util.ArrayList;

/**
 * @author 11718
 *         on 2017  November 22 Wednesday 19:47.
 */

public class FiltersHandler
{
	private ArrayList<CameraFilter> cameraFilters;
	
	public FiltersHandler()
	{
		this.cameraFilters = new ArrayList<>();
		
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
		String filtersDirectory = AppConfig.getFiltersDirectory();
		CameraFilter cameraFilter =
				new CameraFilter(filtersDirectory + File.separator + pathName);
		cameraFilters.add(cameraFilter);
	}
}
