package com.jeramtough.jtandroid.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

/**
 * @author 11718
 *         on 2017  November 26 Sunday 14:04.
 */

public class BitmapUtil
{
	public static Bitmap toBitmap(Drawable drawable)
	{
		
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(),
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 :
						Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}
	
	/**
	 * MD，这个方法没有卵用
	 */
	public static byte[] toBytes(Bitmap bitmap)
	{
		int bytes = bitmap.getByteCount();
		
		ByteBuffer buf = ByteBuffer.allocate(bytes);
		bitmap.copyPixelsToBuffer(buf);
		
		byte[] byteArray = buf.array();
		return byteArray;
	}
	
	
	public static Bitmap toBitmap(byte[] bytes)
	{
		Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return bitmap;
	}
	
	public static Drawable toDrawable(Resources resources, byte[] bytes)
	{
		Bitmap bitmap = toBitmap(bytes);
		BitmapDrawable bd = new BitmapDrawable(resources, bitmap);
		Drawable d = bd;
		return d;
	}
	
	public static void saveBitmapToLocal(Bitmap bitmap, File file,int quality)
	{
		try
		{
			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
			out.flush();
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
