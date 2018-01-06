package com.jeramtough.niyouji.controller.handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.jeramtough.jtandroid.adapter.ViewsPagerAdapter;
import com.jeramtough.jtandroid.controller.handler.JtIocHandler;
import com.jeramtough.jtandroid.function.MusicPlayer;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.jtlog2.P;
import com.jeramtough.jtandroid.ui.JtViewPager;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.jtandroid.util.BitmapUtil;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.jtemoji.JtEmojiCachesManager;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.ali.CameraMusic;
import com.jeramtough.niyouji.component.ali.MusicsHandler;
import com.jeramtough.niyouji.component.ui.AppraisalAreaView;
import com.jeramtough.niyouji.component.travelnote.LiveTravelnotePageType;
import com.jeramtough.niyouji.component.travelnote.LiveTravelnotePageView;
import com.jeramtough.niyouji.component.ui.DanmakuLayout;
import com.jeramtough.niyouji.controller.activity.PerformingActivity;
import com.jeramtough.niyouji.controller.activity.TakePhotoActivityApp;
import com.jeramtough.niyouji.controller.activity.VideoActivityApp;
import com.jeramtough.niyouji.controller.dialog.SelectMusicDialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 11718
 *         on 2017  November 30 Thursday 14:45.
 */

public class LiveTravelnoteNavigationHandler extends JtIocHandler
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
	
	private FragmentManager fragmentManager;
	
	private JtViewPager viewPagerTravelnotePages;
	
	private TextView textViewPagesCount;
	private LinearLayout layoutShutdownForLive;
	private ProgressBar progressBarWaitTakephotoOrVideo;
	private TimedCloseTextView textViewNotification;
	private AppraisalAreaView appraisalAreaView;
	private DanmakuLayout layoutDanmaku;
	private TextView textViewShutdownReminder;
	private ImageButton buttonShutdownForLive;
	
	private ArrayList<LiveTravelnotePageView> liveTravelnotePageViews;
	private LiveTravelnotePageView lastLiveTravelnotePageView;
	
	@InjectComponent
	private MusicsHandler musicsHandler;
	@InjectComponent
	private MusicPlayer musicPlayer;
	
	private TimerTask timerTaskForShutdown;
	private int countdownShutdown = 3;
	private boolean hasCounting = false;
	
	public LiveTravelnoteNavigationHandler(Activity activity, FragmentManager fragmentManager)
	{
		super(activity);
		
		this.fragmentManager = fragmentManager;
		
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
		
		textViewNotification.setVisibility(View.GONE);
		progressBarWaitTakephotoOrVideo.setVisibility(View.INVISIBLE);
		textViewShutdownReminder.setVisibility(View.INVISIBLE);
		
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
					
					int position = viewPagerTravelnotePages.getCurrentItem();
					this.updateViewPagerUI(true);
					this.updatePagesCount();
					this.resetCurrentImgOrVideoOfPage();
					textViewNotification.setText(String.format("删除第%d页成功！", position + 1));
					textViewNotification.closeDelayed(1000);
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
				
				layoutDanmaku.addViewWithAnimation(textView, DanmakuLayout.ANIMATION_STYLE1);
				appraisalAreaView.addAppraisal("JeramTough", barrageContent, 2);
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
		}
		else//不是最后一页
		{
			//让结束按钮消失
			if (layoutShutdownForLive.getVisibility() == View.VISIBLE)
			{
				layoutShutdownForLive.setVisibility(View.INVISIBLE);
			}
			
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
		LiveTravelnotePageView liveTravelnotePageView =
				liveTravelnotePageViews.get(viewPagerTravelnotePages.getCurrentItem());
		if (liveTravelnotePageView.getLiveTravelnotePageType() ==
				LiveTravelnotePageType.PICANDWORD)
		{
			liveTravelnotePageView.getLivePicandwordPage().setMusicPath(cameraMusic.getPath());
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
		
		
		if (liveTravelnotePageView.getLiveTravelnotePageType() ==
				LiveTravelnotePageType.PICANDWORD)
		{
			//设置图文页的图片可以点击
			liveTravelnotePageView.getLivePicandwordPage().getViewPictureOfPage()
					.setClickable(true);
			
			if (path != null)
			{
				//保存下资源路径
				liveTravelnotePageView.setResourcePath(path);
				//加载刚才拍照的图片
				liveTravelnotePageView.getLivePicandwordPage().getViewPictureOfPage()
						.setImageBitmap(BitmapFactory.decodeFile(path));
				
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
		else if (liveTravelnotePageView.getLiveTravelnotePageType() ==
				LiveTravelnotePageType.VIDEO)
		{
			//设置视频可以点击
			liveTravelnotePageView.getLiveVideoPage().setTouchable(true);
			if (path != null)
			{
				//保存下资源路径
				liveTravelnotePageView.setResourcePath(path);
				//加载刚才拍摄的视频
				liveTravelnotePageView.getLiveVideoPage().displayVideo(path);
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
		
		LiveTravelnotePageView liveTravelnotePageView = liveTravelnotePageViews.get(position);
		if (liveTravelnotePageView.getLiveTravelnotePageType() ==
				LiveTravelnotePageType.PICANDWORD)
		{
			AppCompatImageView imageView =
					liveTravelnotePageView.getLivePicandwordPage().getViewPictureOfPage();
			if (imageView != null && imageView.getDrawable() != null)
			{
				//释放图片资源
				P.verbose("Recycle resource of image of the travelnote page.");
				BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
				BitmapUtil.releaseDrawableResouce(bitmapDrawable);
				imageView.setImageDrawable(null);
			}
		}
		else if (liveTravelnotePageView.getLiveTravelnotePageType() ==
				LiveTravelnotePageType.VIDEO)
		{
			VideoView videoView =
					liveTravelnotePageView.getLiveVideoPage().getVideoViewTravelnotePage();
			if (videoView.isPlaying())
			{
				P.verbose("Recycle resource of video of the travelnote page.");
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
			if (lastLiveTravelnotePageView.getLiveTravelnotePageType() ==
					LiveTravelnotePageType.PICANDWORD)
			{
				AppCompatImageView imageView =
						lastLiveTravelnotePageView.getLivePicandwordPage()
								.getViewPictureOfPage();
				if (imageView != null && imageView.getDrawable() != null)
				{
					//释放图片资源
					P.verbose("Recycle resource of image of the travelnote page.");
					BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
					BitmapUtil.releaseDrawableResouce(bitmapDrawable);
					imageView.setImageDrawable(null);
				}
				
				//当初上个PageView的主题资源
				lastLiveTravelnotePageView.getLivePicandwordPage().clearTheme();
			}
			else if (lastLiveTravelnotePageView.getLiveTravelnotePageType() ==
					LiveTravelnotePageType.VIDEO)
			{
				VideoView videoView = lastLiveTravelnotePageView.getLiveVideoPage()
						.getVideoViewTravelnotePage();
				if (videoView.isPlaying())
				{
					P.verbose("Recycle resource of video of the travelnote page.");
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
		
		if (liveTravelnotePageView.getLiveTravelnotePageType() ==
				LiveTravelnotePageType.PICANDWORD)
		{
			if (resourcePath != null)
			{
				P.verbose("Reset resource of image of the travelnote page.");
				liveTravelnotePageView.getLivePicandwordPage().getViewPictureOfPage()
						.setImageBitmap(BitmapFactory.decodeFile(resourcePath));
			}
			
			//恢复主题效果
			liveTravelnotePageView.getLivePicandwordPage().resetTheme();
		}
		else if ((liveTravelnotePageView.getLiveTravelnotePageType() ==
				LiveTravelnotePageType.VIDEO))
		{
			if (resourcePath != null)
			{
				P.verbose("Reset resource of video of the travelnote page.");
				liveTravelnotePageView.getLiveVideoPage().displayVideo(resourcePath);
			}
		}
	}
	
	private void resetCurrentMusicOfPage()
	{
		LiveTravelnotePageView liveTravelnotePageView =
				liveTravelnotePageViews.get(viewPagerTravelnotePages.getCurrentItem());
		if (liveTravelnotePageView.getLiveTravelnotePageType() ==
				LiveTravelnotePageType.PICANDWORD)
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
		IntentUtil.toTheOtherActivity(getActivity(), TakePhotoActivityApp.class,
				PerformingActivity.TAKE_PHOTO_REQUEST_CODE);
	}
	
	private void gotoVideo()
	{
		IntentUtil.toTheOtherActivity(getActivity(), VideoActivityApp.class,
				PerformingActivity.VIDEO_REQUEST_CODE);
	}
	
	private void whenActivateNewPage()
	{
		progressBarWaitTakephotoOrVideo.setVisibility(View.VISIBLE);
		layoutShutdownForLive.setVisibility(View.INVISIBLE);
		
		this.addPageViewToList();
		this.updatePagesCount();
		
		this.updateViewPagerUI(false);
	}
	
	private void addPageViewToList()
	{
		LiveTravelnotePageView page =
				new LiveTravelnotePageView(getContext(), this, fragmentManager);
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
		P.arrive();
	}
}
