package com.jeramtough.niyouji.component.picandword;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PicAndWordThemeImpl implements PicAndWordTheme {

	private Context context;
	private PwResourceCache pwResourceCache;

	public PicAndWordThemeImpl(Context context, PwResourceCache pwResourceCache) {
		super();
		this.context = context;
		this.pwResourceCache = pwResourceCache;
	}

	@Override
	public void setMainBackground(ViewGroup viewGroup) {
		viewGroup.setBackground(new BitmapDrawable(context.getResources(),
				pwResourceCache.getMainBackgroundBitmap()));
	}

	@Override
	public void setFrame(ImageView imageView) {
		imageView.setImageBitmap(pwResourceCache.getFrameBitmap());
	}

	@Override
	public void setDeleteButton(ImageButton imageButton) {
		imageButton.setImageBitmap(pwResourceCache.getDeleteBitmap());
	}

	@Override
	public void setFunctionButton(View view) {

		TextView textView = view.findViewWithTag("TextView");
		
		textView.setTextColor(pwResourceCache.getPwResourcePosition()
				.getDefaultFunctionColor());
		view.setBackground(new BitmapDrawable(context.getResources(),
				pwResourceCache.getDefaultFunctionBitmap()));

		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				TextView textView = (TextView) v.findViewWithTag("TextView");
				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					v.setBackground(new BitmapDrawable(context.getResources(),
							pwResourceCache.getPressedFunctionBitmap()));
					textView.setTextColor(pwResourceCache
							.getPwResourcePosition().getPressedFunctionColor());
					break;

				case MotionEvent.ACTION_UP:
					v.setBackground(new BitmapDrawable(context.getResources(),
							pwResourceCache.getDefaultFunctionBitmap()));
					textView.setTextColor(pwResourceCache
							.getPwResourcePosition().getDefaultFunctionColor());
					break;
				}
				return true;
			}
		});
	}

	@Override
	public void setTextViewOrEditText(TextView textView) {
		textView.setTextColor(pwResourceCache.getPwResourcePosition()
				.getFontColor());
	}

}
