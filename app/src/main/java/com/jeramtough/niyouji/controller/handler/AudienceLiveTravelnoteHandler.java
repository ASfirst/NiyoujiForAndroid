package com.jeramtough.niyouji.controller.handler;

import android.app.Activity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jeramtough.heartlayout.HeartLayout;
import com.jeramtough.jtandroid.adapter.ViewsPagerAdapter;
import com.jeramtough.jtandroid.controller.handler.JtIocHandler;
import com.jeramtough.jtandroid.function.MusicPlayer;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ui.JtViewPager;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.travelnote.Travelnote;
import com.jeramtough.niyouji.component.ali.camera.MusicsHandler;
import com.jeramtough.niyouji.component.travelnote.LiveTravelnotePageView;
import com.jeramtough.niyouji.component.ui.AppraisalAreaView;
import com.jeramtough.niyouji.component.ui.DanmakuLayout;

import java.util.ArrayList;

/**
 * @author 11718
 *         on 2018  January 21 Sunday 19:59.
 */

public class AudienceLiveTravelnoteHandler extends JtIocHandler
{
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
	
	
	private ArrayList<LiveTravelnotePageView> liveTravelnotePageViews;
	private LiveTravelnotePageView lastLiveTravelnotePageView;
	
	@InjectComponent
	private MusicsHandler musicsHandler;
	@InjectComponent
	private MusicPlayer musicPlayer;
	
	public AudienceLiveTravelnoteHandler(Activity activity)
	{
		super(activity);
		
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
		liveTravelnotePageViews = new ArrayList<>();
		
		ViewsPagerAdapter adapter = new ViewsPagerAdapter(liveTravelnotePageViews);
		viewPagerTravelnotePages.setAdapter(adapter);
	}
	
	public void loadTravelnote(Travelnote travelnote)
	{
	
	}
}
