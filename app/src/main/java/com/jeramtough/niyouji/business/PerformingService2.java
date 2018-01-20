package com.jeramtough.niyouji.business;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.niyouji.bean.travelnote.LiveTravelnoteCover;
import com.jeramtough.niyouji.component.httpclient.NiyoujiHttpClient;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author 11718
 *         on 2018  January 20 Saturday 17:22.
 */
@JtService
public class PerformingService2 implements PerformingBusiness2
{
	private NiyoujiHttpClient niyoujiHttpClient;
	private ExecutorService executorService;
	
	@IocAutowire
	public PerformingService2(NiyoujiHttpClient niyoujiHttpClient)
	{
		this.niyoujiHttpClient = niyoujiHttpClient;
		executorService = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());
	}
	
	
	@Override
	public void getTravelnoteCovers(BusinessCaller businessCaller)
	{
		executorService.submit(() ->
		{
			String responseStr = niyoujiHttpClient.getLiveTravelnotesBlocking();
			List<LiveTravelnoteCover> liveTravelnoteCoverList =
					JSON.parseArray(responseStr, LiveTravelnoteCover.class);
			LiveTravelnoteCover[] liveTravelnoteCovers = liveTravelnoteCoverList
					.toArray(new LiveTravelnoteCover[liveTravelnoteCoverList.size()]);
			
			businessCaller.getData().putSerializable("liveTravelnoteCovers", liveTravelnoteCovers);
			businessCaller.callBusiness();
		});
	}
}
