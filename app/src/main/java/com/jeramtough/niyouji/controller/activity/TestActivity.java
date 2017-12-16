package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.picandword.PicAndWordTheme;
import com.jeramtough.niyouji.controller.dialog.SelectPwThemeDialog;

/**
 * @author 11718
 */
public class TestActivity extends AppBaseActivity
		implements SelectPwThemeDialog.SelectPwthemeListener
{
	private ViewGroup viewGroup;
	private ImageButton btnDeletePage;
	private ScrollView scrollViewPicandword;
	private AppCompatImageView viewPictureOfPage;
	private AppCompatImageView imageViewFrame;
	private EditText editTravelnotePageContent;
	private LinearLayout layoutWordToolbar;
	private LinearLayout layoutWordFunction1;
	private LinearLayout layoutWordFunction2;
	private Button boomMenuButton;
	
	private SelectPwThemeDialog selectPwThemeDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		viewGroup =
				findViewById(R.id.layout_travelnote_page_picandword_page_main);
		btnDeletePage = findViewById(R.id.btn_delete_page);
		scrollViewPicandword = findViewById(R.id.scrollView_picandword);
		viewPictureOfPage = findViewById(R.id.view_picture_of_page);
		editTravelnotePageContent = findViewById(R.id.edit_travelnote_page_content);
		layoutWordToolbar = findViewById(R.id.layout_word_toolbar);
		imageViewFrame = findViewById(R.id.imageView_frame);
		layoutWordFunction1 = findViewById(R.id.layout_word_function1);
		layoutWordFunction2 = findViewById(R.id.layout_word_function2);
		boomMenuButton = findViewById(R.id.boomMenuButton);
		
		boomMenuButton.setOnClickListener(this);
		
		selectPwThemeDialog = new SelectPwThemeDialog(this);
		selectPwThemeDialog.setSelectPwthemeListener(this);
		selectPwThemeDialog.selectTheme(0);
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.boomMenuButton:
				selectPwThemeDialog.show();
				break;
		}
	}
	
	@Override
	public void onSelectedPicAndWordTheme(int position,PicAndWordTheme picAndWordTheme)
	{
		picAndWordTheme.setDeleteButton(btnDeletePage);
		picAndWordTheme.setMainBackground(viewGroup);
		picAndWordTheme.setFunctionButton(layoutWordFunction1);
		picAndWordTheme.setFunctionButton(layoutWordFunction2);
		picAndWordTheme.setTextViewOrEditText(editTravelnotePageContent);
		picAndWordTheme.setFrame(imageViewFrame);
	}
	
	
}
