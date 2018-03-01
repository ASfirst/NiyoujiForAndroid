package com.jeramtough.niyouji.controller.handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageButton;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.jeramtough.heartlayout.HeartLayout;
import com.jeramtough.jtandroid.adapter.ViewsPagerAdapter;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.controller.handler.JtIocHandler;
import com.jeramtough.jtandroid.function.MusicPlayer;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.JtViewPager;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.jtemoji.JtEmojiCachesManager;
import com.jeramtough.jtemoji.JtEmojiUtils;
import com.jeramtough.jtemoji.JtEmojisHandler;
import com.jeramtough.jtlog3.P;
import com.jeramtough.jtutil.StringUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.socketmessage.action.AudienceCommandActions;
import com.jeramtough.niyouji.bean.socketmessage.action.PerformerCommandActions;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.AudienceLeaveCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.EnterPerformingRoomCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.LightAttentionCountCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.SendAudienceBarrageCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.*;
import com.jeramtough.niyouji.bean.travelnote.Travelnote;
import com.jeramtough.niyouji.bean.travelnote.TravelnotePage;
import com.jeramtough.niyouji.business.AudienceBusiness;
import com.jeramtough.niyouji.business.AudienceService;
import com.jeramtough.niyouji.component.ali.camera.MusicsHandler;
import com.jeramtough.niyouji.component.app.GlideApp;
import com.jeramtough.niyouji.component.cache.VideoCacheServer;
import com.jeramtough.niyouji.component.travelnote.AudienceLiveTravelnotePageView;
import com.jeramtough.niyouji.component.travelnote.TravelnotePageType;
import com.jeramtough.niyouji.component.travelnote.picandwordtheme.*;
import com.jeramtough.niyouji.component.ui.AppraisalAreaView;
import com.jeramtough.niyouji.component.ui.DanmakuLayout;
import com.jeramtough.niyouji.controller.dialog.AudienceTravelnoteEndDialog;
import com.jeramtough.niyouji.controller.dialog.EditBarrageDialog;
import com.jeramtough.niyouji.controller.dialog.SelectPwThemeDialog;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

/**
 * @author 11718
 *         on 2018  January 21 Sunday 19:59.
 */

public class AudienceLiveTravelnoteHandler extends JtIocHandler
		implements View.OnTouchListener, ViewPager.OnPageChangeListener

{
	private static final int BUSINESS_CODE_PERFORMER_ACTIONS = 2;
	private static final int BUSINESS_CODE_AUDIENCE_ACTIONS = 3;
	
	private JtViewPager viewPagerTravelnotePages;
	private TextView textViewPerformerNickname;
	private TextView textViewAttentionsCount;
	private TextView textViewPagesCount;
	private TextView textViewAudiencesCount;
	private Button buttonBugDelete;
	private TimedCloseTextView timedCloseTextViewShowMessage;
	private AppraisalAreaView appraisalAreaView;
	private DanmakuLayout layoutDanmaku;
	private ProgressBar progressBar;
	private HeartLayout heartLayout;
	private BoomMenuButton boomMenuButton;
	private FrameLayout layoutDoubleClick;
	private TextView textViewNoPage;
	private AppCompatImageButton imageButtonBack;
	
	
	private ArrayList<AudienceLiveTravelnotePageView> liveTravelnotePageViews;
	
	@InjectService(service = AudienceService.class)
	private AudienceBusiness audienceBusiness;
	
	@InjectComponent
	private MusicsHandler musicsHandler;
	@InjectComponent
	private MusicPlayer musicPlayer;
	
	@InjectComponent
	private PicAndWordResourcesHandler picAndWordResourcesHandler;
	@InjectComponent
	private PwResourcesCacheManager pwResourcesCacheManager;
	
	
	@InjectComponent
	private VideoCacheServer videoCacheServer;
	
	private final boolean debugDownloadMode = false;
	private boolean isFollowMode = true;
	private String performerId;
	private int clickCount = 0;
	private int lightAttentionCount = 0;
	private AudienceLiveTravelnotePageView lastAudienceLiveTravelnotePageView;
	
	public AudienceLiveTravelnoteHandler(Activity activity, String performerId)
	{
		super(activity);
		this.performerId = performerId;
		
		viewPagerTravelnotePages = findViewById(R.id.viewPager_travelnote_pages);
		textViewPerformerNickname = findViewById(R.id.textView_performer_nickname);
		textViewAttentionsCount = findViewById(R.id.textView_attentions_count);
		textViewPagesCount = findViewById(R.id.textView_pages_count);
		textViewAudiencesCount = findViewById(R.id.textView_audiences_count);
		buttonBugDelete = findViewById(R.id.button_bug_delete);
		timedCloseTextViewShowMessage = findViewById(R.id.timedCloseTextView_show_message);
		appraisalAreaView = findViewById(R.id.appraisalAreaView);
		layoutDanmaku = findViewById(R.id.layout_danmaku);
		progressBar = findViewById(R.id.progressBar);
		heartLayout = findViewById(R.id.heart_layout);
		boomMenuButton = findViewById(R.id.boomMenuButton);
		layoutDoubleClick = findViewById(R.id.layout_double_click);
		textViewNoPage = findViewById(R.id.textView_no_page);
		imageButtonBack = findViewById(R.id.imageButton_back);
		
		boomMenuButton.setVisibility(View.INVISIBLE);
		textViewNoPage.setVisibility(View.GONE);
		
		viewPagerTravelnotePages.setScrollble(false);
		imageButtonBack.setOnClickListener(this);
		
		layoutDoubleClick.setOnTouchListener(this);
		viewPagerTravelnotePages.addOnPageChangeListener(this);
		
		initResources();
	}
	
	protected void initResources()
	{
		musicPlayer.setRepeated(true);
		
		liveTravelnotePageViews = new ArrayList<>();
		
		//初始化底部点击按钮
		for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++)
		{
			HamButton.Builder builder = new HamButton.Builder();
			switch (i)
			{
				case 0:
					builder.normalImageRes(R.drawable.ic_beautiful);
					builder.normalColorRes(R.color.menu_color3);
					builder.normalText("关闭跟寻主播模式");
					builder.listener(index ->
					{
						if (isFollowMode)
						{
							boomMenuButton.getBoomButton(index).getTextView()
									.setText("开启跟寻主播模式");
							isFollowMode = false;
							viewPagerTravelnotePages.setScrollble(true);
						}
						else
						{
							boomMenuButton.getBoomButton(index).getTextView()
									.setText("关闭跟寻主播模式");
							isFollowMode = true;
							viewPagerTravelnotePages.setScrollble(false);
						}
					});
					break;
				case 1:
					builder.normalImageRes(R.drawable.ic_send_voice);
					builder.normalColorRes(R.color.menu_color4);
					builder.normalText("发送弹幕");
					builder.listener(index ->
					{
						EditBarrageDialog editBarrageDialog =
								new EditBarrageDialog(this.getContext());
						editBarrageDialog.setEditBarrageListener((String content) ->
						{
							
							audienceBusiness.broadcastAudienceSendBarrage(performerId,
									getCurrentPosition(), content);
						});
						
						if (liveTravelnotePageViews.size() > 0)
						{
							editBarrageDialog.show();
						}
					});
					break;
				
				case 2:
					builder.normalImageRes(R.drawable.ic_favorite_red);
					builder.normalColorRes(R.color.menu_color2);
					builder.normalText("点亮 (双击游记页也可点亮哦)");
					builder.listener(index ->
					{
						lightPerformer();
					});
					break;
				case 3:
					builder.normalImageRes(R.drawable.ic_pay_love);
					builder.normalColorRes(R.color.menu_color1);
					builder.normalText("打赏");
					builder.listener(index ->
					{
						timedCloseTextViewShowMessage.setPrimaryMessage("打赏主播功能建设中。。。");
						timedCloseTextViewShowMessage.closeDelayed(2000);
					});
					break;
			}
			builder.textSize(21);
			builder.textGravity(Gravity.CENTER);
			boomMenuButton.addBuilder(builder);
		}
		
		audienceBusiness.callPerformerActions(performerId,
				new BusinessCaller(this, BUSINESS_CODE_PERFORMER_ACTIONS));
		audienceBusiness
				.callAudienceActions(new BusinessCaller(this, BUSINESS_CODE_AUDIENCE_ACTIONS));
	}
	
	@Override
	public void handleMessage(Message message)
	{
		switch (message.what)
		{
			case BUSINESS_CODE_PERFORMER_ACTIONS:
				int performerAction = message.getData().getInt("performerAction");
				switch (performerAction)
				{
					case PerformerCommandActions.ADDED_PAGE:
						AddPageCommand addPageCommand =
								(AddPageCommand) message.getData().getSerializable("command");
						
						this.addPage(addPageCommand);
						break;
					case PerformerCommandActions.SELECTED_PAGE:
						SelectPageCommand selectPageCommand =
								(SelectPageCommand) message.getData()
										.getSerializable("command");
						
						if (isFollowMode)
						{
							this.selectPage(selectPageCommand);
						}
						break;
					case PerformerCommandActions.DELETED_PAGE:
						DeletePageCommand deletePageCommand =
								(DeletePageCommand) message.getData()
										.getSerializable("command");
						
						this.deletePage(deletePageCommand);
						break;
					
					case PerformerCommandActions.PAGE_SET_IMAGE:
						PageSetImageCommand pageSetImageCommand =
								(PageSetImageCommand) message.getData()
										.getSerializable("command");
						
						this.pageSetImage(pageSetImageCommand);
						break;
					
					case PerformerCommandActions.PAGE_SET_VIDEO:
						PageSetVideoCommand pageSetVideoCommand =
								(PageSetVideoCommand) message.getData()
										.getSerializable("command");
						
						this.pageSetVideo(pageSetVideoCommand);
						break;
					
					case PerformerCommandActions.PAGE_SET_THEME:
						PageSetThemeCommand pageSetThemeCommand =
								(PageSetThemeCommand) message.getData()
										.getSerializable("command");
						
						this.pageSetTheme(pageSetThemeCommand);
						break;
					
					case PerformerCommandActions.PAGE_SET_BACKGROUND_MUSIC:
						PageSetBackgroundMusicCommand pageSetBackgroundMusicCommand =
								(PageSetBackgroundMusicCommand) message.getData()
										.getSerializable("command");
						
						this.pageSetBackgroundMusic(pageSetBackgroundMusicCommand);
						break;
					
					case PerformerCommandActions.PAGE_TEXT_CHANGED:
						PageTextChangeCommand pageTextChangeCommand =
								(PageTextChangeCommand) message.getData()
										.getSerializable("command");
						
						this.pageTextChanged(pageTextChangeCommand);
						break;
					
					case PerformerCommandActions.SENT_PERFORMER_BARRAGE:
						SendPerformerBarrageCommand sendPerformerBarrageCommand =
								(SendPerformerBarrageCommand) message.getData()
										.getSerializable("command");
						
						this.sentPerformerBarrage(sendPerformerBarrageCommand);
						break;
					
					case PerformerCommandActions.TRAVELNOTE_END:
						TravelnoteEndCommand travelnoteEndCommand =
								(TravelnoteEndCommand) message.getData()
										.getSerializable("command");
						
						this.travelnoteEnd(travelnoteEndCommand);
						break;
					case PerformerCommandActions.PERFORMER_LEAVE:
						PerformerLeaveCommand performerLeaveCommand =
								(PerformerLeaveCommand) message.getData()
										.getSerializable("command");
						this.whenPerformerLeave(performerLeaveCommand);
						break;
					
					case PerformerCommandActions.PERFORMER_REBACK:
						PerformerRebackCommand performerRebackCommand =
								(PerformerRebackCommand) message.getData()
										.getSerializable("command");
						this.whenPerformerReback(performerRebackCommand);
						break;
				}
				break;
			
			case BUSINESS_CODE_AUDIENCE_ACTIONS:
				int audienceAction = message.getData().getInt("audienceAction");
				switch (audienceAction)
				{
					case AudienceCommandActions.ENTER_PERFORMING_ROOM:
						//其他观众进入直播间时回调到这里
						EnterPerformingRoomCommand enterPerformingRoomCommand =
								(EnterPerformingRoomCommand) message.getData()
										.getSerializable("command");
						otherAudienceEnterPerformingRoom(enterPerformingRoomCommand);
						break;
					
					case AudienceCommandActions.SEND_AUDIENCE_BARRAGE:
						SendAudienceBarrageCommand sendAudienceBarrageCommand =
								(SendAudienceBarrageCommand) message.getData()
										.getSerializable("command");
						sentAudienceBarrage(sendAudienceBarrageCommand);
						break;
					
					case AudienceCommandActions.LIGHT_ATTENTION_COUNT:
						LightAttentionCountCommand lightAttentionCountCommand =
								(LightAttentionCountCommand) message.getData()
										.getSerializable("command");
						lightAttentionCount(lightAttentionCountCommand);
						break;
					
					case AudienceCommandActions.AUDIENCE_LEAVE:
						AudienceLeaveCommand audienceLeaveCommand =
								(AudienceLeaveCommand) message.getData()
										.getSerializable("command");
						otherAudienceLeavePerformingRoom(audienceLeaveCommand);
						break;
				}
				break;
		}
	}
	
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.imageButton_back:
				leaveByUseingDialog();
				break;
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		if (v.getId() == R.id.layout_double_click)
		{
			if (event.getAction() == MotionEvent.ACTION_DOWN)
			{
				clickCount++;
				if (clickCount == 1)
				{
					v.postDelayed(() ->
					{
						if (clickCount == 2)
						{
							lightPerformer();
						}
						clickCount = 0;
					}, 300);
				}
			}
		}
		return false;
	}
	
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{
	}
	
	@Override
	public void onPageSelected(int position)
	{
		if (!isFollowMode)
		{
			SelectPageCommand selectPageCommand = new SelectPageCommand();
			selectPageCommand.setPosition(position);
			
			this.selectPage(selectPageCommand);
		}
	}
	
	@Override
	public void onPageScrollStateChanged(int state)
	{
	}
	
	@Override
	public void onDestroy()
	{
		musicPlayer.end();
		recycleCurrentPageResource();
		Glide.get(this.getContext()).clearMemory();
		JtEmojiCachesManager.getJtEmojiCachesManager().clearAllCaches();
	}
	
	public void loadTravelnote(Travelnote travelnote)
	{
		List<TravelnotePage> travelnotePages = travelnote.getTravelnotePages();
		
		for (TravelnotePage travelnotePage : travelnotePages)
		{
			AudienceLiveTravelnotePageView liveTravelnotePageView =
					new AudienceLiveTravelnotePageView(getContext());
			liveTravelnotePageViews.add(liveTravelnotePageView);
			
			liveTravelnotePageView.setCurrentThemePosition(travelnotePage.getThemePosition());
			liveTravelnotePageView.setMusicPath(travelnotePage.getBackgroundMusicPath());
			liveTravelnotePageView.setResourcePath(travelnotePage.getResourceUrl());
			liveTravelnotePageView.setTravelnotePageType(
					TravelnotePageType.toTravelnotePageType(travelnotePage.getPageType()));
			
			if (travelnotePage.getTextContent() != null)
			{
				//翻译表情字符
				SpannableString spannableString = JtEmojiUtils.getEmotionContent(getContext(),
						liveTravelnotePageView.getTextViewTravelnotePageContent(),
						JtEmojisHandler.getJtEmojisHandler(), travelnotePage.getTextContent());
				liveTravelnotePageView.getTextViewTravelnotePageContent()
						.setText(spannableString);
			}
			
			
		}
		
		ViewsPagerAdapter adapter = new ViewsPagerAdapter(liveTravelnotePageViews);
		//强制更新UI
		adapter.setForceUpdateMode(true);
		viewPagerTravelnotePages.setAdapter(adapter);
		
		//加上这个用户本生的观众数
		int audiencesCount = Integer.parseInt(textViewAudiencesCount.getText().toString()) + 1;
		textViewAudiencesCount.setText(audiencesCount + "");
		
		//模拟选中最后一页或者提示没有page
		if (travelnotePages.size() > 0)
		{
			SelectPageCommand selectPageCommand = new SelectPageCommand();
			selectPageCommand.setPosition(travelnotePages.size() - 1);
			selectPage(selectPageCommand);
		}
		else
		{
			textViewNoPage.setVisibility(View.VISIBLE);
		}
		
		progressBar.setVisibility(View.INVISIBLE);
		boomMenuButton.setVisibility(View.VISIBLE);
		timedCloseTextViewShowMessage.setNiceMessage("初始化资源完成");
		timedCloseTextViewShowMessage.closeDelayed(1000);
	}
	
	public int getCurrentPosition()
	{
		return viewPagerTravelnotePages.getCurrentItem();
	}
	
	public AudienceLiveTravelnotePageView getCurrentAudienceLiveTravelnoteView()
	{
		return liveTravelnotePageViews.get(getCurrentPosition());
	}
	
	public void leaveByUseingDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setMessage("是否立即退出游记直播？").setNegativeButton("取消", (dialog, which) ->
		{
		}).setPositiveButton("确定", (dialog, which) ->
		{
			audienceBusiness.broadcastAudienceLeave(performerId);
			getActivity().finish();
		}).create().show();
	}
	
	//***********************************************************
	private void addPage(AddPageCommand addPageCommand)
	{
		AudienceLiveTravelnotePageView liveTravelnotePageView =
				new AudienceLiveTravelnotePageView(getContext());
		
		liveTravelnotePageView.setTravelnotePageType(
				TravelnotePageType.toTravelnotePageType((addPageCommand.getPageType())));
		
		//提示当前主播行为
		if (liveTravelnotePageView.getTravelnotePageType() == TravelnotePageType.PICANDWORD)
		{
			timedCloseTextViewShowMessage.setPrimaryMessage("主播正在拍照中。。。");
			
		}
		else if (liveTravelnotePageView.getTravelnotePageType() == TravelnotePageType.VIDEO)
		{
			timedCloseTextViewShowMessage.setPrimaryMessage("主播正在录像中。。。");
		}
		timedCloseTextViewShowMessage.visible();
		
		liveTravelnotePageViews.add(liveTravelnotePageView);
		
		viewPagerTravelnotePages.getAdapter().notifyDataSetChanged();
		
		
	}
	
	private void deletePage(DeletePageCommand deletePageCommand)
	{
		//先回收资源
		recycleCurrentPageResource();
		//再移除page
		liveTravelnotePageViews.remove(deletePageCommand.getPosition());
		viewPagerTravelnotePages.getAdapter().notifyDataSetChanged();
		
		P.debug(liveTravelnotePageViews.size());
		if (liveTravelnotePageViews.size() <= 0)
		{
			textViewNoPage.setVisibility(View.VISIBLE);
		}
		pauseMusicIf();
	}
	
	private void selectPage(SelectPageCommand selectPageCommand)
	{
		//取消没有page提示
		if (textViewNoPage.getVisibility() == View.VISIBLE)
		{
			textViewNoPage.setVisibility(View.GONE);
		}
		
		//先暂停背景音乐和回收视频资源
		pauseMusicIf();
		recycleCurrentPageResource();
		
		viewPagerTravelnotePages.setCurrentItem(selectPageCommand.getPosition());
		
		updatePagesCount();
		
		//恢复显示的资源内容
		if (getCurrentAudienceLiveTravelnoteView().getResourcePath() != null)
		{
			if (getCurrentAudienceLiveTravelnoteView().getTravelnotePageType() ==
					TravelnotePageType.PICANDWORD)
			{
				PageSetImageCommand pageSetImageCommand = new PageSetImageCommand();
				pageSetImageCommand.setPosition(selectPageCommand.getPosition());
				pageSetImageCommand
						.setImageUrl(getCurrentAudienceLiveTravelnoteView().getResourcePath());
				
				this.pageSetImage(pageSetImageCommand);
				
				//图文页才恢复音乐
				if (getCurrentAudienceLiveTravelnoteView().getMusicPath() != null)
				{
					PageSetBackgroundMusicCommand pageSetBackgroundMusicCommand =
							new PageSetBackgroundMusicCommand();
					pageSetBackgroundMusicCommand.setPosition(selectPageCommand.getPosition());
					pageSetBackgroundMusicCommand.setMusicPath(
							getCurrentAudienceLiveTravelnoteView().getMusicPath());
					
					this.pageSetBackgroundMusic(pageSetBackgroundMusicCommand);
				}
				
				//图文页才恢复主题
				PageSetThemeCommand pageSetThemeCommand = new PageSetThemeCommand();
				pageSetThemeCommand.setPosition(selectPageCommand.getPosition());
				pageSetThemeCommand.setThemePosition(
						getCurrentAudienceLiveTravelnoteView().getCurrentThemePosition());
				
				this.pageSetTheme(pageSetThemeCommand);
				
				//恢复文字，因为视频页切换到图文页的乱象bug
				if (lastAudienceLiveTravelnotePageView != null &&
						lastAudienceLiveTravelnotePageView.getTravelnotePageType() ==
								TravelnotePageType.VIDEO)
				{
					viewPagerTravelnotePages.getAdapter().notifyDataSetChanged();
				}
				
			}
			else if (getCurrentAudienceLiveTravelnoteView().getTravelnotePageType() ==
					TravelnotePageType.VIDEO)
			{
				PageSetVideoCommand pageSetVideoCommand = new PageSetVideoCommand();
				pageSetVideoCommand.setPosition(selectPageCommand.getPosition());
				pageSetVideoCommand
						.setVideoUrl(getCurrentAudienceLiveTravelnoteView().getResourcePath());
				
				this.pageSetVideo(pageSetVideoCommand);
			}
		}
		
		//上个page作用域以结束
		lastAudienceLiveTravelnotePageView = getCurrentAudienceLiveTravelnoteView();
	}
	
	private void pageSetImage(PageSetImageCommand pageSetImageCommand)
	{
		timedCloseTextViewShowMessage.invisible();
		
		if (debugDownloadMode)
		{
			//暂时先设置一张静态的图片
			pageSetImageCommand.setImageUrl(
					"http://niyouji.oss-cn-shenzhen.aliyuncs.com/images/img_0_172641769.jpg");
		}
		
		liveTravelnotePageViews.get(pageSetImageCommand.getPosition())
				.setResourcePath(pageSetImageCommand.getImageUrl());
		
		//加载图片
		GlideApp.with(getContext()).load(pageSetImageCommand.getImageUrl()).fitCenter()
				.skipMemoryCache(true)
				.into(getCurrentAudienceLiveTravelnoteView().getViewPictureOfPage());
	}
	
	private void pageSetVideo(PageSetVideoCommand pageSetVideoCommand)
	{
		timedCloseTextViewShowMessage.invisible();
		if (debugDownloadMode)
		{
			//暂时先设置静态视频
			pageSetVideoCommand.setVideoUrl(
					"http://niyouji.oss-cn-shenzhen.aliyuncs.com/videos/vdo_2_224281219.mp4");
		}
		
		liveTravelnotePageViews.get(pageSetVideoCommand.getPosition())
				.setResourcePath(pageSetVideoCommand.getVideoUrl());
		
		AudienceLiveTravelnotePageView audienceLiveTravelnotePageView =
				liveTravelnotePageViews.get(pageSetVideoCommand.getPosition());
		String videoCacheUrl = videoCacheServer.toCacheUrl(pageSetVideoCommand.getVideoUrl());
		audienceLiveTravelnotePageView.getVideoViewTravelnotePage()
				.setVideoPath(videoCacheUrl);
		
		audienceLiveTravelnotePageView.getVideoViewTravelnotePage().start();
	}
	
	private void pageSetTheme(PageSetThemeCommand pageSetThemeCommand)
	{
		liveTravelnotePageViews.get(pageSetThemeCommand.getPosition())
				.setCurrentThemePosition(pageSetThemeCommand.getThemePosition());
		
		PwResourcePosition pwResourcePosition =
				picAndWordResourcesHandler.getPwResourcePositions()
						.get(pageSetThemeCommand.getThemePosition());
		PwResourceCache pwResourceCache =
				pwResourcesCacheManager.getPwResourceCache(pwResourcePosition);
		PicAndWordTheme picAndWordTheme =
				new PicAndWordThemeImpl(getContext(), pwResourceCache);
		
		picAndWordTheme.setMainBackground(
				liveTravelnotePageViews.get(pageSetThemeCommand.getPosition())
						.getLayoutAudienceLiveTravelnotePage());
		
		picAndWordTheme.setTextViewOrEditText(
				liveTravelnotePageViews.get(pageSetThemeCommand.getPosition())
						.getTextViewTravelnotePageContent());
		picAndWordTheme.setFrame(liveTravelnotePageViews.get(pageSetThemeCommand.getPosition())
				.getImageViewFrame());
	}
	
	private void pageSetBackgroundMusic(
			PageSetBackgroundMusicCommand pageSetBackgroundMusicCommand)
	{
		musicPlayer.playMusic(pageSetBackgroundMusicCommand.getMusicPath());
		
		liveTravelnotePageViews.get(pageSetBackgroundMusicCommand.getPosition())
				.setMusicPath(pageSetBackgroundMusicCommand.getMusicPath());
	}
	
	private void pageTextChanged(PageTextChangeCommand pageTextChangeCommand)
	{
		AudienceLiveTravelnotePageView audienceLiveTravelnotePageView =
				liveTravelnotePageViews.get(pageTextChangeCommand.getPosition());
		
		String text =
				audienceLiveTravelnotePageView.getTextViewTravelnotePageContent().getText()
						.toString();
		
		text = StringUtil.addOrDeleteWords(text, pageTextChangeCommand.isAdded(),
				pageTextChangeCommand.getStart(), pageTextChangeCommand.getWords());
		
		SpannableString spannableString = JtEmojiUtils.getEmotionContent(getContext(),
				audienceLiveTravelnotePageView.getTextViewTravelnotePageContent(),
				JtEmojisHandler.getJtEmojisHandler(), text);
		
		audienceLiveTravelnotePageView.getTextViewTravelnotePageContent()
				.setText(spannableString);
		
		if (isFollowMode)
		{
			//混动文本到底部
			audienceLiveTravelnotePageView.scrollToBottom();
		}
		
	}
	
	private void sentPerformerBarrage(SendPerformerBarrageCommand sendPerformerBarrageCommand)
	{
		TextView textView = new TextView(getContext());
		textView.setBackgroundResource(R.color.colorPrimary);
		textView.setPadding(10, 10, 10, 10);
		textView.setText(sendPerformerBarrageCommand.getContent());
		layoutDanmaku.addViewWithAnimation(textView, DanmakuLayout.ANIMATION_STYLE2);
		
		appraisalAreaView.addAppraisal(sendPerformerBarrageCommand.getNickname(),
				sendPerformerBarrageCommand.getContent(), 2);
	}
	
	private void sentAudienceBarrage(SendAudienceBarrageCommand sendAudienceBarrageCommand)
	{
		TextView textView = new TextView(getContext());
		textView.setBackgroundResource(R.color.colorPrimary);
		textView.setPadding(10, 10, 10, 10);
		textView.setText(sendAudienceBarrageCommand.getContent());
		layoutDanmaku.addViewWithAnimation(textView, DanmakuLayout.ANIMATION_STYLE1);
		
		appraisalAreaView.addAppraisal(sendAudienceBarrageCommand.getNickname(),
				sendAudienceBarrageCommand.getContent(), 1);
	}
	
	private void lightAttentionCount(LightAttentionCountCommand lightAttentionCountCommand)
	{
		heartLayout.addHeartWithRandomColor();
		int count = Integer.parseInt(textViewAttentionsCount.getText().toString()) + 1;
		textViewAttentionsCount.setText(count + "");
	}
	
	
	private void otherAudienceEnterPerformingRoom(
			EnterPerformingRoomCommand enterPerformingRoomCommand)
	{
		int audiencesCount = Integer.parseInt(textViewAudiencesCount.getText().toString()) + 1;
		textViewAudiencesCount.setText(audiencesCount + "");
		
		//系统通知
		if (enterPerformingRoomCommand.getAudienceNickname() != null)
		{
			appraisalAreaView.addSystemMessage(
					enterPerformingRoomCommand.getAudienceNickname() + "进入直播间");
		}
		else
		{
			appraisalAreaView.addSystemMessage("一名观众进入直播间");
			
		}
	}
	
	private void otherAudienceLeavePerformingRoom(AudienceLeaveCommand audienceLeaveCommand)
	{
		int audiencesCount = Integer.parseInt(textViewAudiencesCount.getText().toString());
		if (audiencesCount > 0)
		{
			audiencesCount--;
		}
		textViewAudiencesCount.setText(audiencesCount + "");
		
		//系统通知
		if (audienceLeaveCommand.getAudienceNickname() != null)
		{
			appraisalAreaView
					.addSystemMessage(audienceLeaveCommand.getAudienceNickname() + "退出直播间");
		}
		else
		{
			appraisalAreaView.addSystemMessage("一名观众退出直播间");
		}
		
	}
	
	private void travelnoteEnd(TravelnoteEndCommand travelnoteEndCommand)
	{
		AudienceTravelnoteEndDialog dialog =
				new AudienceTravelnoteEndDialog(this.getActivity());
		dialog.show();
	}
	
	private void whenPerformerLeave(PerformerLeaveCommand performerLeaveCommand)
	{
		timedCloseTextViewShowMessage.setErrorMessage("主播断线中?.?.?..");
		timedCloseTextViewShowMessage.visible();
	}
	
	private void whenPerformerReback(PerformerRebackCommand performerRebackCommand)
	{
		timedCloseTextViewShowMessage.setNiceMessage("主播已重连~");
		timedCloseTextViewShowMessage.closeDelayed(3000);
	}
	
	private void pauseMusicIf()
	{
		if (musicPlayer.getMediaPlayer().isPlaying())
		{
			musicPlayer.getMediaPlayer().pause();
		}
	}
	
	private void recycleCurrentPageResource()
	{
		if (liveTravelnotePageViews.size() > 0)
		{
			if (isFollowMode)
			{
				if (getCurrentAudienceLiveTravelnoteView().getTravelnotePageType() ==
						TravelnotePageType.VIDEO)
				{
					getCurrentAudienceLiveTravelnoteView().getVideoViewTravelnotePage()
							.stopAndClear();
				}
			}
			else
			{
				if (lastAudienceLiveTravelnotePageView != null &&
						lastAudienceLiveTravelnotePageView.getTravelnotePageType() ==
								TravelnotePageType.VIDEO)
				{
					lastAudienceLiveTravelnotePageView.getVideoViewTravelnotePage()
							.stopAndClear();
				}
			}
			
		}
	}
	
	private void updatePagesCount()
	{
		
		textViewPagesCount
				.setText((getCurrentPosition() + 1) + "/" + (liveTravelnotePageViews.size()));
	}
	
	private void lightPerformer()
	{
		lightAttentionCount++;
		if (lightAttentionCount <= 10)
		{
			audienceBusiness.broadcastLightAttentionCount(performerId);
		}
		else
		{
			Toast.makeText(this.getContext(), "点亮以到最大限额", Toast.LENGTH_SHORT).show();
		}
	}
	
}
