package com.jeramtough.niyouji.controller.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import cn.sharesdk.onekeyshare.OnekeyShare;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.bean.travelnote.Appraise;
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
	private AppCompatImageButton imageButtonShare;
	private EditText editAppraise;
	private LinearLayout layoutAppraiseAndFavorite;
	private FrameLayout layoutAppraise;
	private TextView textViewAppraiseCount;
	private AppCompatImageView imageButtonFavorite;
	private TextView textViewDone;
	
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
		imageButtonShare = findViewById(R.id.imageButton_share);
		editAppraise = findViewById(R.id.edit_appraise);
		layoutAppraiseAndFavorite = findViewById(R.id.layout_appraise_and_favorite);
		layoutAppraise = findViewById(R.id.layout_appraise);
		textViewAppraiseCount = findViewById(R.id.textView_appraise_count);
		imageButtonFavorite = findViewById(R.id.imageButton_favorite);
		textViewDone = findViewById(R.id.textView_done);
		
		textViewDone.setVisibility(View.GONE);
		
		imageButtonBack.setOnClickListener(this);
		imageButtonShare.setOnClickListener(this);
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
					
					editAppraise.setText("");
					
					editAppraise.clearFocus();
					//让输入框消失
					InputMethodManager imm = (InputMethodManager) this
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					if (imm != null)
					{
						imm.hideSoftInputFromWindow(editAppraise.getWindowToken(), 0);
					}
				}
				else
				{
					Toast.makeText(this, "评价内容不能为空！", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.imageButton_favorite:
				//刷新，到时会删掉
				//				niyoujiWebView.clearCache(true);
				//niyoujiWebView.reload();
				
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
			case R.id.imageButton_share:
				showShare();
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
					
					int appraisesCount =
							Integer.parseInt(textViewAppraiseCount.getText().toString()) + 1;
					textViewAppraiseCount.setText(appraisesCount + "");
					
					Appraise appraise =
							(Appraise) message.getData().getSerializable("appraise");
					niyoujiWebView.addAppraise(appraise);
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
	
	//************************************************
	private void showShare()
	{
		OnekeyShare oks = new OnekeyShare();
		//关闭sso授权
		oks.disableSSOWhenAuthorize();
		
		// title标题，微信、QQ和QQ空间等平台使用
		oks.setTitle(finishedTravelnoteCover.getTravelnoteTitle());
		// titleUrl QQ和QQ空间跳转链接
		String url = niyoujiWebView.getUrl() + "&&isShared=1";
		P.debug(url);
		oks.setTitleUrl(url);
		// text是分享文本，所有平台都需要这个字段
		oks.setText(finishedTravelnoteCover.getFirstTravelnotePage().getTextContent());
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		//		oks.setImagePath();//确保SDcard下面存在此张图片
		oks.setImageUrl(finishedTravelnoteCover.getCoverResourceUrl());
		// url在微信、微博，Facebook等平台中使用
		oks.setUrl(url);
		// comment是我对这条分享的评论，仅在人人网使用
		//oks.setComment("我是测试评论文本");
		// 启动分享GUI
		oks.show(this);
	}
}
