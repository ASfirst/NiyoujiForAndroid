package com.jeramtough.jtandroid.jtlog2;

import java.util.ArrayList;

/**
 * Created by 11718
 * on 2017  October 03 Tuesday 21:26.
 */
public class JtLog2Config
{
	private boolean isColouredMode = false;
	private static volatile JtLog2Config jtLog2Config;
	private int maxLengthOfRow = 130;
	private ArrayList<String> tagFilters;
	
	private JtLog2Config()
	{
		tagFilters = new ArrayList<>();
	}
	
	public static JtLog2Config getJtLog2Config()
	{
		if (jtLog2Config == null)
		{
			synchronized (JtLog2Config.class)
			{
				if (jtLog2Config == null)
				{
					jtLog2Config = new JtLog2Config();
					return jtLog2Config;
				}
			}
		}
		
		return jtLog2Config;
	}
	
	public boolean isColouredMode()
	{
		return isColouredMode;
	}
	
	public void setColouredMode(boolean colouredMode)
	{
		isColouredMode = colouredMode;
	}
	
	public int getMaxLengthOfRow()
	{
		return maxLengthOfRow;
	}
	
	public void setMaxLengthOfRow(int maxLengthOfRow)
	{
		this.maxLengthOfRow = maxLengthOfRow;
	}

	public void addTagFilter(String tag)
	{
		tagFilters.add(tag);
	}
	
	public ArrayList<String> getTagFilters()
	{
		return tagFilters;
	}
}
