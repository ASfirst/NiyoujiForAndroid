package com.jeramtough.niyouji.controller.handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.jeramtough.jtandroid.adapter.ViewsPagerAdapter;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.controller.handler.JtIocHandler;
import com.jeramtough.jtandroid.function.MusicPlayer;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.JtViewPager;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.jtandroid.util.BitmapUtil;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.jtemoji.JtEmojiCachesManager;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.business.PerformingBusiness;
import com.jeramtough.niyouji.business.PerformingService;
import com.jeramtough.niyouji.component.ali.camera.CameraMusic;
import com.jeramtough.niyouji.component.ali.camera.MusicsHandler;
import com.jeramtough.niyouji.component.travelnote.LiveTravelnoteEventsCaller;
import com.jeramtough.niyouji.component.travelnote.LiveTravelnotePageView;
import com.jeramtough.niyouji.component.travelnote.ProcessNameOfCloud;
import com.jeramtough.niyouji.component.travelnote.TravelnotePageType;
import com.jeramtough.niyouji.component.ui.AppraisalAreaView;
import com.jeramtough.niyouji.component.ui.DanmakuLayout;
import com.jeramtough.niyouji.component.ui.UploadTestView;
import com.jeramtough.niyouji.controller.activity.PerformingActivity;
import com.jeramtough.niyouji.controller.activity.TakePhotoActivity;
import com.jeramtough.niyouji.controller.activity.VideoActivity;
import com.jeramtough.niyouji.controller.dialog.SelectMusicDialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 11718
 *         on 2017  November 30 Thursday 14:45.
 */

public class PerformerLiveTravelnoteHandler extends LiveTravelnoteHandler
		implements ViewPager.OnPageChangeListener, SelectMusicDialog.SelectMusicListener,
		View.OnTouchListener
{
	public final static int ACTIVATE_IMAGE_ACTION = 0X1;
	public final static int ACTIVATE_VIDEO_ACTION = 0X3;
	public final static int DELETE_ACTION = 0X2;
	public final static int TAKE_PHOTO_ACTION = 0X4;
	public final static int VIDEO_ACTION = 0X5;
	public final static int SELECT_MUSIC_ACTION = 0X6;
	public final static int SENT_BARRAGE_ACTION = 0X7;
	public final static int SELECT_PICANDWORD_THEME_ACTION = 0X8;
	public final static int CHANGED_PAGE_TEXT_CONTENT_ACTION = 0X9;
	
	public final static int BUSINESS_CODE_UPDATE_IMAGE_FILE = 0X30;
	public final static int BUSINESS_CODE_UPDATE_VIDEO_FILE = 0X31;
	
	private JtViewPager viewPagerTravelnotePages;
	
	private TextView textViewPagesCount;
	private LinearLayout layoutShutdownForLive;
	private ProgressBar progressBarWaitTakephotoOrVideo;
	private TimedCloseTextView textViewNotification;
	private AppraisalAreaView appraisalAreaView;
	private DanmakuLayout layoutDanmaku;
	private TextView textViewShutdownReminder;
	private ImageButton buttonShutdownForLive;
	private Button buttonBugDelete;
	
	private ArrayList<LiveTravelnotePageView> liveTravelnotePageViews;
	private LiveTravelnotePageView lastLiveTravelnotePageView;
	
	@InjectComponent
	private MusicsHandler musicsHandler;
	@InjectComponent
	private MusicPlayer musicPlayer;
	
	@InjectService(service = PerformingService.class)
	private PerformingBusiness performingBusiness;
	
	private TimerTask timerTaskForShutdown;
	private int countdownShutdown = 3;
	
	private LiveTravelnoteEventsCaller liveTravelnoteEventsCaller;
	
	public PerformerLiveTravelnoteHandler(Activity activity)
	{
		super(activity);
		
		viewPagerTravelnotePages = findViewById(R.id.viewPager_travelnote_pages);
		textViewPagesCount = findViewById(R.id.textView_pages_count);
		layoutShutdownForLive = findViewById(R.id.layout_shutdown_for_live);
		progressBarWaitTakephotoOrVideo =
				findViewById(R.id.progressBar_wait_takephoto_or_video);
		textViewNotification = findViewById(R.id.textView_notification);
		appraisalAreaView = findViewById(R.id.appraisalAreaView);
		layoutDanmaku = findViewById(R.id.layout_danmaku);
		textViewShutdownReminder = findViewById(R.id.textView_shutdown_reminder);
		buttonShutdownForLive = findViewById(R.id.button_shutdown_for_live);
		buttonBugDelete = findViewById(R.id.button_bug_delete);
		
		textViewNotification.setVisibility(View.GONE);
		progressBarWaitTakephotoOrVideo.setVisibility(View.INVISIBLE);
		textViewShutdownReminder.setVisibility(View.INVISIBLE);
		buttonBugDelete.setVisibility(View.GONE);
		
		viewPagerTravelnotePages.addOnPageChangeListener(this);
		buttonShutdownForLive.setOnTouchListener(this);
		
		this.initResources();
	}
	
	protected void initResources()
	{
		liveTravelnotePageViews = new ArrayList<>();
		
		this.addPageViewToList();
		
		ViewsPagerAdapter adapter = new ViewsPagerAdapter(liveTravelnotePageViews);
		viewPagerTravelnotePages.setAdapter(adapter);
		
		this.recycleLastPage();
		
		//一些模拟操作，到时候会删掉的
		for (int i = 0; i < 7; i++)
		{
			int finalI = i;
			appraisalAreaView.postDelayed(() ->
			{
				if (finalI % 2 == 0)
				{
					appraisalAreaView.addAppraisal("JeramTough", finalI + "dfasdfsa", 1);
				}
				else
				{
					appraisalAreaView.addAppraisal("JeramTough", finalI + "dfasdfsa", 2);
				}
			}, 1500 * i);
			
		}
		
	}
	
	@Override
	public void handleMessage(Message msg)
	{
		int position = viewPagerTravelnotePages.getCurrentItem();
		switch (msg.what)
		{
			case ACTIVATE_IMAGE_ACTION:
				textViewNotification.setText("正在打开照相机。。。。。。");
				textViewNotification.closeDelayed(1300);
				whenActivateNewPage();
				gotoTakephoto();
				break;
			
			case ACTIVATE_VIDEO_ACTION:
				textViewNotification.setText("正在打开摄像机。。。。。。");
				textViewNotification.closeDelayed(1300);
				whenActivateNewPage();
				gotoVideo();
				break;
			
			case DELETE_ACTION:
				if (viewPagerTravelnotePages.getCurrentItem() < liveTravelnotePageViews.size())
				{
					this.clearAndRemoveResource();
					
					this.updateViewPagerUI(true);
					this.updatePagesCount();
					this.resetCurrentImgOrVideoOfPage();
					textViewNotification.setText(String.format("删除第%d页成功！", position + 1));
					textViewNotification.closeDelayed(1000);
					
					//回调当删除了一个page时
					onPageSelected(viewPagerTravelnotePages.getCurrentItem());
				}
				break;
			
			case TAKE_PHOTO_ACTION:
				gotoTakephoto();
				break;
			
			case VIDEO_ACTION:
				gotoVideo();
				break;
			case SELECT_MUSIC_ACTION:
				popupChoiceMusicDialog();
				break;
			case SENT_BARRAGE_ACTION:
				String barrageContent = msg.getData().getString("barrageContent");
				TextView textView = new TextView(getContext());
				textView.setBackgroundResource(R.color.colorPrimary);
				textView.setPadding(10, 10, 10, 10);
				textView.setText(barrageContent);
				
				String nicknameOfPerformer = performingBusiness.getNicknameOfPerformer();
				
				layoutDanmaku.addViewWithAnimation(textView, DanmakuLayout.ANIMATION_STYLE1);
				appraisalAreaView.addAppraisal(nicknameOfPerformer, barrageContent, 2);
				
				//回调发送主播弹幕事件
				liveTravelnoteEventsCaller
						.onTravelnoteSentPerformerBarrage(position, barrageContent);
				break;
			case SELECT_PICANDWORD_THEME_ACTION:
				//回调选择游记页主题事件
				int themePosition = msg.getData().getInt("themePosition");
				liveTravelnoteEventsCaller.onPageSetTheme(position, themePosition);
				break;
			case CHANGED_PAGE_TEXT_CONTENT_ACTION:
				//回调当游记页文字内容发生改变
				boolean isAdded = msg.getData().getBoolean("isAdded");
				String words = msg.getData().getString("words");
				int start = msg.getData().getInt("start");
				liveTravelnoteEventsCaller
						.onPageContentChanged(position, isAdded, words, start);
				break;
			
			case BUSINESS_CODE_UPDATE_IMAGE_FILE:
				LiveTravelnotePageView liveTravelnotePageView1 =
						liveTravelnotePageViews.get(position);
				
				boolean hasUploaded = msg.getData().getBoolean("hasUploaded");
				if (!hasUploaded)
				{
					float percent = msg.getData().getFloat("percent");
					liveTravelnotePageView1.getLivePicandwordPage().getUploadTextView()
							.setProcessOfUpdatingImage(percent);
				}
				else
				{
					//设置图文页的图片可以点击
					liveTravelnotePageView1.getLivePicandwordPage().getViewPictureOfPage()
							.setClickable(true);
					
					//设置可以滑动到下一页
					viewPagerTravelnotePages.setScrollble(true);
					
					boolean isSuccessful = msg.getData().getBoolean("isSuccessful");
					if (isSuccessful)
					{
						Toast.makeText(getContext(), "上传图片完成", Toast.LENGTH_SHORT).show();
						
						//回调当上传完成page内容资源
						String imageUrl = ProcessNameOfCloud.processImageFileUrl(
								ProcessNameOfCloud.processImageFileName(performingBusiness.getUserId(),
										liveTravelnotePageView1));
						liveTravelnoteEventsCaller.onPageSetPicture(position, imageUrl);
					}
					else
					{
						String exceptionMessage = msg.getData().getString("exceptionMessage");
						
						whenUploadingFail(exceptionMessage,
								liveTravelnotePageView1.getLivePicandwordPage()
										.getUploadTextView());
					}
				}
				
				
				break;
			
			case BUSINESS_CODE_UPDATE_VIDEO_FILE:
				LiveTravelnotePageView liveTravelnotePageView2 =
						liveTravelnotePageViews.get(position);
				
				boolean hasUploaded2 = msg.getData().getBoolean("hasUploaded");
				if (!hasUploaded2)
				{
					float percent = msg.getData().getFloat("percent");
					liveTravelnotePageView2.getLiveVideoPage().getUploadTextView()
							.setProcessOfUpdatingVideo(percent);
				}
				else
				{
					//设置视频可以点击
					liveTravelnotePageView2.getLiveVideoPage().setTouchable(true);
					
					//设置可以滑动到下一页
					viewPagerTravelnotePages.setScrollble(true);
					
					boolean isSuccessful = msg.getData().getBoolean("isSuccessful");
					
					
					if (isSuccessful)
					{
						Toast.makeText(getContext(), "上传视频完成", Toast.LENGTH_SHORT).show();
						
						//回调当上传完成page内容资源
						String videoUrl = ProcessNameOfCloud.processVideoFileUrl(
								ProcessNameOfCloud.processVideoFileName(performingBusiness.getUserId(),
										liveTravelnotePageView2));
						liveTravelnoteEventsCaller.onPageSetPicture(position, videoUrl);
					}
					else
					{
						String exceptionMessage = msg.getData().getString("exceptionMessage");
						P.debug(exceptionMessage);
						whenUploadingFail(exceptionMessage,
								liveTravelnotePageView2.getLiveVideoPage()
										.getUploadTextView());
					}
				}
				
				break;
		}
	}
	
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{
	
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		int viewId = v.getId();
		switch (viewId)
		{
			case R.id.button_shutdown_for_live:
				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					textViewShutdownReminder.setVisibility(View.VISIBLE);
					
					timerTaskForShutdown = new TimerTask()
					{
						@Override
						public void run()
						{
							countdownShutdown = countdownShutdown - 1;
							if (countdownShutdown == 0)
							{
								shutdownForLive();
							}
							String reminder = "长按结束按钮3秒后结束直播:" + countdownShutdown + "s";
							textViewShutdownReminder.post(() ->
							{
								textViewShutdownReminder.setText(reminder);
							});
						}
					};
					
					new Timer().schedule(timerTaskForShutdown, 1000, 1000);
				}
				else if (event.getAction() == MotionEvent.ACTION_UP ||
						event.getAction() == MotionEvent.ACTION_CANCEL)
				{
					textViewShutdownReminder.setVisibility(View.INVISIBLE);
					timerTaskForShutdown.cancel();
					countdownShutdown = 3;
					String reminder = "长按结束按钮3秒后结束直播:" + countdownShutdown + "s";
					textViewShutdownReminder.setText(reminder);
				}
				break;
		}
		return false;
	}
	
	@Override
	public void onPageSelected(int position)
	{
		pauseMusicIf();
		
		//如果是最后一页
		if (position == liveTravelnotePageViews.size() - 1)
		{
			layoutShutdownForLive.setVisibility(View.VISIBLE);
			//这个按钮为了解决viewpager最后一页还可以删除问题
			buttonBugDelete.setVisibility(View.VISIBLE);
		}
		else//不是最后一页
		{
			//让结束按钮消失和bug按钮消失
			if (layoutShutdownForLive.getVisibility() == View.VISIBLE)
			{
				layoutShutdownForLive.setVisibility(View.INVISIBLE);
			}
			if (buttonBugDelete.getVisibility() == View.VISIBLE)
			{
				buttonBugDelete.setVisibility(View.GONE);
			}
			
			liveTravelnoteEventsCaller.onTravelnoteSelectedPage(position);
			
			resetCurrentImgOrVideoOfPage();
			resetCurrentMusicOfPage();
		}
		
		//回收上个Page的内存
		recycleLastPage();
		
		updatePagesCount();
		
	}
	
	@Override
	public void onPageScrollStateChanged(int state)
	{
	}
	
	@Override
	public void selectMusic(CameraMusic cameraMusic)
	{
		musicPlayer.playMusic(cameraMusic.getPath(), true);
		
		int position = viewPagerTravelnotePages.getCurrentItem();
		LiveTravelnotePageView liveTravelnotePageView = liveTravelnotePageViews.get(position);
		
		if (liveTravelnotePageView.getTravelnotePageType() == TravelnotePageType.PICANDWORD)
		{
			String musicPath = cameraMusic.getPath();
			
			liveTravelnotePageView.getLivePicandwordPage().setMusicPath(musicPath);
			
			//回调当设置了游记页背景音乐
			liveTravelnoteEventsCaller.onPageSetBackgroundMusic(position, musicPath);
		}
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		musicPlayer.end();
		JtEmojiCachesManager.getJtEmojiCachesManager().clearAllCaches();
	}
	
	
	public void setPageResourcePath(String path)
	{
		progressBarWaitTakephotoOrVideo.setVisibility(View.INVISIBLE);
		
		LiveTravelnotePageView liveTravelnotePageView =
				liveTravelnotePageViews.get(viewPagerTravelnotePages.getCurrentItem());
		
		
		if (liveTravelnotePageView.getTravelnotePageType() == TravelnotePageType.PICANDWORD)
		{
			if (path == null)
			{
				//设置图文页的图片可以点击
				liveTravelnotePageView.getLivePicandwordPage().getViewPictureOfPage()
						.setClickable(true);
			}
			else
			{
				//保存下资源路径
				liveTravelnotePageView.setResourcePath(path);
				//加载刚才拍照的图片
				liveTravelnotePageView.getLivePicandwordPage().getViewPictureOfPage()
						.setImageBitmap(BitmapFactory.decodeFile(path));
				
				//让上传进度可见
				liveTravelnotePageView.getLivePicandwordPage().getUploadTextView()
						.setVisibility(View.VISIBLE);
				liveTravelnotePageView.getLivePicandwordPage().getUploadTextView()
						.setText("正在上传图片\n。。。");
				
				//生成云图片文件名
				String ossImageFileName = ProcessNameOfCloud
						.processImageFileName(performingBusiness.getUserId(), liveTravelnotePageView);
				
				//上传图片到云端
				this.performingBusiness.uploadImageFile(getContext(), ossImageFileName, path,
						new BusinessCaller(this, BUSINESS_CODE_UPDATE_IMAGE_FILE));
				
				//设置不可滑动到下一页
				viewPagerTravelnotePages.setScrollble(false);
				
				//焦点到编辑框
				EditText editText = liveTravelnotePageView.getLivePicandwordPage()
						.getEditTravelnotePageContent();
				editText.setVisibility(View.VISIBLE);
				editText.requestFocus();
				
				//文字工具栏可见
				liveTravelnotePageView.getLivePicandwordPage().getLayoutWordToolbar()
						.setVisibility(View.VISIBLE);
				
				//底部悬浮按钮可见
				liveTravelnotePageView.getLivePicandwordPage().getBoomMenuButton()
						.setVisibility(View.VISIBLE);
				
				//what do you want to write
				liveTravelnotePageView.getLivePicandwordPage().reminderWriting();
			}
		}
		else if (liveTravelnotePageView.getTravelnotePageType() == TravelnotePageType.VIDEO)
		{
			if (path == null)
			{
				//设置视频可以点击
				liveTravelnotePageView.getLiveVideoPage().setTouchable(true);
				
				//恢复视频页
				resetCurrentImgOrVideoOfPage();
			}
			else
			{
				//保存下资源路径
				liveTravelnotePageView.setResourcePath(path);
				//加载刚才拍摄的视频
				liveTravelnotePageView.getLiveVideoPage().displayVideo(path);
				
				//让上传进度可见
				liveTravelnotePageView.getLiveVideoPage().getUploadTextView()
						.setVisibility(View.VISIBLE);
				liveTravelnotePageView.getLiveVideoPage().getUploadTextView()
						.setText("正在上传视频\n。。。");
				
				//生成云视频文件名
				String ossVideoFileName = ProcessNameOfCloud
						.processVideoFileName(performingBusiness.getUserId(), liveTravelnotePageView);
				
				//上传视频到云端
				this.performingBusiness.uploadVideoFile(getContext(), ossVideoFileName, path,
						new BusinessCaller(this, BUSINESS_CODE_UPDATE_VIDEO_FILE));
			}
		}
	}
	
	public void shutdownForLiveByUseingDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setMessage("是否想要立刻结束游记直播？").setNegativeButton("取消", (dialog, which) ->
		{
		
		}).setPositiveButton("确定", (dialog, which) ->
		{
			shutdownForLive();
		}).create().show();
	}
	
	public void setLiveTravelnoteEventsCaller(
			LiveTravelnoteEventsCaller liveTravelnoteEventsCaller)
	{
		this.liveTravelnoteEventsCaller = liveTravelnoteEventsCaller;
	}
	
	//************************************
	
	private void popupChoiceMusicDialog()
	{
		SelectMusicDialog selectMusicDialog =
				new SelectMusicDialog(getContext(), musicsHandler.getCameraMusics());
		selectMusicDialog.setSelectMusicListener(this);
		selectMusicDialog.show();
	}
	
	private void clearAndRemoveResource()
	{
		int position = viewPagerTravelnotePages.getCurrentItem();
		
		//回调删除游记页事件
		liveTravelnoteEventsCaller.onTravelnoteDeletedPage(position);
		
		LiveTravelnotePageView liveTravelnotePageView = liveTravelnotePageViews.get(position);
		if (liveTravelnotePageView.getTravelnotePageType() == TravelnotePageType.PICANDWORD)
		{
			AppCompatImageView imageView =
					liveTravelnotePageView.getLivePicandwordPage().getViewPictureOfPage();
			if (imageView != null && imageView.getDrawable() != null)
			{
				//释放图片资源
				BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
				BitmapUtil.releaseDrawableResouce(bitmapDrawable);
				imageView.setImageDrawable(null);
			}
		}
		else if (liveTravelnotePageView.getTravelnotePageType() == TravelnotePageType.VIDEO)
		{
			VideoView videoView =
					liveTravelnotePageView.getLiveVideoPage().getVideoViewTravelnotePage();
			if (videoView.isPlaying())
			{
				videoView.stopPlayback();
				videoView.destroyDrawingCache();
			}
		}
		
		//如果正在播放音乐就暂停
		pauseMusicIf();
		
		liveTravelnotePageViews.remove(position);
	}
	
	private void recycleLastPage()
	{
		if (lastLiveTravelnotePageView != null)
		{
			if (lastLiveTravelnotePageView.getTravelnotePageType() ==
					TravelnotePageType.PICANDWORD)
			{
				AppCompatImageView imageView =
						lastLiveTravelnotePageView.getLivePicandwordPage()
								.getViewPictureOfPage();
				if (imageView != null && imageView.getDrawable() != null)
				{
					//释放图片资源
					BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
					BitmapUtil.releaseDrawableResouce(bitmapDrawable);
					imageView.setImageDrawable(null);
				}
				
				//当初上个PageView的主题资源
				lastLiveTravelnotePageView.getLivePicandwordPage().clearTheme();
			}
			else if (lastLiveTravelnotePageView.getTravelnotePageType() ==
					TravelnotePageType.VIDEO)
			{
				VideoView videoView = lastLiveTravelnotePageView.getLiveVideoPage()
						.getVideoViewTravelnotePage();
				if (videoView.isPlaying())
				{
					videoView.stopPlayback();
					videoView.destroyDrawingCache();
				}
			}
		}
		lastLiveTravelnotePageView =
				liveTravelnotePageViews.get(viewPagerTravelnotePages.getCurrentItem());
	}
	
	private void resetCurrentImgOrVideoOfPage()
	{
		LiveTravelnotePageView liveTravelnotePageView =
				liveTravelnotePageViews.get(viewPagerTravelnotePages.getCurrentItem());
		
		String resourcePath = liveTravelnotePageView.getResourcePath();
		
		if (liveTravelnotePageView.getTravelnotePageType() == TravelnotePageType.PICANDWORD)
		{
			if (resourcePath != null)
			{
				liveTravelnotePageView.getLivePicandwordPage().getViewPictureOfPage()
						.setImageBitmap(BitmapFactory.decodeFile(resourcePath));
			}
			
			//恢复主题效果
			liveTravelnotePageView.getLivePicandwordPage().resetTheme();
		}
		else if ((liveTravelnotePageView.getTravelnotePageType() == TravelnotePageType.VIDEO))
		{
			if (resourcePath != null)
			{
				liveTravelnotePageView.getLiveVideoPage().displayVideo(resourcePath);
			}
		}
	}
	
	private void resetCurrentMusicOfPage()
	{
		LiveTravelnotePageView liveTravelnotePageView =
				liveTravelnotePageViews.get(viewPagerTravelnotePages.getCurrentItem());
		if (liveTravelnotePageView.getTravelnotePageType() == TravelnotePageType.PICANDWORD)
		{
			String musicPath = liveTravelnotePageView.getLivePicandwordPage().getMusicPath();
			if (musicPath != null)
			{
				musicPlayer.playMusic(musicPath, true);
			}
			else
			{
				musicPlayer.getMediaPlayer().pause();
			}
		}
	}
	
	private void pauseMusicIf()
	{
		if (musicPlayer.getMediaPlayer().isPlaying())
		{
			musicPlayer.getMediaPlayer().pause();
		}
	}
	
	private void gotoTakephoto()
	{
		IntentUtil.toTheOtherActivity(getActivity(), TakePhotoActivity.class,
				PerformingActivity.TAKE_PHOTO_REQUEST_CODE);
		
		//设置图文页的图片不可以点击
		liveTravelnotePageViews.get(viewPagerTravelnotePages.getCurrentItem())
				.getLivePicandwordPage().getViewPictureOfPage().setClickable(false);
	}
	
	private void gotoVideo()
	{
		liveTravelnotePageViews.get(viewPagerTravelnotePages.getCurrentItem())
				.getLiveVideoPage().setTouchable(false);
		IntentUtil.toTheOtherActivity(getActivity(), VideoActivity.class,
				PerformingActivity.VIDEO_REQUEST_CODE);
	}
	
	private void whenActivateNewPage()
	{
		progressBarWaitTakephotoOrVideo.setVisibility(View.VISIBLE);
		layoutShutdownForLive.setVisibility(View.INVISIBLE);
		buttonBugDelete.setVisibility(View.GONE);
		
		int position = viewPagerTravelnotePages.getCurrentItem();
		LiveTravelnotePageView pageView = liveTravelnotePageViews.get(position);
		
		//回调添加新的page事件
		liveTravelnoteEventsCaller.onTravelnoteAddedPage(pageView);
		
		//回调选中刚才新添加的page
		liveTravelnoteEventsCaller.onTravelnoteSelectedPage(position);
		
		//添加新的待激活的page到栈堆
		this.addPageViewToList();
		this.updatePagesCount();
		
		this.updateViewPagerUI(false);
	}
	
	private void addPageViewToList()
	{
		LiveTravelnotePageView page =
				new LiveTravelnotePageView(getContext(), this);
		liveTravelnotePageViews.add(page);
	}
	
	private void updateViewPagerUI(boolean isForceUpdateMode)
	{
		if (isForceUpdateMode == true)
		{
			ViewsPagerAdapter adapter =
					(ViewsPagerAdapter) viewPagerTravelnotePages.getAdapter();
			adapter.setForceUpdateMode(true);
			viewPagerTravelnotePages.getAdapter().notifyDataSetChanged();
			adapter.setForceUpdateMode(false);
		}
		else
		{
			viewPagerTravelnotePages.getAdapter().notifyDataSetChanged();
		}
	}
	
	private void updatePagesCount()
	{
		int position = viewPagerTravelnotePages.getCurrentItem();
		if (position == liveTravelnotePageViews.size() - 1)
		{
			textViewPagesCount.setText("-/" + (liveTravelnotePageViews.size() - 1));
		}
		else
		{
			textViewPagesCount
					.setText((position + 1) + "/" + (liveTravelnotePageViews.size() - 1));
		}
	}
	
	private void shutdownForLive()
	{
		if (timerTaskForShutdown != null)
		{
			timerTaskForShutdown.cancel();
		}
		
		liveTravelnoteEventsCaller.onTravelnoteEnd();
		
		getActivity().finish();
	}
	
	private void whenUploadingFail(String exceptionMessage, UploadTestView uploadTestView)
	{
		
		textViewNotification.setErrorMessage(exceptionMessage);
		textViewNotification.closeDelayed(3000);
		
		uploadTestView.setText("上传失败！");
	}
	
}
