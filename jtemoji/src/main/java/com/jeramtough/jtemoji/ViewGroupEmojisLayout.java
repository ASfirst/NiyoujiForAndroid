package com.jeramtough.jtemoji;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author JeramTough
 *         on 2017  December 21 Thursday 23:46.
 */

public class ViewGroupEmojisLayout
{
	public static final int EACH_COUNT = 18;
	
	private static volatile ViewGroupEmojisLayout viewGroupEmojisLayout;
	
	private Context context;
	private ViewGroup viewGroup;
	
	public ViewGroupEmojisLayout(Context context)
	{
		this.context = context;
	}
	
	public static ViewGroupEmojisLayout getViewGroupEmojisLayout(Context context)
	{
		if (viewGroupEmojisLayout == null)
		{
			synchronized (ViewGroupEmojisLayout.class)
			{
				if (viewGroupEmojisLayout == null)
				{
					viewGroupEmojisLayout = new ViewGroupEmojisLayout(context);
				}
			}
		}
		return viewGroupEmojisLayout;
	}
	
	public ViewGroup getViewGroup()
	{
		if (viewGroup == null)
		{
			viewGroup = (ViewGroup) LayoutInflater.from(context)
					.inflate(R.layout.view_emojis_layout, null);
			initViews();
		}
		return viewGroup;
	}
	
	//**************************************
	private void initViews()
	{
		/*ArrayList<ArrayList<JtEmoji>> list = jtEmojisHandler.getApartJtEmojis(EACH_COUNT);
		
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
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
					LinearLayout.LayoutParams.MATCH_PARENT);
			params.weight = 1;
			textView.setLayoutParams(params);
			linearLayoutSelectEmojisPage.addView(textView);
		}*/
	}
}
