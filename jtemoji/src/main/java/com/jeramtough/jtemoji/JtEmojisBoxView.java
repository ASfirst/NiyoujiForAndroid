package com.jeramtough.jtemoji;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

/**
 * @author JeramTough
 *         on 2017  December 22 Friday 00:30.
 */

public class JtEmojisBoxView extends FrameLayout
		implements View.OnClickListener, AdapterView.OnItemClickListener
{
	public static final int EACH_COUNT = 18;
	
	private OnClickEmojiListener onClickEmojiListener;
	
	private ViewFlipper viewFlipperShowEmojis;
	private LinearLayout linearLayoutSelectEmojisPage;
	private ImageView imageViewCloseEmojisLayout;
	
	private TextView lastTextView;
	
	private int currentPage = 0;
	
	public JtEmojisBoxView(@NonNull Context context)
	{
		super(context);
		initViews();
		initResources();
	}
	
	public JtEmojisBoxView(@NonNull Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
		initViews();
		initResources();
	}
	
	protected void initViews()
	{
		ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext())
				.inflate(R.layout.view_emojis_box, this, false);
		this.addView(viewGroup);
		
		viewFlipperShowEmojis = this.findViewById(R.id.viewFlipper_show_emojis);
		linearLayoutSelectEmojisPage = this.findViewById(R.id.linearLayout_select_emojis_page);
		imageViewCloseEmojisLayout = this.findViewById(R.id.imageView_close_emojis_layout);
		
		imageViewCloseEmojisLayout.setOnClickListener(this);
	}
	
	
	protected void initResources()
	{
		ArrayList<ArrayList<JtEmoji>> list =
				JtEmojisHandler.getJtEmojisHandler().getApartJtEmojis(EACH_COUNT);
		
		for (int i = 0; i < list.size(); i++)
		{
			JtEmojisAdapter adapter = new JtEmojisAdapter(getContext(), list.get(i));
			
			GridView gridView = new GridView(getContext());
			gridView.setNumColumns(6);
			gridView.setHorizontalSpacing((int) TypedValue
					.applyDimension(TypedValue.COMPLEX_UNIT_PX, 15,
							getContext().getResources().getDisplayMetrics()));
			gridView.setVerticalSpacing((int) TypedValue
					.applyDimension(TypedValue.COMPLEX_UNIT_PX, 3,
							getContext().getResources().getDisplayMetrics()));
			gridView.setAdapter(adapter);
			gridView.setOnItemClickListener(this);
			
			viewFlipperShowEmojis.addView(gridView);
			
			TextView textView = new TextView(getContext());
			textView.setGravity(Gravity.CENTER);
			textView.setTextColor(Color.BLACK);
			textView.setText((i + 1) + "");
			textView.setOnClickListener(this);
			textView.setTextSize(18);
			if (i == 0)
			{
				textView.setTextColor(Color.BLACK);
				lastTextView = textView;
			}
			else
			{
				textView.setTextColor(0xFF949494);
			}
			LinearLayout.LayoutParams params =
					new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
			params.weight = 1;
			textView.setLayoutParams(params);
			linearLayoutSelectEmojisPage.addView(textView);
		}
	}
	
	@Override
	public void onClick(View v)
	{
		if (v instanceof TextView)
		{
			TextView textView = (TextView) v;
			int position = Integer.valueOf(textView.getText().toString());
			currentPage = position - 1;
			viewFlipperShowEmojis.setDisplayedChild(currentPage);
			
			lastTextView.setTextColor(0XFF949494);
			textView.setTextColor(Color.BLACK);
			lastTextView = textView;
		}
		else
		{
			int i = v.getId();
			if (i == R.id.imageView_close_emojis_layout)
			{
				if (onClickEmojiListener != null)
				{
					onClickEmojiListener.onCancelEmojisLayout();
				}
			}
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		if (onClickEmojiListener != null)
		{
			int index = position + currentPage * EACH_COUNT;
			JtEmoji jtEmoji = JtEmojisHandler.getJtEmojisHandler().getJtEmojis().get(index);
			onClickEmojiListener.onClickEmoji(position, jtEmoji);
		}
	}
	
	//gggggggggggggssssssssssssssssssss
	public void setOnClickEmojiListener(OnClickEmojiListener onClickEmojiListener)
	{
		this.onClickEmojiListener = onClickEmojiListener;
	}
}
