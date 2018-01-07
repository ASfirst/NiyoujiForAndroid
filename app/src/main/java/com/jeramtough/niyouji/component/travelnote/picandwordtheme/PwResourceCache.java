package com.jeramtough.niyouji.component.travelnote.picandwordtheme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PwResourceCache {

	private PwResourcePosition pwResourcePosition;

	private Bitmap mainBackgroundBitmap, frameBitmap, deleteBitmap,
			defaultFunctionBitmap, pressedFunctionBitmap;

	public PwResourceCache(PwResourcePosition pwResourcePosition) {
		super();
		this.pwResourcePosition = pwResourcePosition;
		this.initResources();
	}

	protected void initResources() {
		mainBackgroundBitmap = BitmapFactory.decodeFile(pwResourcePosition
				.getBackgroundPath());

		frameBitmap = BitmapFactory.decodeFile(pwResourcePosition
				.getFramePath());

		deleteBitmap = BitmapFactory.decodeFile(pwResourcePosition
				.getDeleteBtnBackgroundPath());

		defaultFunctionBitmap = BitmapFactory.decodeFile(pwResourcePosition
				.getDefaultFunctionBtnBackgroundPath());

		pressedFunctionBitmap = BitmapFactory.decodeFile(pwResourcePosition
				.getPressedFunctionBtnBackgroundPath());
	}

	public void recycleMemory() {
		mainBackgroundBitmap.recycle();
		frameBitmap.recycle();
		deleteBitmap.recycle();
		defaultFunctionBitmap.recycle();
		pressedFunctionBitmap.recycle();

	}

	public Bitmap getMainBackgroundBitmap() {
		return mainBackgroundBitmap;
	}

	public Bitmap getFrameBitmap() {
		return frameBitmap;
	}

	public Bitmap getDeleteBitmap() {
		return deleteBitmap;
	}

	public Bitmap getDefaultFunctionBitmap() {
		return defaultFunctionBitmap;
	}

	public Bitmap getPressedFunctionBitmap() {
		return pressedFunctionBitmap;
	}

	public PwResourcePosition getPwResourcePosition() {
		return pwResourcePosition;
	}

	
	
}
