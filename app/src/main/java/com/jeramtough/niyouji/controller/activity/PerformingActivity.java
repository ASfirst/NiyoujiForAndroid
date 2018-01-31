package com.jeramtough.niyouji.controller.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.socketmessage.action.PerformerCommandActions;
import com.jeramtough.niyouji.bean.socketmessage.command.performer.PerformerRebackCommand;
import com.jeramtough.niyouji.business.PerformingBusiness1;
import com.jeramtough.niyouji.business.PerformingService1;
import com.jeramtough.niyouji.component.travelnote.LiveTravelnoteEventsCaller;
import com.jeramtough.niyouji.component.travelnote.LiveTravelnotePageView;
import com.jeramtough.niyouji.controller.handler.PerformerLiveTravelnoteHandler;
import com.jeramtough.niyouji.controller.handler.TravelnoteWithAudiencesHandler;

/**
 * @author 11718
 */
public class PerformingActivity extends AppBaseActivity implements LiveTravelnoteEventsCaller
{
	public static final int TAKE_PHOTO_REQUEST_CODE = 0X1;
	public static final int VIDEO_REQUEST_CODE = 0X2;
	
	private static final int BUSINESS_CODE_WHEN_PERFORMER_LEAVE = 0X3;
	private static final int BUSINESS_CODE_WHEN_PERFORMER_REBACK = 0X4;
	
	private TimedCloseTextView timedCloseTextView;
	private TextView textViewAttentionsCount;
	private TextView textViewAudiencesCount;
	private Button buttonPing;
	
	private PerformerLiveTravelnoteHandler performerLiveTravelnoteHandler;
	private TravelnoteWithAudiencesHandler travelnoteWithAudiencesHandler;
	
	@InjectService(service = PerformingService1.class)
	private PerformingBusiness1 performingBusiness1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_performing);
		
		timedCloseTextView = findViewById(R.id.textView_notification);
		textViewAttentionsCount = findViewById(R.id.textView_attentions_count);
		textViewAudiencesCount = findViewById(R.id.textView_audiences_count);
		buttonPing = findViewById(R.id.button_ping);
		
		performerLiveTravelnoteHandler = new PerformerLiveTravelnoteHandler(this);
		travelnoteWithAudiencesHandler = new TravelnoteWithAudiencesHandler(this);
		
		performerLiveTravelnoteHandler.setLiveTravelnoteEventsCaller(this);
		
		buttonPing.setOnClickListener(this);
		
		initResources();
	}
	
	protected void initResources()
	{
		performingBusiness1.whenPerformerLeave(
				new BusinessCaller(getActivityHandler(), BUSINESS_CODE_WHEN_PERFORMER_LEAVE));
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == TAKE_PHOTO_REQUEST_CODE &&
				resultCode == TakePhotoActivity.TAKE_PHOTO_RESULT_CODE)
		{
			String path = data.getStringExtra(TakePhotoActivity.PHOTO_PATH_NAME);
			performerLiveTravelnoteHandler.setPageResourcePath(path);
			
		}
		else if (requestCode == VIDEO_REQUEST_CODE &&
				resultCode == VideoActivity.VIDEO_RESULT_CODE)
		{
			String path = data.getStringExtra(VideoActivity.VIDEO_PATH_NAME);
			performerLiveTravelnoteHandler.setPageResourcePath(path);
		}
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		performerLiveTravelnoteHandler.onResume();
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		performerLiveTravelnoteHandler.onStop();
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		performerLiveTravelnoteHandler.onDestroy();
	}
	
	@Override
	public void onBackPressed()
	{
		performerLiveTravelnoteHandler.shutdownForLiveByUseingDialog();
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.button_ping:
				performingBusiness1.pingTest(this);
				break;
		}
	}
	
	@Override
	public void handleActivityMessage(Message message)
	{
		switch (message.what)
		{
			case BUSINESS_CODE_WHEN_PERFORMER_LEAVE:
				popupReconnectDialog();
				break;
			
			case BUSINESS_CODE_WHEN_PERFORMER_REBACK:
				boolean isSuccessful =
						message.getData().getBoolean(BusinessCaller.IS_SUCCESSFUL);
				if (isSuccessful)
				{
					int performerActions = message.getData().getInt("performerActions");
					switch (performerActions)
					{
						case PerformerCommandActions.PERFORMER_REBACK:
							PerformerRebackCommand performerRebackCommand =
									(PerformerRebackCommand) message.getData()
											.getSerializable("command");
							
							textViewAudiencesCount
									.setText(performerRebackCommand.getAudiencesCount() + "");
							textViewAttentionsCount
									.setText(performerRebackCommand.getAttentionsCount() + "");
							
							timedCloseTextView.setNiceMessage("重连成功！");
							timedCloseTextView.closeDelayed(3000);
							break;
					}
				}
				else
				{
					popupReconnectDialog();
				}
				break;
		}
	}
	
	@Override
	public void onTravelnoteSelectedPage(int position)
	{
		performingBusiness1.spreadTravelnoteSelectedPage(position);
	}
	
	@Override
	public void onTravelnoteAddedPage(LiveTravelnotePageView liveTravelnotePageView)
	{
		P.arrive();
		performingBusiness1.spreadTravelnoteAddedPage(liveTravelnotePageView);
	}
	
	@Override
	public void onTravelnoteDeletedPage(int position)
	{
		performingBusiness1.spreadTravelnoteDeletedPage(position);
	}
	
	@Override
	public void onPageSetPicture(int position, String imageUrl)
	{
		performingBusiness1.spreadPageSetPicture(position, imageUrl);
	}
	
	@Override
	public void onPageSetVideo(int position, String videoUrl)
	{
		performingBusiness1.spreadPageSetVideo(position, videoUrl);
	}
	
	@Override
	public void onPageContentChanged(int position, boolean isAdded, String words, int start)
	{
		performingBusiness1.spreadPageContentChanged(position, isAdded, words, start);
	}
	
	@Override
	public void onPageSetTheme(int position, int themePosition)
	{
		performingBusiness1.spreadPageSetTheme(position, themePosition);
	}
	
	@Override
	public void onPageSetBackgroundMusic(int position, String musicPath)
	{
		performingBusiness1.spreadPageSetBackgroundMusic(position, musicPath);
	}
	
	@Override
	public void onTravelnoteSentPerformerBarrage(int position, String barrageContent)
	{
		performingBusiness1.spreadTravelnoteSentPerformerBarrage(position, barrageContent);
	}
	
	@Override
	public void onTravelnoteEnd()
	{
		performingBusiness1.spreadTravelnoteEnd();
	}
	
	//*************************************
	private void popupReconnectDialog()
	{
		AlertDialog dialog = new AlertDialog.Builder(this).setMessage("与服务器连接失败," + "点击确定进行重连")
				.setPositiveButton("确定", (dialog1, which) ->
				{
					performingBusiness1.performerReback(
							new BusinessCaller(getActivityHandler(),
									BUSINESS_CODE_WHEN_PERFORMER_REBACK));
					
					timedCloseTextView.setPrimaryMessage("等待重连中...");
					timedCloseTextView.visible();
					
				}).setNegativeButton("取消", (dialog12, which) ->
				{
					PerformingActivity.this.finish();
				}).create();
		dialog.show();
	}
}
