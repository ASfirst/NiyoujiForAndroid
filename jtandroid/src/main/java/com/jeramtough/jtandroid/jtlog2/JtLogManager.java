package com.jeramtough.jtandroid.jtlog2;

import com.jeramtough.jtandroid.jtlog2.logger.Logger;

import java.util.ArrayList;

/**
 * @author JeramTough
 *         on 2017  December 15 Friday 09:43.
 */

public class JtLogManager
{
	public synchronized void print(LogInformation logInformation, Logger logger)
	{
		ArrayList<String> tagFilters = JtLog2Config.getJtLog2Config().getTagFilters();
		if (tagFilters.size() > 0)
		{
			if (tagFilters.contains(logInformation.getTag())==false)
			{
				logger.println(logInformation);
			}
		}
		else
		{
			logger.println(logInformation);
		}
	}
}
