package com.jeramtough.niyouji.business;

import android.location.Location;
import android.os.Bundle;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.function.LocationHolder;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtService;
import com.jeramtough.jtlog3.P;
import com.jeramtough.jtutil.DateTimeUtil;
import com.jeramtough.niyouji.bean.locaiton.TencentLocationServiceResponse;
import com.jeramtough.niyouji.bean.socketmessage.SocketMessage;
import com.jeramtough.niyouji.component.communicate.factory.PerformerSocketMessageFactory;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.CreatePerformingRoomCommand;
import com.jeramtough.niyouji.bean.socketmessage.action.ServerCommandActions;
import com.jeramtough.niyouji.component.ali.oss.AliOssManager;
import com.jeramtough.niyouji.component.ali.sts.NiyoujiStsManager;
import com.jeramtough.niyouji.component.app.AppUser;
import com.jeramtough.niyouji.component.httpclient.TencentHttpClient;
import com.jeramtough.niyouji.component.travelnote.PageCounter;
import com.jeramtough.niyouji.component.travelnote.ProcessNameOfCloud;
import com.jeramtough.niyouji.component.travelnote.TravelnoteResourceTypes;
import com.jeramtough.niyouji.component.websocket.WebSocketClientListener;
import com.jeramtough.niyouji.component.websocket.WebSocketClientProxy;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 11718
 *         on 2018  January 15 Monday 16:33.
 */
@JtService
public class CreateTravelnoteService implements CreateTravelnoteBusiness
{
	private PageCounter pageCounter;
	private AppUser appUser;
	private NiyoujiStsManager niyoujiStsManager;
	private AliOssManager aliOssManager;
	private WebSocketClientProxy webSocketClientProxy;
	private LocationHolder locationHolder;
	
	private Executor executor;
	
	@IocAutowire
	public CreateTravelnoteService(PageCounter pageCounter, AppUser appUser,
			NiyoujiStsManager niyoujiStsManager, AliOssManager aliOssManager,
			WebSocketClientProxy webSocketClientProxy, LocationHolder locationHolder)
	{
		this.pageCounter = pageCounter;
		this.appUser = appUser;
		this.niyoujiStsManager = niyoujiStsManager;
		this.aliOssManager = aliOssManager;
		this.webSocketClientProxy = webSocketClientProxy;
		this.locationHolder = locationHolder;
		
		executor = new ThreadPoolExecutor(0, 20, 60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());
	}
	
	@Override
	public void createTravelnote(String travelnoteTitle, String coverResourcePath,
			BusinessCaller createBusinessCaller, BusinessCaller connectBusinessCaller,
			BusinessCaller uploadBusinessCaller, BusinessCaller obtainLocationBusinessCaller)
	{
		//归零
		pageCounter.setPageCount(0);
		
		//状态归零
		appUser.setPerformingJustNow(false);
		
		//先与服务器连接，然后上传封面，然后获取当前地理位置，然后发送创建游记的命令
		executor.execute(() ->
		{
			try
			{
				//初始化socket客户端对象
				webSocketClientProxy.resetPerformerWebSocketClient();
				
				//连接服务器
				boolean connectSuccessfully =
						webSocketClientProxy.getPerformerWebSocketClient().connectBlocking();
				connectBusinessCaller.getData()
						.putBoolean("connectSuccessfully", connectSuccessfully);
				connectBusinessCaller.callBusiness();
				
				if (connectSuccessfully)
				{
					//上传封面
					String fileSuffix =
							coverResourcePath.substring(coverResourcePath.length() - 3);
					AssumeRoleResponse.Credentials credentials =
							niyoujiStsManager.getCredentials();
					aliOssManager.connect(credentials);
					
					String filename = null;
					String coverResourceUrl = null;
					String coverType = null;
					if (fileSuffix.equals("jpg"))
					{
						filename = ProcessNameOfCloud.processImageCoverName();
						coverResourceUrl = ProcessNameOfCloud.processImageFileUrl(filename);
						coverType = TravelnoteResourceTypes.IMAGE.toString();
					}
					else if (fileSuffix.equals("mp4"))
					{
						filename = ProcessNameOfCloud.processVideoCoverName();
						coverResourceUrl = ProcessNameOfCloud.processVideoFileUrl(filename);
						coverType = TravelnoteResourceTypes.VIDEO.toString();
					}
					boolean uploadSuccessfully =
							aliOssManager.uploadImageFileBlocking(filename, coverResourcePath);
					uploadBusinessCaller.getData()
							.putBoolean("uploadSuccessfully", uploadSuccessfully);
					uploadBusinessCaller.callBusiness();
					
					//获取地理位置
					boolean isShowedLocation = obtainLocationBusinessCaller.getData()
							.getBoolean("isShowedLocation");
					String currentLocation = null;
					Location location = locationHolder.getLocation();
					if (isShowedLocation && location != null)
					{
						TencentHttpClient tencentHttpClient = new TencentHttpClient();
						String locationJs = tencentHttpClient
								.getLocation(location.getLatitude() + "",
										location.getLongitude() + "");
						TencentLocationServiceResponse tencentLocationServiceResponse =
								JSON.parseObject(locationJs,
										TencentLocationServiceResponse.class);
						if (tencentLocationServiceResponse.getStatus() == 0)
						{
							currentLocation =
									tencentLocationServiceResponse.getResult().getAddress();
							obtainLocationBusinessCaller.setSuccessful(true);
							obtainLocationBusinessCaller.getData()
									.putString("currentLocation", currentLocation);
						}
						else
						{
							obtainLocationBusinessCaller.setSuccessful(false);
						}
						obtainLocationBusinessCaller.callBusiness();
					}
					
					if (uploadSuccessfully)
					{
						WebSocketClientListener webSocketClientListener =
								new WebSocketClientListener()
								{
									@Override
									public void onMessage(SocketMessage socketMessage)
									{
										if (socketMessage.getCommandAction() ==
												ServerCommandActions.CREATING_PERFORMING_ROOM_FINISH)
										{
											createBusinessCaller.callBusiness();
										}
									}
								};
						
						webSocketClientProxy.getPerformerWebSocketClient()
								.addWebSocketClientListener(webSocketClientListener);
						
						CreatePerformingRoomCommand createPerformingRoomCommand =
								new CreatePerformingRoomCommand();
						createPerformingRoomCommand.setCoverResourceUrl(coverResourceUrl);
						createPerformingRoomCommand.setCoverType(coverType);
						createPerformingRoomCommand
								.setCreateTime(DateTimeUtil.getCurrentDateTime());
						createPerformingRoomCommand.setPerformerId(appUser.getUserId());
						createPerformingRoomCommand.setTravelnoteTitle(travelnoteTitle);
						createPerformingRoomCommand.setLocation(currentLocation);
						
						SocketMessage socketMessage = PerformerSocketMessageFactory
								.processCreatePerformingRoomSocketMessage(
										createPerformingRoomCommand);
						
						webSocketClientProxy.getPerformerWebSocketClient()
								.sendSocketMessage(socketMessage);
					}
				}
			}
			catch (InterruptedException | IllegalStateException e)
			{
				connectBusinessCaller.getData().putBoolean("connectSuccessfully", false);
				connectBusinessCaller.callBusiness();
				e.printStackTrace();
			}
			catch (ClientException e)
			{
				e.printStackTrace();
			}
		});
	}
	
	@Override
	public Bundle checkCustomCoverImage(String imagePath)
	{
		Bundle bundle=new Bundle();
		String fileSuffix =
				imagePath.substring(imagePath.length() - 3);
		if (!"jpg".equals(fileSuffix))
		{
			bundle.putString("message","暂时只支持jpg格式图片，请检查您选择的图片格式！");
			bundle.putBoolean("isPassed",false);
			return bundle;
		}
		
		File file=new File(imagePath);
		long maxLeng=1024*500;
		if (file.length()>maxLeng)
		{
			bundle.putString("message","图片大小不能超过512kb，请检查您选择的图片大小！");
			bundle.putBoolean("isPassed",false);
			return bundle;
		}
		
		bundle.putBoolean("isPassed",true);
		return bundle;
	}
	
	@Override
	public String getUserNickname()
	{
		return appUser.getNickname();
	}
}
