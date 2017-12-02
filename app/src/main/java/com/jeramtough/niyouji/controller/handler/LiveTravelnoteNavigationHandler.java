package com.jeramtough.niyouji.controller.handler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jeramtough.jtandroid.adapter.ViewsPagerAdapter;
import com.jeramtough.jtandroid.controller.handler.JtBaseHandler;
import com.jeramtough.jtandroid.jtlog2.P;
import com.jeramtough.jtandroid.ui.JtViewPager;
import com.jeramtough.jtandroid.util.BitmapUtil;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.ui.travelnote.LiveTravelnotePageType;
import com.jeramtough.niyouji.component.ui.travelnote.LiveTravelnotePageView;
import com.jeramtough.niyouji.controller.activity.PerformingActivity;
import com.jeramtough.niyouji.controller.activity.TakePhotoActivity;
import com.jeramtough.niyouji.controller.activity.VideoActivity;

import java.util.ArrayList;

/**
 * @author 11718
 *         on 2017  November 30 Thursday 14:45.
 */

public class LiveTravelnoteNavigationHandler extends JtBaseHandler
		implements ViewPager.OnPageChangeListener
{
	public final static int ACTIVATE_IMAGE_ACTION = 0X1;
	public final static int ACTIVATE_VIDEO_ACTION = 0X3;
	public final static int DELETE_ACTION = 0X2;
	public final static int TAKE_PHOTO_ACTION = 0X4;
	public final static int VIDEO_ACTION = 0X5;
	
	private JtViewPager viewPagerTravelnotePages;
	private TextView textViewAttentionsCount;
	private TextView textViewPagesCount;
	private TextView textViewAudiencesCount;
	private LinearLayout layoutShutdownForLive;
	private ProgressBar progressBarWaitTakephotoOrVideo;
	
	private ArrayList<LiveTravelnotePageView> liveTravelnotePageViews;
	private LiveTravelnotePageView lastLiveTravelnotePageView;
	
	public LiveTravelnoteNavigationHandler(Activity activity)
	{
		super(activity);
		viewPagerTravelnotePages = findViewById(R.id.viewPager_travelnote_pages);
		textViewAttentionsCount = findViewById(R.id.textView_attentions_count);
		textViewPagesCount = findViewById(R.id.textView_pages_count);
		textViewAudiencesCount = findViewById(R.id.textView_audiences_count);
		layoutShutdownForLive = findViewById(R.id.layout_shutdown_for_live);
		progressBarWaitTakephotoOrVideo =
				findViewById(R.id.progressBar_wait_takephoto_or_video);
		
		progressBarWaitTakephotoOrVideo.setVisibility(View.INVISIBLE);
		
		viewPagerTravelnotePages.addOnPageChangeListener(this);
		
		this.initResources();
	}
	
	protected void initResources()
	{
		liveTravelnotePageViews = new ArrayList<>();
		
		this.addPageViewToList();
		
		ViewsPagerAdapter adapter = new ViewsPagerAdapter(liveTravelnotePageViews);
		viewPagerTravelnotePages.setAdapter(adapter);
		
		this.recycleLastPage();
	}
	
	@Override
	public void handleMessage(Message msg)
	{
		switch (msg.what)
		{
			case ACTIVATE_IMAGE_ACTION:
				whenActivateNewPage();
				gotoTakephoto();
				break;
			
			case ACTIVATE_VIDEO_ACTION:
				whenActivateNewPage();
				IntentUtil.toTheOtherActivity(getActivity(), VideoActivity.class,
						PerformingActivity.VIDEO_REQUEST_CODE);
				break;
			
			case DELETE_ACTION:
				if (viewPagerTravelnotePages.getCurrentItem() < liveTravelnotePageViews.size())
				{
					liveTravelnotePageViews.remove(viewPagerTravelnotePages.getCurrentItem());
					this.updateViewPagerUI(true);
					this.updatePagesCount();
					this.resetCurrentPage();
				}
				break;
			
			case TAKE_PHOTO_ACTION:
				gotoTakephoto();
				break;
		}
	}
	
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{
	
	}
	
	@Override
	public void onPageSelected(int position)
	{
		//如果是最后一页
		if (position == liveTravelnotePageViews.size() - 1)
		{
			layoutShutdownForLive.setVisibility(View.VISIBLE);
		}
		else
		{
			//让结束按钮消失
			if (layoutShutdownForLive.getVisibility() == View.VISIBLE)
			{
				layoutShutdownForLive.setVisibility(View.INVISIBLE);
			}
			
			resetCurrentPage();
		}
		
		//回收上个Page的内存
		recycleLastPage();
		
		updatePagesCount();
		
	}
	
	@Override
	public void onPageScrollStateChanged(int state)
	{
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
				
				//what do you want to write
				liveTravelnotePageView.getLivePicandwordPage().reminderWriting();
				
			}
		}
	}
	
	//************************************
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
			}
		}
		lastLiveTravelnotePageView =
				liveTravelnotePageViews.get(viewPagerTravelnotePages.getCurrentItem());
	}
	
	private void resetCurrentPage()
	{
		LiveTravelnotePageView liveTravelnotePageView =
				liveTravelnotePageViews.get(viewPagerTravelnotePages.getCurrentItem());
		
		if (liveTravelnotePageView.getLiveTravelnotePageType() ==
				LiveTravelnotePageType.PICANDWORD)
		{
			String resourcePath = liveTravelnotePageView.getResourcePath();
			
			if (resourcePath != null)
			{
				P.verbose("Reset resource of image of the travelnote page.");
				liveTravelnotePageView.getLivePicandwordPage().getViewPictureOfPage()
						.setImageBitmap(BitmapFactory.decodeFile(resourcePath));
			}
		}
	}
	
	private void gotoTakephoto()
	{
		progressBarWaitTakephotoOrVideo.setVisibility(View.VISIBLE);
		IntentUtil.toTheOtherActivity(getActivity(), TakePhotoActivity.class,
				PerformingActivity.TAKE_PHOTO_REQUEST_CODE);
	}
	
	private void whenActivateNewPage()
	{
		layoutShutdownForLive.setVisibility(View.INVISIBLE);
		
		this.addPageViewToList();
		this.updatePagesCount();
		
		this.updateViewPagerUI(false);
	}
	
	private void addPageViewToList()
	{
		LiveTravelnotePageView page = new LiveTravelnotePageView(getContext(), this);
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
}