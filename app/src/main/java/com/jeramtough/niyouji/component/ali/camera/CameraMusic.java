package com.jeramtough.niyouji.component.ali.camera;

/**
 * @author 11718
 *         on 2017  November 24 Friday 09:52.
 */

public class CameraMusic
{
	private String name;
	private String path;
	
	public CameraMusic(String name, String path)
	{
		this.name = name;
		this.path = path;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public void setPath(String path)
	{
		this.path = path;
	}
	
	@Override
	public String toString()
	{
		return "CameraMusic{" + "name='" + name + '\'' + ", path='" + path + '\'' + '}';
	}
}
