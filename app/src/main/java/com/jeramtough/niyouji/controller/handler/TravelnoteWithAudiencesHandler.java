package com.jeramtough.niyouji.controller.handler;

import android.app.Activity;
import android.os.Message;
import android.widget.TextView;
import com.jeramtough.heartlayout.HeartLayout;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.controller.handler.JtIocHandler;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.socketmessage.action.AudienceCommandActions;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.AudienceLeaveCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.EnterPerformingRoomCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.LightAttentionCountCommand;
import com.jeramtough.niyouji.bean.socketmessage.command.audience.SendAudienceBarrageCommand;
import com.jeramtough.niyouji.business.PerformingBusiness1;
import com.jeramtough.niyouji.business.PerformingService1;
import com.jeramtough.niyouji.component.ui.AppraisalAreaView;
import com.jeramtough.niyouji.component.ui.DanmakuLayout;

/**
 * @author JeramTough
 *         on 2017  December 20 Wednesday 02:01.
 */

public class TravelnoteWithAudiencesHandler extends JtIocHandler
{
	private static final int BUSINESS_CODE_AUDIENCE_ACTIONS = 4;
	
	private TextView textViewAttentionsCount;
	private TextView textViewAudiencesCount;
	private TimedCloseTextView textViewNotification;
	private AppraisalAreaView appraisalAreaView;
	private DanmakuLayout layoutDanmaku;
	private HeartLayout heartLayout;
	
	@InjectService(service = PerformingService1.class)
	private PerformingBusiness1 performingBusiness1;
	
	public TravelnoteWithAudiencesHandler(Activity activity)
	{
		super(activity);
		
		textViewAttentionsCount = findViewById(R.id.textView_attentions_count);
		textViewAudiencesCount = findViewById(R.id.textView_audiences_count);
		textViewNotification = findViewById(R.id.textView_notification);
		appraisalAreaView = findViewById(R.id.appraisalAreaView);
		layoutDanmaku = findViewById(R.id.layout_danmaku);
		heartLayout = findViewById(R.id.heart_layout);
		
		initResources();
	}
	
	protected void initResources()
	{
		performingBusiness1
				.callAudienceActions(new BusinessCaller(this, BUSINESS_CODE_AUDIENCE_ACTIONS));
		/*//一些模拟操作
		for (int i = 0; i < 5; i++)
		{
			heartLayout.postDelayed(() ->
			{
				addAttention();
			}, i * 2000);
		}*/
	}
	
	@Override
	public void handleMessage(Message message)
	{
		switch (message.what)
		{
			case BUSINESS_CODE_AUDIENCE_ACTIONS:
				int audienceAction = message.getData().getInt("audienceAction");
				switch (audienceAction)
				{
					case AudienceCommandActions.ENTER_PERFORMING_ROOM:
						//其他观众进入直播间时回调到这里
						EnterPerformingRoomCommand enterPerformingRoomCommand =
								(EnterPerformingRoomCommand) message.getData()
										.getSerializable("command");
						audienceEnterPerformingRoom(enterPerformingRoomCommand);
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
						audienceLeavePerformingRoom(audienceLeaveCommand);
						break;
				}
				break;
		}
	}
	
	
	//********************************************************
	private void audienceEnterPerformingRoom(
			EnterPerformingRoomCommand enterPerformingRoomCommand)
	{
		int count = Integer.valueOf(textViewAudiencesCount.getText().toString());
		textViewAudiencesCount.setText((count + 1) + "");
	}
	
	private void audienceLeavePerformingRoom(AudienceLeaveCommand audienceLeaveCommand)
	{
		int audiencesCount = Integer.parseInt(textViewAudiencesCount.getText().toString());
		if (audiencesCount > 0)
		{
			audiencesCount--;
		}
		textViewAudiencesCount.setText(audiencesCount + "");
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
}
