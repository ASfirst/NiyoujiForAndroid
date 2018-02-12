package com.jeramtough.niyouji.business;

import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.jtutil.DateTimeUtil;
import com.jeramtough.niyouji.bean.travelnote.Appraise;
import com.jeramtough.niyouji.component.app.AppUser;
import com.jeramtough.niyouji.component.httpclient.NiyoujiHttpClient;

import java.io.IOException;

/**
 * @author 11718
 *         on 2018  February 11 Sunday 23:06.
 */
@JtService
public class FinishedTravelnoteService implements FinishedTravelnoteBusiness
{
	private AppUser appUser;
	private NiyoujiHttpClient niyoujiHttpClient;
	
	@IocAutowire
	public FinishedTravelnoteService(AppUser appUser, NiyoujiHttpClient niyoujiHttpClient)
	{
		this.appUser = appUser;
		this.niyoujiHttpClient = niyoujiHttpClient;
	}
	
	@Override
	public boolean userHasLogined()
	{
		return appUser.getHasLogined();
	}
	
	@Override
	public void publishAppraise(String travelnoteId, String appraiseContent,
			BusinessCaller businessCaller)
	{
		if (appraiseContent.length() > 0)
		{
			new Thread()
			{
				@Override
				public void run()
				{
					Appraise appraise = new Appraise();
					appraise.setNickname(appUser.getNickname());
					appraise.setContent(appraiseContent);
					appraise.setTravelnoteId(travelnoteId);
					appraise.setCreateTime(DateTimeUtil.getCurrentDateTime());
					
					boolean isSuccessful = niyoujiHttpClient.publishAppraise(appraise);
					businessCaller.getData().putSerializable("appraise",appraise);
					businessCaller.setSuccessful(isSuccessful);
					businessCaller.callBusiness();
				}
			}.start();
		}
		else
		{
			businessCaller.setSuccessful(false);
			businessCaller.setMessage("评价内容为空！");
			businessCaller.callBusiness();
		}
	}
	
	@Override
	public void obtainAppraisesCount(String travelnoteId, BusinessCaller businessCaller)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					String appraisesCount = niyoujiHttpClient.getAppraisesCount(travelnoteId);
					businessCaller.setSuccessful(true);
					businessCaller.getData().putString("appraisesCount", appraisesCount);
					businessCaller.callBusiness();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
	
}
