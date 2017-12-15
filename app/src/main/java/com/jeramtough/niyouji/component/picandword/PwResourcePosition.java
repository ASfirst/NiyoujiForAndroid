package com.jeramtough.niyouji.component.picandword;

/**
 * @author 11718 on 2017 December 10 Sunday 18:39.
 */

public class PwResourcePosition {
	private String themeName;

	private String surfacePath;

	private String backgroundPath;

	private String framePath;

	private String deleteBtnBackgroundPath;

	private String defaultFunctionBtnBackgroundPath;

	private String pressedFunctionBtnBackgroundPath;

	private int fontColor;

	private int defaultFunctionColor;

	private int pressedFunctionColor;

	private boolean isVip;

	public boolean isVip() {
		return isVip;
	}

	public void setVip(boolean vip) {
		isVip = vip;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public String getSurfacePath() {
		return surfacePath;
	}

	public void setSurfacePath(String surfacePath) {
		this.surfacePath = surfacePath;
	}

	public String getBackgroundPath() {
		return backgroundPath;
	}

	public void setBackgroundPath(String backgroundPath) {
		this.backgroundPath = backgroundPath;
	}

	public String getFramePath() {
		return framePath;
	}

	public void setFramePath(String framePath) {
		this.framePath = framePath;
	}

	public String getDeleteBtnBackgroundPath() {
		return deleteBtnBackgroundPath;
	}

	public void setDeleteBtnBackgroundPath(String deleteBtnBackgroundPath) {
		this.deleteBtnBackgroundPath = deleteBtnBackgroundPath;
	}

	public String getDefaultFunctionBtnBackgroundPath() {
		return defaultFunctionBtnBackgroundPath;
	}

	public void setDefaultFunctionBtnBackgroundPath(
			String defaultFunctionBtnBackgroundPath) {
		this.defaultFunctionBtnBackgroundPath = defaultFunctionBtnBackgroundPath;
	}

	public String getPressedFunctionBtnBackgroundPath() {
		return pressedFunctionBtnBackgroundPath;
	}

	public void setPressedFunctionBtnBackgroundPath(
			String pressedFunctionBtnBackgroundPath) {
		this.pressedFunctionBtnBackgroundPath = pressedFunctionBtnBackgroundPath;
	}

	public int getFontColor() {
		return fontColor;
	}

	public void setFontColor(int fontColor) {
		this.fontColor = fontColor;
	}

	public int getDefaultFunctionColor() {
		return defaultFunctionColor;
	}

	public void setDefaultFunctionColor(int defaultFunctionColor) {
		this.defaultFunctionColor = defaultFunctionColor;
	}

	public int getPressedFunctionColor() {
		return pressedFunctionColor;
	}

	public void setPressedFunctionColor(int pressedFunctionColor) {
		this.pressedFunctionColor = pressedFunctionColor;
	}

}
