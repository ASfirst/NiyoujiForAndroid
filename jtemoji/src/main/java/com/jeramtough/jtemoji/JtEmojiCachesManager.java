package com.jeramtough.jtemoji;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JeramTough
 *         on 2017  December 21 Thursday 10:57.
 */
public class JtEmojiCachesManager
{
	private static volatile JtEmojiCachesManager jtEmojiCachesManager;
	
	private HashMap<JtEmoji, Bitmap> jtEmojiBitmaps;
	
	public JtEmojiCachesManager()
	{
		jtEmojiBitmaps = new HashMap<>();
	}
	
	public Bitmap processNewJtEmojiBitmap(Resources resources, JtEmoji jtEmoji, int fontSize)
	{
		
		Bitmap bitmap = jtEmojiBitmaps.get(jtEmoji);
		if (bitmap == null)
		{
			Bitmap bitmapTemp =
					BitmapFactory.decodeResource(resources, jtEmoji.getImageResId());
			bitmap = Bitmap.createScaledBitmap(bitmapTemp, fontSize + 5, fontSize + 5, true);
		}
		return bitmap;
	}
	
	public void clearAllCaches()
	{
		for (Map.Entry<JtEmoji, Bitmap> entry : jtEmojiBitmaps.entrySet())
		{
			entry.getValue().recycle();
		}
		jtEmojiBitmaps.clear();
	}
	
	public static JtEmojiCachesManager getJtEmojiCachesManager()
	{
		if (jtEmojiCachesManager == null)
		{
			synchronized (JtEmojiCachesManager.class)
			{
				if (jtEmojiCachesManager == null)
				{
					jtEmojiCachesManager = new JtEmojiCachesManager();
				}
			}
		}
		return jtEmojiCachesManager;
	}
}
