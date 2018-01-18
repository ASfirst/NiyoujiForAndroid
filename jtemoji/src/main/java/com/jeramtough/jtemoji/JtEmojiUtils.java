package com.jeramtough.jtemoji;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author JeramTough
 *         on 2017  December 21 Thursday 12:26.
 */

public class JtEmojiUtils
{
	public static SpannableString getEmotionContent(final Context context,
			final TextView textView, JtEmojisHandler jtEmojisHandler, String emojiContentStr)
	{
		SpannableString spannableString = new SpannableString(emojiContentStr);
		
		String regexEmotion = "\\[[a-z][0-9]+\\]";
		Pattern patternEmotion = Pattern.compile(regexEmotion);
		Matcher matcherEmotion = patternEmotion.matcher(emojiContentStr);
		
		while (matcherEmotion.find())
		{
			// 获取匹配到的具体字符
			String placeholder = matcherEmotion.group();
			// 匹配字符串的开始位置
			int start = matcherEmotion.start();
			// 利用表情名字获取到对应的图片
			ImageSpan span = new ImageSpan(context,
					JtEmojiCachesManager.getJtEmojiCachesManager()
							.processNewJtEmojiBitmap(context.getResources(),
									jtEmojisHandler.getJtEmojiByPlaceholder(placeholder),
									(int) textView.getTextSize()));
			spannableString.setSpan(span, start, start + placeholder.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return spannableString;
	}

}
