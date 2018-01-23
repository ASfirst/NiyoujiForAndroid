package com.jeramtough.niyouji.business;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.jtutil.DateTimeUtil;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.*;
import com.jeramtough.niyouji.component.app.AppUser;
import com.jeramtough.niyouji.component.travelnote.LiveTravelnotePageView;
import com.jeramtough.niyouji.component.travelnote.TravelnotePageType;
import com.jeramtough.niyouji.component.travelnote.TravelnoteResourceTypes;
import com.jeramtough.niyouji.component.websocket.PerformerWebSocketClient;
import com.jeramtough.niyouji.component.communicate.factory.PerformerSocketMessageFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
				
				CreatePerformingRoomCommand createPerformingRoomCommand =
						new CreatePerformingRoomCommand();
				createPerformingRoomCommand.setCoverResourceUrl(
						"http://niyouji.oss-cn-shenzhen.aliyuncs.com/images/cover_1516105481681.jpg");
				createPerformingRoomCommand
						.setCoverType(TravelnoteResourceTypes.IMAGE.toString());
				createPerformingRoomCommand.setCreateTime(DateTimeUtil.getCurrentDateTime());
				createPerformingRoomCommand.setPerformerId(this.appUser.getUserId());
				createPerformingRoomCommand.setTravelnoteTitle("这是测试游记");
				SocketMessage socketMessage = PerformerSocketMessageFactory
						.processCreatePerformingRoomSocketMessage(createPerformingRoomCommand);
				
				performerWebSocketClient.sendSocketMessage(socketMessage);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
		});
	}
	
	@Override
	public void spreadTravelnoteSelectedPage(int position)
	{
		SelectPageCommand selectPageCommand = new SelectPageCommand();
		selectPageCommand.setPerformerId(appUser.getUserId());
		selectPageCommand.setPosition(position);
		
		SocketMessage socketMessage = PerformerSocketMessageFactory
				.processSelectPageSocketMessage(selectPageCommand);
		
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
		DeletePageCommand deletePageCommand = new DeletePageCommand();
		deletePageCommand.setPerformerId(appUser.getUserId());
		deletePageCommand.setPosition(position);
		
		SocketMessage socketMessage = PerformerSocketMessageFactory
				.processDeletedPageSocketMessage(deletePageCommand);
		
		executorService.submit(() ->
		{
			performerWebSocketClient.sendSocketMessage(socketMessage);
		});
	}
	
	@Override
	public void spreadPageSetPicture(int position, String imageUrl)
	{
		PageSetImageCommand pageSetImageCommand = new PageSetImageCommand();
		pageSetImageCommand.setPerformerId(appUser.getUserId());
		pageSetImageCommand.setPosition(position);
		pageSetImageCommand.setImageUrl(imageUrl);
		
		SocketMessage socketMessage = PerformerSocketMessageFactory
				.processPageSetImageSocketMessage(pageSetImageCommand);
		
		executorService.submit(() ->
		{
			performerWebSocketClient.sendSocketMessage(socketMessage);
		});
	}
	
	@Override
	public void spreadPageSetVideo(int position, String videoUrl)
	{
		PageSetVideoCommand pageSetVideoCommand = new PageSetVideoCommand();
		pageSetVideoCommand.setPerformerId(appUser.getUserId());
		pageSetVideoCommand.setPosition(position);
		pageSetVideoCommand.setVideoUrl(videoUrl);
		
		SocketMessage socketMessage = PerformerSocketMessageFactory
				.processPageSetVideoSocketMessage(pageSetVideoCommand);
		
		executorService.submit(() ->
		{
			performerWebSocketClient.sendSocketMessage(socketMessage);
		});
	}
	
	@Override
	public void spreadPageContentChanged(int position, boolean isAdded, String words,
			int start)
	{
		PageTextChangeCommand pageTextChangeCommand = new PageTextChangeCommand();
		pageTextChangeCommand.setPerformerId(appUser.getUserId());
		pageTextChangeCommand.setAdded(isAdded);
		pageTextChangeCommand.setPosition(position);
		pageTextChangeCommand.setStart(start);
		pageTextChangeCommand.setWords(words);
		
		SocketMessage socketMessage = PerformerSocketMessageFactory
				.processPageTextChangeCommandSocketMessage(pageTextChangeCommand);
		
		executorService.submit(() ->
		{
			performerWebSocketClient.sendSocketMessage(socketMessage);
		});
	}
	
	@Override
	public void spreadPageSetTheme(int position, int themePosition)
	{
		PageSetThemeCommand pageSetThemeCommand = new PageSetThemeCommand();
		pageSetThemeCommand.setPerformerId(appUser.getUserId());
		pageSetThemeCommand.setPosition(position);
		pageSetThemeCommand.setThemePosition(themePosition);
		
		SocketMessage socketMessage = PerformerSocketMessageFactory
				.processPageSetThemeCommandSSocketMessage(pageSetThemeCommand);
		
		executorService.submit(() ->
		{
			performerWebSocketClient.sendSocketMessage(socketMessage);
		});
	}
	
	@Override
	public void spreadPageSetBackgroundMusic(int position, String musicPath)
	{
		PageSetBackgroundMusicCommand pageSetBackgroundMusicCommand =
				new PageSetBackgroundMusicCommand();
		pageSetBackgroundMusicCommand.setPerformerId(appUser.getUserId());
		pageSetBackgroundMusicCommand.setPosition(position);
		pageSetBackgroundMusicCommand.setMusicPath(musicPath);
		
		SocketMessage socketMessage = PerformerSocketMessageFactory
				.processPageSetBackgroundMusicCommandSocketMessage(
						pageSetBackgroundMusicCommand);
		
		executorService.submit(() ->
		{
			performerWebSocketClient.sendSocketMessage(socketMessage);
		});
	}
	
	@Override
	public void spreadTravelnoteSentPerformerBarrage(int position, String barrageContent)
	{
		SendPerformerBarrageCommand sendPerformerBarrageCommand =
				new SendPerformerBarrageCommand();
		sendPerformerBarrageCommand.setPerformerId(appUser.getUserId());
		sendPerformerBarrageCommand.setPosition(position);
		sendPerformerBarrageCommand.setContent(barrageContent);
		sendPerformerBarrageCommand.setCreateTime(DateTimeUtil.getCurrentDateTime());
		sendPerformerBarrageCommand.setIsPerformers(true);
		sendPerformerBarrageCommand.setNickname(appUser.getNickname());
		
		SocketMessage socketMessage = PerformerSocketMessageFactory
				.processSendPerformerBarrageCommandSocketMessage(sendPerformerBarrageCommand);
		
		executorService.submit(() ->
		{
			performerWebSocketClient.sendSocketMessage(socketMessage);
		});
	}
	
	@Override
	public void spreadTravelnoteEnd()
	{
		TravelnoteEndCommand travelnoteEndCommand = new TravelnoteEndCommand();
		travelnoteEndCommand.setPerformerId(appUser.getUserId());
		
		SocketMessage socketMessage = PerformerSocketMessageFactory
				.processTravelnoteEndCommandSocketMessage(travelnoteEndCommand);
		
		executorService.submit(() ->
		{
			performerWebSocketClient.sendSocketMessage(socketMessage);
		});
	}
}
