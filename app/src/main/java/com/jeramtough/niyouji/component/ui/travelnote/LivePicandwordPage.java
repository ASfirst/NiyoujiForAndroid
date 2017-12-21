package com.jeramtough.niyouji.component.ui.travelnote;

import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatImageView;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.jeramtough.jtandroid.jtlog2.P;
import com.jeramtough.jtemoji.JtEmoji;
import com.jeramtough.jtemoji.JtEmojiUtils;
import com.jeramtough.jtemoji.JtEmojisLayout;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.picandword.PicAndWordTheme;
import com.jeramtough.niyouji.controller.dialog.SelectPwThemeDialog;
import com.jeramtough.niyouji.controller.handler.LiveTravelnoteNavigationHandler;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

/**
 * @author 11718
 *         on 2017  December 01 Friday 18:36.
 */

public class LivePicandwordPage
		implements View.OnClickListener, SelectPwThemeDialog.SelectPwthemeListener,
		JtEmojisLayout.OnClickEmojiListener
{
	private Handler handler;
	private FragmentManager fragmentManager;
	
	private ViewGroup viewGroup;
	private BoomMenuButton boomMenuButton;
	private EditText editTravelnotePageContent;
	private AppCompatImageView viewPictureOfPage;
	private ScrollView scrollViewPicandword;
	private ImageButton btnDeletePage;
	private TextView textViewReminderWriting;
	private LinearLayout layoutWordToolbar;
	private AppCompatImageView imageViewFrame;
	private LinearLayout layoutWordFunction1;
	private LinearLayout layoutWordFunction2;
	private JtEmojisLayout jtEmojisLayout;
	
	
	private String musicPath;
	
	private SelectPwThemeDialog selectPwThemeDialog;
	
	private int currentThemePosition = 0;
	
	
	public LivePicandwordPage(ViewGroup viewGroupPicandwordPage, Handler handler,
			FragmentManager fragmentManager)
	{
		this.viewGroup = viewGroupPicandwordPage;
		this.handler = handler;
		this.fragmentManager = fragmentManager;
		
		boomMenuButton = viewGroup.findViewById(R.id.boomMenuButton);
		editTravelnotePageContent = viewGroup.findViewById(R.id.edit_travelnote_page_content);
		viewPictureOfPage = viewGroup.findViewById(R.id.view_picture_of_page);
		scrollViewPicandword = viewGroup.findViewById(R.id.scrollView_picandword);
		btnDeletePage = viewGroup.findViewById(R.id.btn_delete_page);
		textViewReminderWriting = viewGroup.findViewById(R.id.textView_reminder_writing);
		layoutWordToolbar = viewGroup.findViewById(R.id.layout_word_toolbar);
		imageViewFrame = viewGroup.findViewById(R.id.imageView_frame);
		layoutWordFunction1 = viewGroup.findViewById(R.id.layout_word_function1);
		layoutWordFunction2 = viewGroup.findViewById(R.id.layout_word_function2);
		jtEmojisLayout = viewGroup.findViewById(R.id.jtEmojisLayout);
		
		editTravelnotePageContent.setVisibility(View.GONE);
		textViewReminderWriting.setVisibility(View.GONE);
		layoutWordToolbar.setVisibility(View.GONE);
		boomMenuButton.setVisibility(View.GONE);
		
		viewPictureOfPage.setClickable(false);
		btnDeletePage.setOnClickListener(this);
		viewPictureOfPage.setOnClickListener(this);
		layoutWordFunction1.setOnClickListener(this);
		layoutWordFunction2.setOnClickListener(this);
		jtEmojisLayout.setOnClickEmojiListener(this);
		
		initResources();
	}
	
	protected void initResources()
	{
		selectPwThemeDialog = new SelectPwThemeDialog(viewGroup.getContext());
		selectPwThemeDialog.setSelectPwthemeListener(this);
		selectPwThemeDialog.selectTheme(currentThemePosition);
		
		for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++)
		{
			TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder();
			switch (i)
			{
				case 0:
					builder.normalImageRes(R.drawable.ic_arrow_upward);
					builder.normalColorRes(R.color.menu_color1);
					builder.normalText("回到顶部");
					builder.listener(index ->
					{
						scrollViewPicandword.fullScroll(View.FOCUS_UP);
					});
					break;
				case 1:
					builder.normalImageRes(R.drawable.ic_music);
					builder.normalColorRes(R.color.menu_color2);
					builder.normalText("添加背景音乐");
					builder.listener(index ->
					{
						handler.sendEmptyMessage(
								LiveTravelnoteNavigationHandler.SELECT_MUSIC_ACTION);
					});
					break;
				case 2:
					builder.normalImageRes(R.drawable.ic_send_voice);
					builder.normalColorRes(R.color.menu_color3);
					builder.normalText("发送主播弹幕");
					builder.listener(index ->
					{
					});
					
					break;
				case 3:
					builder.normalImageRes(R.drawable.ic_picandword);
					builder.normalColorRes(R.color.menu_color4);
					builder.normalText("更换主题");
					builder.listener(index ->
					{
						selectPwThemeDialog.show();
					});
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
			case R.id.layout_word_function1:
				jtEmojisLayout.show();
				boomMenuButton.setVisibility(View.GONE);
				scrollViewPicandword.post(() ->
				{
					scrollViewPicandword.fullScroll(View.FOCUS_DOWN);
				});
				break;
			case R.id.layout_word_function2:
				break;
		}
	}
	
	@Override
	public void onSelectedPicAndWordTheme(int position, PicAndWordTheme picAndWordTheme)
	{
		this.currentThemePosition = position;
		
		picAndWordTheme.setDeleteButton(btnDeletePage);
		picAndWordTheme.setMainBackground(viewGroup);
		picAndWordTheme.setFunctionButton(layoutWordFunction1);
		picAndWordTheme.setFunctionButton(layoutWordFunction2);
		picAndWordTheme.setTextViewOrEditText(editTravelnotePageContent);
		picAndWordTheme.setFrame(imageViewFrame);
	}
	
	@Override
	public void onClickEmoji(int position, JtEmoji jtEmoji)
	{
		SpannableString spannableString = JtEmojiUtils
				.getEmotionContent(viewGroup.getContext(), editTravelnotePageContent,
						jtEmojisLayout.getJtEmojisHandler(),
						editTravelnotePageContent.getText().toString() +
								jtEmoji.getPlaceholder());
		
		editTravelnotePageContent.setText(spannableString);
		editTravelnotePageContent.setSelection(editTravelnotePageContent.getText().length());
	}
	
	@Override
	public void onCancelEmojisLayout()
	{
		boomMenuButton.setVisibility(View.VISIBLE);
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
	
	public LinearLayout getLayoutWordToolbar()
	{
		return layoutWordToolbar;
	}
	
	public BoomMenuButton getBoomMenuButton()
	{
		return boomMenuButton;
	}
	
	public String getMusicPath()
	{
		return musicPath;
	}
	
	public void setMusicPath(String musicPath)
	{
		this.musicPath = musicPath;
	}
	
	public void reminderWriting()
	{
		textViewReminderWriting.setVisibility(View.VISIBLE);
		textViewReminderWriting.postDelayed(() ->
		{
			textViewReminderWriting.setVisibility(View.GONE);
		}, 3000);
	}
	
	public void clearTheme()
	{
		btnDeletePage.setImageResource(R.color.transparent);
		viewGroup.setBackgroundResource(R.color.transparent);
		layoutWordFunction1.setBackgroundResource(R.color.transparent);
		layoutWordFunction2.setBackgroundResource(R.color.transparent);
		imageViewFrame.setImageResource(R.color.transparent);
		
		layoutWordFunction1.setOnTouchListener((v, event) -> false);
		layoutWordFunction2.setOnTouchListener((v, event) -> false);
	}
	
	public void resetTheme()
	{
		selectPwThemeDialog.selectTheme(currentThemePosition);
	}
	
	
}
