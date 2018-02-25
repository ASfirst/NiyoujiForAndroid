package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.jeramtough.heartlayout.HeartLayout;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.JtViewPager;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.travelnote.LiveTravelnoteCover;
import com.jeramtough.niyouji.bean.travelnote.Travelnote;
import com.jeramtough.niyouji.business.AudienceBusiness;
import com.jeramtough.niyouji.business.AudienceService;
import com.jeramtough.niyouji.component.ui.AppraisalAreaView;
import com.jeramtough.niyouji.component.ui.DanmakuLayout;
import com.jeramtough.niyouji.controller.dialog.EditBarrageDialog;
import com.jeramtough.niyouji.controller.handler.AudienceLiveTravelnoteHandler;
import com.jeramtough.niyouji.controller.handler.PerformerLiveTravelnoteHandler;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

/**
 * @author 11718
 */
public class AudienceActivity extends AppBaseActivity
{
	private static final int BUSINESS_CODE_ENTER_PERFORMING_ROOM = 0;
	private static final int BUSINESS_CODE_OBTAINING_LIVE_TRAVELNOTE = 1;
	
	private JtViewPager viewPagerTravelnotePages;
	private TextView textViewAttentionsCount;
	private TextView textViewPagesCount;
	private TextView textViewAudiencesCount;
	private TextView textViewPerformerNickname;
	private Button buttonBugDelete;
	private TimedCloseTextView timedCloseTextViewShowMessage;
	private AppraisalAreaView appraisalAreaView;
	private DanmakuLayout layoutDanmaku;
	private ProgressBar progressBar;
	private HeartLayout heartLayout;
	
	@InjectService(service = AudienceService.class)
	private AudienceBusiness audienceBusiness;
	
	private AudienceLiveTravelnoteHandler audienceLiveTravelnoteHandler;
	
	private LiveTravelnoteCover liveTravelnoteCover;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audience);
		
		viewPagerTravelnotePages = findViewById(R.id.viewPager_travelnote_pages);
		textViewAttentionsCount = findViewById(R.id.textView_attentions_count);
		textViewPagesCount = findViewById(R.id.textView_pages_count);
		textViewPerformerNickname = findViewById(R.id.textView_performer_nickname);
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
		liveTravelnoteCover =
				(LiveTravelnoteCover) getIntent().getSerializableExtra("liveTravelnoteCover");
		
		//观众开始进入直播间
		audienceBusiness.enterPerformingRoom(liveTravelnoteCover.getPerformerId(),
				new BusinessCaller(getActivityHandler(), BUSINESS_CODE_ENTER_PERFORMING_ROOM),
				new BusinessCaller(getActivityHandler(),
						BUSINESS_CODE_OBTAINING_LIVE_TRAVELNOTE));
		
		timedCloseTextViewShowMessage.setPrimaryMessage("正在连接服务器...");
		timedCloseTextViewShowMessage.visible();
		
		
		audienceLiveTravelnoteHandler =
				new AudienceLiveTravelnoteHandler(this, liveTravelnoteCover.getPerformerId());
		
		textViewPerformerNickname.setText(liveTravelnoteCover.getPerformerNickname());
		textViewAudiencesCount.setText(liveTravelnoteCover.getAudiencesCount() + "");
		
	}
	
	@Override
	public void handleActivityMessage(Message message)
	{
		switch (message.what)
		{
			case BUSINESS_CODE_ENTER_PERFORMING_ROOM:
				boolean connectSuccessfully =
						message.getData().getBoolean("connectSuccessfully");
				if (connectSuccessfully)
				{
					timedCloseTextViewShowMessage.setNiceMessage("连接服务器成功！获取游记资源中...");
					timedCloseTextViewShowMessage.visible();
				}
				else
				{
					timedCloseTextViewShowMessage.setErrorMessage("连接服务器失败!\n请检查网络！");
					timedCloseTextViewShowMessage.closeDelayed(3000);
					progressBar.setVisibility(View.INVISIBLE);
				}
				break;
			
			case BUSINESS_CODE_OBTAINING_LIVE_TRAVELNOTE:
				if (message.getData().getBoolean(BusinessCaller.IS_SUCCESSFUL))
				{
					Travelnote travelnote =
							(Travelnote) message.getData().getSerializable("travelnote");
					timedCloseTextViewShowMessage.setNiceMessage("获取资源成功！");
					
					audienceLiveTravelnoteHandler.loadTravelnote(travelnote);
					
					timedCloseTextViewShowMessage.postDelayed(() ->
					{
						timedCloseTextViewShowMessage.setPrimaryMessage("正在初始化资源...");
					}, 1000);
				}
				else
				{
					timedCloseTextViewShowMessage.setErrorMessage("该直播以结束");
					timedCloseTextViewShowMessage.visible();
					
					timedCloseTextViewShowMessage.postDelayed(() ->
					{
						Toast.makeText(this, "该直播以结束", Toast.LENGTH_SHORT).show();
						AudienceActivity.this.finish();
					}, 2000);
					
				}
				break;
			
			default:
				break;
		}
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		audienceLiveTravelnoteHandler.onDestroy();
	}
	
	@Override
	public void onBackPressed()
	{
		audienceLiveTravelnoteHandler.leaveByUseingDialog();
	}
}
