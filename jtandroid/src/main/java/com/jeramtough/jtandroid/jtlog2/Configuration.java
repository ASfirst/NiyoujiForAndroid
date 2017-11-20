package com.jeramtough.jtandroid.jtlog2;

/**
 * Created by 11718
 * on 2017  October 03 Tuesday 21:26.
 */
public class Configuration
{
	private boolean isColouredMode=false;
	private static volatile Configuration configuration;
	private int maxNumberOfRow=110;
	
	private Configuration()
	{
	}
	
	public static Configuration getConfiguration()
	{
		if (configuration==null)
		{
			synchronized (Configuration.class)
			{
				if (configuration==null)
				{
					configuration=new Configuration();
					return configuration;
				}
			}
		}
		
		return configuration;
	}
	
	public boolean isColouredMode()
	{
		return isColouredMode;
	}
	
	public void setColouredMode(boolean colouredMode)
	{
		isColouredMode = colouredMode;
	}
	
	public int getMaxNumberOfRow()
	{
		return maxNumberOfRow;
	}
	
	public void setMaxNumberOfRow(int maxNumberOfRow)
	{
		this.maxNumberOfRow = maxNumberOfRow;
	}
}
