package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.travelnote.FinishedTravelnoteCover;
import com.jeramtough.niyouji.business.FinishedTravelnoteBusiness;
import com.jeramtough.niyouji.business.FinishedTravelnoteService;
import com.jeramtough.niyouji.component.ui.NiyoujiWebView;
import com.jeramtough.niyouji.controller.dialog.GoToLoginDialog;

/**
 * @author 11718
 */
public class FinishedTravelnoteActivity extends AppBaseActivity
		implements View.OnFocusChangeListener, View.OnTouchListener
{
	private final int BUSINESS_CODE_PUBLISH_APPRAISE = 0;
	private final int BUSINESS_CODE_OBTAIN_APPRAISES_COUNT = 1;
	
	private NiyoujiWebView niyoujiWebView;
	private AppCompatImageButton imageButtonBack;
	private EditText editAppraise;
	private FrameLayout layoutAppraise;
	private TextView textViewAppraiseCount;
	private AppCompatImageView imageButtonFavorite;
	private TextView textViewDone;
	private LinearLayout layoutAppraiseAndFavorite;
	
	private FinishedTravelnoteCover finishedTravelnoteCover;
	
	@InjectService(service = FinishedTravelnoteService.class)
	private FinishedTravelnoteBusiness finishedTravelnoteBusiness;
	
	private boolean isFavorite = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finished_travelnote);
		
		niyoujiWebView = findViewById(R.id.webView);
		imageButtonBack = findViewById(R.id.imageButton_back);
		editAppraise = findViewById(R.id.edit_appraise);
		layoutAppraise = findViewById(R.id.layout_appraise);
		textViewAppraiseCount = findViewById(R.id.textView_appraise_count);
		imageButtonFavorite = findViewById(R.id.imageButton_favorite);
		textViewDone = findViewById(R.id.textView_done);
		layoutAppraiseAndFavorite = findViewById(R.id.layout_appraise_and_favorite);
		
		textViewDone.setVisibility(View.GONE);
		
		imageButtonBack.setOnClickListener(this);
		textViewDone.setOnClickListener(this);
		layoutAppraise.setOnClickListener(this);
		imageButtonFavorite.setOnClickListener(this);
		editAppraise.setOnFocusChangeListener(this);
		
		niyoujiWebView.setOnTouchListener(this);
		initResources();
	}
	
	protected void initResources()
	{
		finishedTravelnoteCover = (FinishedTravelnoteCover) getIntent()
				.getSerializableExtra("finishedTravelnoteCover");
		if (finishedTravelnoteCover.getAppraiseCount() > 0)
		{
			textViewAppraiseCount.setText(finishedTravelnoteCover.getAppraiseCount() + "");
			
			//重新从服务器获取评价数
			finishedTravelnoteBusiness
					.obtainAppraisesCount(finishedTravelnoteCover.getTravelnoteId(),
							new BusinessCaller(getActivityHandler(),
									BUSINESS_CODE_OBTAIN_APPRAISES_COUNT));
		}
		else
		{
			textViewAppraiseCount.setVisibility(View.GONE);
		}
		
		niyoujiWebView.loadTravelnoteWebpage(finishedTravelnoteCover.getTravelnoteId());
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.layout_appraise:
				niyoujiWebView.focusToAppraiseArea();
				break;
			
			case R.id.imageButton_back:
				this.finish();
				break;
			case R.id.textView_done:
				String appraiseContent = editAppraise.getText().toString();
				if (appraiseContent.length() > 0)
				{
					finishedTravelnoteBusiness
							.publishAppraise(finishedTravelnoteCover.getTravelnoteId(),
									appraiseContent, new BusinessCaller(getActivityHandler(),
											BUSINESS_CODE_PUBLISH_APPRAISE));
				}
				else
				{
					Toast.makeText(this, "评价内容不能为空！", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.imageButton_favorite:
				//刷新，到时会删掉
				niyoujiWebView.clearCache(true);
				niyoujiWebView
						.loadTravelnoteWebpage(finishedTravelnoteCover.getTravelnoteId());
				
				if (isFavorite)
				{
					imageButtonFavorite.setImageResource(R.drawable.ic_favorite_gray);
				}
				else
				{
					imageButtonFavorite.setImageResource(R.drawable.ic_favorite_red);
				}
				isFavorite = !isFavorite;
				break;
		}
	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus)
	{
		switch (v.getId())
		{
			case R.id.edit_appraise:
				if (hasFocus)
				{
					if (finishedTravelnoteBusiness.userHasLogined())
					{
						textViewDone.setVisibility(View.VISIBLE);
						layoutAppraiseAndFavorite.setVisibility(View.GONE);
					}
					else
					{
						GoToLoginDialog goToLoginDialog = new GoToLoginDialog(this);
						goToLoginDialog.show();
					}
				}
				else
				{
					textViewDone.setVisibility(View.GONE);
					layoutAppraiseAndFavorite.setVisibility(View.VISIBLE);
				}
				break;
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if (editAppraise.hasFocus())
			{
				editAppraise.clearFocus();
			}
		}
		return false;
	}
	
	@Override
	public void handleActivityMessage(Message message)
	{
		switch (message.what)
		{
			case BUSINESS_CODE_PUBLISH_APPRAISE:
				boolean isSuccessful =
						message.getData().getBoolean(BusinessCaller.IS_SUCCESSFUL);
				if (isSuccessful)
				{
					Toast.makeText(this, "发表成功~", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(this, "发表失败！", Toast.LENGTH_SHORT).show();
				}
				break;
			case BUSINESS_CODE_OBTAIN_APPRAISES_COUNT:
				if (message.getData().getBoolean(BusinessCaller.IS_SUCCESSFUL))
				{
					String appraisesCount = message.getData().getString("appraisesCount");
					textViewAppraiseCount.setText(appraisesCount + "");
				}
				break;
		}
	}
}
