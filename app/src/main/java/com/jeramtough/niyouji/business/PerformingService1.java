package com.jeramtough.niyouji.business;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.jtutil.DateTimeUtil;
import com.jeramtough.jtutil.IdUtil;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.AddPageCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.CreatePerformingRoomCommand;
import com.jeramtough.niyouji.component.app.AppUser;
import com.jeramtough.niyouji.component.travelnote.LiveTravelnotePageView;
import com.jeramtough.niyouji.component.travelnote.TravelnotePageType;
import com.jeramtough.niyouji.component.travelnote.TravelnoteResourceTypes;
import com.jeramtough.niyouji.component.websocket.PerformerWebSocketClient;
import com.jeramtough.niyouji.component.websocket.communicate.PerformerSocketMessageFactory;

import java.util.concurrent.*;

/**
 * @author 11718
 *         on 2018  January 17 Wednesday 18:42.
 */
@JtService
public class PerformingService1 implements PerformingBusiness1
{
	private PerformerWebSocketClient performerWebSocketClient;
	private ExecutorService executorService;
	private AppUser appUser;
	
	@IocAutowire
	public PerformingService1(PerformerWebSocketClient performerWebSocketClient,
			AppUser appUser)
	{
		this.performerWebSocketClient = performerWebSocketClient;
		this.appUser = appUser;
		
		executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		
		executorService.submit(() ->
		{
			//模拟创建房间操作
			try
			{
				performerWebSocketClient.connectBlocking();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			CreatePerformingRoomCommand createPerformingRoomCommand =
					new CreatePerformingRoomCommand();
			createPerformingRoomCommand.setCoverResourceUrl(
					"http://niyouji.oss-cn-shenzhen.aliyuncs.com/images/cover_1516105481681.jpg");
			createPerformingRoomCommand.setCoverType(TravelnoteResourceTypes.IMAGE.toString());
			createPerformingRoomCommand.setCreateTime(DateTimeUtil.getCurrentDateTime());
			createPerformingRoomCommand.setOwnerId(this.appUser.getUserId());
			createPerformingRoomCommand
					.setTravelnoteId(this.appUser.getUserId() + IdUtil.getUUID());
			createPerformingRoomCommand.setTravelnoteTitle("这是测试游记");
			SocketMessage socketMessage = PerformerSocketMessageFactory
					.processCreatePerformingRoomSocketMessage(createPerformingRoomCommand);
			
			performerWebSocketClient.sendSocketMessage(socketMessage);
		});
	}
	
	@Override
	public void spreadTravelnoteSelectedPage(int position)
	{
		SocketMessage socketMessage = PerformerSocketMessageFactory
				.processSelectPageSocketMessage(appUser.getUserId(), position);
		
		executorService.submit(() ->
		{
			performerWebSocketClient.sendSocketMessage(socketMessage);
		});
	}
	
	@Override
	public void spreadTravelnoteAddedPage(LiveTravelnotePageView liveTravelnotePageView)
	{
		AddPageCommand addPageCommand = new AddPageCommand();
		addPageCommand.setCreateTime(DateTimeUtil.getCurrentDateTime());
		addPageCommand.setPageType(liveTravelnotePageView.getTravelnotePageType().toString());
		addPageCommand.setPerformerId(appUser.getUserId());
		
		if (liveTravelnotePageView.getTravelnotePageType() == TravelnotePageType.PICANDWORD)
		{
			addPageCommand.setThemePosition(0);
		}
		else
		{
			addPageCommand.setThemePosition(-1);
		}
		
		SocketMessage socketMessage =
				PerformerSocketMessageFactory.processAddedPageSocketMessage(addPageCommand);
		executorService.submit(() ->
		{
			performerWebSocketClient.sendSocketMessage(socketMessage);
		});
	}
	
	@Override
	public void spreadTravelnoteDeletedPage(int position)
	{
		SocketMessage socketMessage = PerformerSocketMessageFactory
				.processDeletedPageSocketMessage(appUser.getUserId(), position);
		
		executorService.submit(() ->
		{
			performerWebSocketClient.sendSocketMessage(socketMessage);
		});
	}
	
	@Override
	public void spreadPageSetPicture(int position, String imageUrl)
	{
	
	}
	
	@Override
	public void spreadPageSetVideo(int position, String videoUrl)
	{
	
	}
	
	@Override
	public void spreadPageContentChanged(int position, boolean isAdded, String words,
			int start)
	{
	
	}
	
	@Override
	public void spreadPageSetTheme(int position, int themePosition)
	{
	
	}
	
	@Override
	public void spreadPageSetBackgroundMusic(int position, String musicPath)
	{
	
	}
	
	@Override
	public void spreadTravelnoteSentPerformerBarrage(int position, String barrageContent)
	{
	
	}
	
	@Override
	public void spreadTravelnoteEnd()
	{
	
	}
}
