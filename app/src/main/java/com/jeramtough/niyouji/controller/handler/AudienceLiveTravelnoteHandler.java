package com.jeramtough.niyouji.controller.handler;

import android.app.Activity;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jeramtough.heartlayout.HeartLayout;
import com.jeramtough.jtandroid.adapter.ViewsPagerAdapter;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.controller.handler.JtIocHandler;
import com.jeramtough.jtandroid.function.MusicPlayer;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.JtViewPager;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.jtlog3.P;
import com.jeramtough.jtutil.StringUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.socketmessage.action.PerformerCommandActions;
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
import com.jeramtough.niyouji.controller.dialog.SelectPwThemeDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 11718
 *         on 2018  January 21 Sunday 19:59.
 */

public class AudienceLiveTravelnoteHandler extends JtIocHandler
		implements SelectPwThemeDialog.SelectPwthemeListener
{
	private static final int BUSINESS_CODE_PERFORMER_ACTIONS = 2;
	
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
	
	
	private String performerId;
	
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
		
		initResources();
	}
	
	protected void initResources()
	{
		musicPlayer.setRepeated(true);
		
		liveTravelnotePageViews = new ArrayList<>();
		
		audienceBusiness.callPerformerActions(performerId,
				new BusinessCaller(this, BUSINESS_CODE_PERFORMER_ACTIONS));
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
						
						this.selectPage(selectPageCommand);
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
				}
				break;
		}
	}
	
	
	@Override
	public void onSelectedPicAndWordTheme(int position, PicAndWordTheme picAndWordTheme)
	{
		picAndWordTheme.setMainBackground(
				getCurrentAudienceLiveTravelnoteView().getLayoutAudienceLiveTravelnotePage());
		picAndWordTheme.setTextViewOrEditText(
				getCurrentAudienceLiveTravelnoteView().getTextViewTravelnotePageContent());
		picAndWordTheme.setFrame(getCurrentAudienceLiveTravelnoteView().getImageViewFrame());
	}
	
	public void loadTravelnote(Travelnote travelnote)
	{
		List<TravelnotePage> travelnotePages = travelnote.getTravelnotePages();
		P.debug(travelnote.getTravelnotePages().size());
		
		
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
			liveTravelnotePageView.getTextViewTravelnotePageContent()
					.setText(travelnotePage.getTextContent());
		}
		
		ViewsPagerAdapter adapter = new ViewsPagerAdapter(liveTravelnotePageViews);
		//强制更新UI
		adapter.setForceUpdateMode(true);
		viewPagerTravelnotePages.setAdapter(adapter);
		
		textViewAttentionsCount.setText(travelnote.getAttentionsCount() + "");
		
		//模拟选中最后一页
		if (travelnotePages.size() > 0)
		{
			SelectPageCommand selectPageCommand = new SelectPageCommand();
			selectPageCommand.setPosition(travelnotePages.size() - 1);
			selectPage(selectPageCommand);
		}
		
		progressBar.setVisibility(View.INVISIBLE);
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
	
	//***********************************************************
	private void addPage(AddPageCommand addPageCommand)
	{
		AudienceLiveTravelnotePageView liveTravelnotePageView =
				new AudienceLiveTravelnotePageView(getContext());
		
		liveTravelnotePageView.setTravelnotePageType(
				TravelnotePageType.toTravelnotePageType((addPageCommand.getPageType())));
		
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
		
		pauseMusicIf();
	}
	
	private void selectPage(SelectPageCommand selectPageCommand)
	{
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
	}
	
	private void pageSetImage(PageSetImageCommand pageSetImageCommand)
	{
		//暂时先设置一张静态的图片
		pageSetImageCommand.setImageUrl(
				"http://niyouji.oss-cn-shenzhen.aliyuncs.com/images/img_0_172641769.jpg");
		
		liveTravelnotePageViews.get(pageSetImageCommand.getPosition())
				.setResourcePath(pageSetImageCommand.getImageUrl());
		
		//加载图片
		GlideApp.with(getContext()).load(pageSetImageCommand.getImageUrl()).fitCenter()
				.skipMemoryCache(true)
				.into(getCurrentAudienceLiveTravelnoteView().getViewPictureOfPage());
	}
	
	private void pageSetVideo(PageSetVideoCommand pageSetVideoCommand)
	{
		//暂时先设置静态视频
		pageSetVideoCommand.setVideoUrl(
				"http://niyouji.oss-cn-shenzhen.aliyuncs.com/videos/vdo_1_251934020.mp4");
		
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
		
		P.debug(pageSetThemeCommand.getThemePosition());
		
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
		audienceLiveTravelnotePageView.getTextViewTravelnotePageContent().setText(text);
		
	}
	
	private void sentPerformerBarrage(SendPerformerBarrageCommand sendPerformerBarrageCommand)
	{
		TextView textView = new TextView(getContext());
		textView.setBackgroundResource(R.color.colorPrimary);
		textView.setPadding(10, 10, 10, 10);
		textView.setText(sendPerformerBarrageCommand.getContent());
		layoutDanmaku.addViewWithAnimation(textView, DanmakuLayout.ANIMATION_STYLE1);
		
		appraisalAreaView.addAppraisal(sendPerformerBarrageCommand.getNickname(),
				sendPerformerBarrageCommand.getContent(), 2);
	}
	
	private void travelnoteEnd(TravelnoteEndCommand travelnoteEndCommand)
	{
		P.arrive();
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
		if (getCurrentAudienceLiveTravelnoteView().getTravelnotePageType() ==
				TravelnotePageType.VIDEO)
		{
			getCurrentAudienceLiveTravelnoteView().getVideoViewTravelnotePage().stopAndClear();
		}
	}
	
	private void updatePagesCount()
	{
		
		textViewPagesCount
				.setText((getCurrentPosition() + 1) + "/" + (liveTravelnotePageViews.size()));
	}
}
