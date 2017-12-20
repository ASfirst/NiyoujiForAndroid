package com.jeramtough.niyouji.component.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import com.jeramtough.jtandroid.animation.AnimationFactory;

/**
 * @author JeramTough
 *         on 2017  December 20 Wednesday 03:22.
 */

public class DanmakuLayout extends FrameLayout
{
	public static final int ANIMATION_STYLE1 = 1;
	public static final int ANIMATION_STYLE2 = 2;
	
	private int animationDuration = 7000;
	
	private Handler handler;
	
	public DanmakuLayout(@NonNull Context context)
	{
		super(context);
		initResources();
	}
	
	public DanmakuLayout(@NonNull Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
		initResources();
	}
	
	protected void initResources()
	{
		handler = new Handler(Looper.getMainLooper());
	}
	
	public void addViewWithAnimation(View view, int animationStyle)
	{
		addView(view);
		
		AnimationSet animationSet = precessAnimationSet(view);
		switch (animationStyle)
		{
			case ANIMATION_STYLE1:
				animationSet
						.addAnimation(AnimationFactory.getTranslateAnimationFromLeftToRight());
				break;
			case ANIMATION_STYLE2:
				animationSet
						.addAnimation(AnimationFactory.getTranslateAnimationFromBottomToTop());
				animationSet.addAnimation(AnimationFactory.getScaleAnimationFromSmallToLarge());
				break;
			default:
		}
		
		view.startAnimation(animationSet);
	}
	
	//*******************************
	private AnimationSet precessAnimationSet(final View view)
	{
		final AnimationSet animationSet = new AnimationSet(true);
		animationSet.setInterpolator(new DecelerateInterpolator());
		animationSet.setDuration(animationDuration);
		animationSet.setAnimationListener(new Animation.AnimationListener()
		{
			@Override
			public void onAnimationStart(Animation animation)
			{
			
			}
			
			@Override
			public void onAnimationEnd(Animation animation)
			{
				animation.reset();
				handler.post(new Runnable()
				{
					@Override
					public void run()
					{
						DanmakuLayout.this.removeView(view);
					}
				});
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation)
			{
			
			}
		});
		
		
		return animationSet;
	}
}
