package com.jeramtough.niyouji.component.picandword;

import java.util.HashMap;

import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtandroid.jtlog2.P;

@JtComponent
public class PwResourcesCacheManager
{
	private int maxCacheCount = 2;
	
	private HashMap<PwResourcePosition, PwResourceCache> pwResourceCachesMap;
	
	private PwResourcePosition lastPwResourcePosition;
	
	public PwResourcesCacheManager()
	{
		pwResourceCachesMap = new HashMap<>();
	}
	
	
	public PwResourceCache getPwResourceCache(PwResourcePosition pwResourcePosition)
	{
		PwResourceCache pwResourceCache = pwResourceCachesMap.get(pwResourcePosition);
		
		if (pwResourceCache != null)
		{
			return pwResourceCache;
		}
		else
		{
			pwResourceCache = new PwResourceCache(pwResourcePosition);
			if (pwResourceCachesMap.size() < maxCacheCount)
			{
				pwResourceCachesMap.put(pwResourcePosition, pwResourceCache);
			}
			else
			{
				PwResourceCache lastPwResourceCache =
						pwResourceCachesMap.get(lastPwResourcePosition);
				lastPwResourceCache.recycleMemory();
				pwResourceCachesMap.remove(lastPwResourcePosition);
				pwResourceCachesMap.put(pwResourcePosition, pwResourceCache);
			}
		}
		
		lastPwResourcePosition = pwResourcePosition;
		
		P.debug(pwResourceCachesMap.size());
		
		return pwResourceCache;
	}
	
}
