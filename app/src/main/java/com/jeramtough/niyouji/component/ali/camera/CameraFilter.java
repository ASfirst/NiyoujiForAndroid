package com.jeramtough.niyouji.component.ali.camera;

import com.aliyun.struct.effect.EffectFilter;

import java.io.File;

/**
 * @author 11718
 *         on 2017  November 22 Wednesday 19:52.
 */

public class CameraFilter extends EffectFilter
{
	
	public CameraFilter(String s)
	{
		super(s);
	}
	
	public String getIconPath()
	{
		return getPath()+ File.separator+"icon.png";
	}
	
}
