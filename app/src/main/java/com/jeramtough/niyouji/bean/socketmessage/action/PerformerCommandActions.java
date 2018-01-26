package com.jeramtough.niyouji.bean.socketmessage.action;

/**
 * @author 11718
 */
public class PerformerCommandActions
{
	private static final int BASE = 100;
	
	public static final int CREATE_PERFORMING_ROOM = BASE + 0;
	
	public static final int SELECTED_PAGE = BASE + 1;
	
	public static final int ADDED_PAGE = BASE + 2;
	
	public static final int DELETED_PAGE = BASE + 3;
	
	public static final int PAGE_SET_IMAGE = BASE + 4;
	
	public static final int PAGE_SET_VIDEO = BASE + 5;
	
	public static final int PAGE_TEXT_CHANGED = BASE + 6;
	
	public static final int PAGE_SET_THEME = BASE + 7;
	
	public static final int PAGE_SET_BACKGROUND_MUSIC = BASE + 8;
	
	public static final int SENT_PERFORMER_BARRAGE = BASE + 9;
	
	public static final int TRAVELNOTE_END = BASE + 10;
	
	public static final int PERFORMER_LEAVE = BASE + 11;
	
	public static final int PERFORMER_REBACK = BASE + 12;
}
