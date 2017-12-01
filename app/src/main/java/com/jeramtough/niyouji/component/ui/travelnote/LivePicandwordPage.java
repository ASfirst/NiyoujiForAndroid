package com.jeramtough.niyouji.component.ui.travelnote;

import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.jeramtough.niyouji.R;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

/**
 * @author 11718
 *         on 2017  December 01 Friday 18:36.
 */

public class LivePicandwordPage
{
	private ViewGroup viewGroup;
	private BoomMenuButton boomMenuButton;
	private EditText editTravelnotePageContent;
	private AppCompatImageView viewPictureOfPage;
	
	public LivePicandwordPage(ViewGroup viewGroup)
	{
		this.viewGroup = viewGroup;
		
		boomMenuButton = viewGroup.findViewById(R.id.boomMenuButton);
		editTravelnotePageContent = viewGroup.findViewById(R.id.edit_travelnote_page_content);
		viewPictureOfPage = viewGroup.findViewById(R.id.view_picture_of_page);
		
		editTravelnotePageContent.setVisibility(View.INVISIBLE);
		viewPictureOfPage.setClickable(false);
		
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
	
}
