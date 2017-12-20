package com.jeramtough.jtandroid.animation;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * @author JeramTough
 *         on 2017  December 20 Wednesday 14:22.
 */

public class AnimationFactory
{
	
	public static TranslateAnimation getTranslateAnimationFromLeftToRight()
	{
		TranslateAnimation translateAnimation =
				new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f,
						Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0f,
						Animation.RELATIVE_TO_PARENT, 0.0f);
		return translateAnimation;
	}
	
	public static TranslateAnimation getTranslateAnimationFromTopToBottom()
	{
		TranslateAnimation translateAnimation =
				new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
						Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, -1f,
						Animation.RELATIVE_TO_PARENT, 1f);
		return translateAnimation;
	}
	
	public static TranslateAnimation getTranslateAnimationFromBottomToTop()
	{
		TranslateAnimation translateAnimation =
				new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
						Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 1f,
						Animation.RELATIVE_TO_PARENT, -1f);
		return translateAnimation;
	}
	
	public static ScaleAnimation getScaleAnimationFromSmallToLarge()
	{
		ScaleAnimation scaleAnimation =
				new ScaleAnimation(0.0f, 3f, 0.0f, 3f, Animation.RELATIVE_TO_PARENT,
						0.5f,
						Animation.RELATIVE_TO_PARENT, 1f);
		return scaleAnimation;
	}
}
