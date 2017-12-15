package com.jeramtough.niyouji.component.picandword;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public interface PicAndWordTheme {

	void setMainBackground(ViewGroup viewGroup);

	void setFrame(ImageView imageView);

	void setDeleteButton(ImageButton imageButton);

	void setFunctionButton(View view);

	void setTextViewOrEditText(TextView textView);
}
