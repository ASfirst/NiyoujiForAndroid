package com.jeramtough.niyouji.component.ui.travelnote;

import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.controller.handler.LiveTravelnoteNavigationHandler;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

/**
 * @author 11718
 *         on 2017  December 01 Friday 18:36.
 */

public class LivePicandwordPage implements View.OnClickListener
{
	private Handler handler;
	
	private ViewGroup viewGroup;
	private BoomMenuButton boomMenuButton;
	private EditText editTravelnotePageContent;
	private AppCompatImageView viewPictureOfPage;
	private ScrollView scrollViewPicandword;
	private ImageButton btnDeletePage;
	private TextView textViewReminderWriting;
	
	
	public LivePicandwordPage(ViewGroup viewGroupPicandwordPage, Handler handler)
	{
		this.viewGroup = viewGroupPicandwordPage;
		this.handler = handler;
		
		boomMenuButton = viewGroup.findViewById(R.id.boomMenuButton);
		editTravelnotePageContent = viewGroup.findViewById(R.id.edit_travelnote_page_content);
		viewPictureOfPage = viewGroup.findViewById(R.id.view_picture_of_page);
		scrollViewPicandword = viewGroup.findViewById(R.id.scrollView_picandword);
		btnDeletePage = viewGroup.findViewById(R.id.btn_delete_page);
		textViewReminderWriting = viewGroup.findViewById(R.id.textView_reminder_writing);
		
		editTravelnotePageContent.setVisibility(View.GONE);
		textViewReminderWriting.setVisibility(View.GONE);
		
		viewPictureOfPage.setClickable(false);
		
		btnDeletePage.setOnClickListener(this);
		viewPictureOfPage.setOnClickListener(this);
		
		initResources();
	}
	
	protected void initResources()
	{
		for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++)
		{
			TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder();
			switch (i)
			{
				case 0:
					builder.normalImageRes(R.drawable.ic_picandword);
					builder.normalColorRes(R.color.menu_color1);
					builder.normalText("添加文字描述");
					break;
				case 1:
					builder.normalImageRes(R.drawable.ic_music);
					builder.normalColorRes(R.color.menu_color2);
					builder.normalText("添加背景音乐");
					break;
				case 2:
					builder.normalImageRes(R.drawable.ic_beautiful);
					builder.normalColorRes(R.color.menu_color3);
					builder.normalText("更换主题");
					break;
			}
			builder.imagePadding(new Rect(30, 30, 30, 30));
			builder.textGravity(Gravity.CENTER | Gravity.BOTTOM);
			boomMenuButton.addBuilder(builder);
		}
	}
	
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.view_picture_of_page:
				handler.sendEmptyMessage(LiveTravelnoteNavigationHandler.TAKE_PHOTO_ACTION);
				break;
			case R.id.btn_delete_page:
				handler.sendEmptyMessage(LiveTravelnoteNavigationHandler.DELETE_ACTION);
				break;
		}
	}
	
	public EditText getEditTravelnotePageContent()
	{
		return editTravelnotePageContent;
	}
	
	public AppCompatImageView getViewPictureOfPage()
	{
		return viewPictureOfPage;
	}
	
	public ScrollView getScrollViewPicandword()
	{
		return scrollViewPicandword;
	}
	
	public ImageButton getBtnDeletePage()
	{
		return btnDeletePage;
	}
	
	public void reminderWriting()
	{
		textViewReminderWriting.setVisibility(View.VISIBLE);
		textViewReminderWriting.postDelayed(() ->
		{
			textViewReminderWriting.setVisibility(View.GONE);
		}, 3000);
	}
}
