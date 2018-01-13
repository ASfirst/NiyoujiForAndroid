package com.jeramtough.jtandroid.controller.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.jeramtough.jtandroid.R;

/**
 * @author 11718
 *         on 2018  January 12 Friday 08:43.
 */

public class CourseDialog extends JtIocDialog implements View.OnClickListener
{
	private ImageView imageView;
	private Button understandButton;
	
	public CourseDialog(@NonNull Context context)
	{
		this(context, R.style.NoBackGroundDialog);
		
	}
	
	public CourseDialog(@NonNull Context context, int themeResId)
	{
		super(context, themeResId);
		initViews();
		initResources();
	}
	
	protected void initViews()
	{
		LinearLayout linearLayout = new LinearLayout(this.getContext());
		linearLayout.setLayoutParams(
				new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT));
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		
		imageView = new ImageView(this.getContext());
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
		params.weight = 1;
		imageView.setLayoutParams(params);
		
		LinearLayout linearLayout1 = new LinearLayout(this.getContext());
		linearLayout1.setLayoutParams(
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT));
		linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout1.setGravity(Gravity.CENTER);
		linearLayout1.setPadding(10,15,10,30);
		
		understandButton = new Button(getContext());
		understandButton.setText("明白了");
		understandButton.setOnClickListener(this);
		
		linearLayout1.addView(understandButton);
		linearLayout.addView(imageView);
		linearLayout.addView(linearLayout1);
		
		this.setContentView(linearLayout);
	}
	
	protected void initResources()
	{
		WindowManager.LayoutParams lp=this.getWindow().getAttributes();
		lp.dimAmount=0.3f;
//		lp.alpha=0.5f;
		// 模糊度getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
		this.getWindow().setAttributes(lp);
	}
	
	@Override
	public void onClick(View v)
	{
		if (v==understandButton)
		{
			this.cancel();
		}
	}
	
	public ImageView getImageView()
	{
		return imageView;
	}
}
