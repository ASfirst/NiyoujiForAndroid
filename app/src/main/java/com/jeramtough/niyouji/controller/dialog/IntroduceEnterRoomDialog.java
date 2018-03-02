package com.jeramtough.niyouji.controller.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jeramtough.jtandroid.controller.dialog.NoActionBarDialog;
import com.jeramtough.jtandroid.ui.DontRemindAgainView;
import com.jeramtough.jtandroid.util.MeasureUtil;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.app.GlideApp;

import java.security.acl.Group;
import java.util.IllegalFormatCodePointException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 11718
 *         on 2018  March 01 Thursday 20:36.
 */

public class IntroduceEnterRoomDialog extends NoActionBarDialog implements View.OnClickListener
{
	private AppCompatImageView imageViewTravelnoteCover;
	private TextView textViewTravelnoteTitle;
	private TextView textViewPerformerName;
	private TextView textViewAttentionsCount;
	private LinearLayout viewNoLiveTravelnote;
	private DontRemindAgainView dontRemindAgainView;
	private Button buttonUnderstand;
	private LinearLayout layoutIntroduce;
	private AppCompatImageView imageViewFinger;
	private TextView textViewIntroduce;
	
	private String coverImagePath;
	private String travelnoteTitle;
	private String userNickname;
	
	private OnUnderstandListener onUnderstandListener;
	private Timer timer;
	
	public IntroduceEnterRoomDialog(@NonNull Context context, String coverImagePath,
			String travelnoteTitle, String userNickname)
	{
		super(context);
		this.userNickname = userNickname;
		this.coverImagePath = coverImagePath;
		this.travelnoteTitle = travelnoteTitle;
		this.setContentView(R.layout.dialog_introduce_enter_room);
		
		imageViewTravelnoteCover = findViewById(R.id.imageView_travelnote_cover);
		textViewTravelnoteTitle = findViewById(R.id.textView_travelnote_title);
		textViewPerformerName = findViewById(R.id.textView_performer_name);
		textViewAttentionsCount = findViewById(R.id.textView_attentions_count);
		viewNoLiveTravelnote = findViewById(R.id.view_no_live_travelnote);
		dontRemindAgainView = findViewById(R.id.dontRemindAgainView);
		buttonUnderstand = findViewById(R.id.button_understand);
		layoutIntroduce = findViewById(R.id.layout_introduce);
		imageViewFinger = findViewById(R.id.imageView_finger);
		textViewIntroduce = findViewById(R.id.textView_introduce);
		
		buttonUnderstand.setOnClickListener(this);
		initResources();
	}
	
	protected void initResources()
	{
		timer = new Timer();
		
		GlideApp.with(getContext()).load(coverImagePath).centerCrop().skipMemoryCache(true)
				.into(imageViewTravelnoteCover);
		textViewTravelnoteTitle.setText(travelnoteTitle);
		textViewPerformerName.setText(userNickname);
		
		startFingerAnimation();
	}
	
	@Override
	public void onClick(View v)
	{
		if (timer != null)
		{
			timer.cancel();
		}
		this.cancel();
		if (onUnderstandListener != null)
		{
			onUnderstandListener.onUnderstand();
		}
	}
	
	public void setOnUnderstandListener(OnUnderstandListener onUnderstandListener)
	{
		this.onUnderstandListener = onUnderstandListener;
	}
	
	public boolean isDontRemind()
	{
		return dontRemindAgainView.isDontRemind();
	}
	
	//***********************************
	private void startFingerAnimation()
	{
		TimerTask timerTask = new TimerTask()
		{
			private int a = 0;
			
			@Override
			public void run()
			{
				layoutIntroduce.post(() ->
				{
					if (a == 0)
					{
						layoutIntroduce.setBackgroundColor(0x00000000);
						ViewGroup.LayoutParams layoutParams =
								imageViewFinger.getLayoutParams();
						int value = MeasureUtil.dp2px(getContext(), 100);
						layoutParams.height = value;
						layoutParams.width = value;
						imageViewFinger.setLayoutParams(layoutParams);
						
						a++;
					}
					else
					{
						layoutIntroduce.setBackgroundColor(0x7F000000);
						ViewGroup.LayoutParams layoutParams =
								imageViewFinger.getLayoutParams();
						int value = MeasureUtil.dp2px(getContext(), 150);
						layoutParams.height = value;
						layoutParams.width = value;
						imageViewFinger.setLayoutParams(layoutParams);
						
						a--;
					}
				});
			}
		};
		
		timer.schedule(timerTask, 500, 500);
	}
	
	//{{{{{{{{{{{{{{{}}}}}}}}}}}}}}}}}}}}}}}
	public interface OnUnderstandListener
	{
		void onUnderstand();
	}
	
}
