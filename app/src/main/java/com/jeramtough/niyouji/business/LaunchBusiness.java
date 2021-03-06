package com.jeramtough.niyouji.business;

import android.app.Activity;
import com.jeramtough.jtandroid.business.BusinessCaller;

/**
 * @author 11718
 *         on 2017  November 20 Monday 10:55.
 */

public interface LaunchBusiness
{
	/**
	 * 是否启动过app并看过介绍。
	 *
	 * @return true if no boot.
	 */
	boolean isFirstBoot();
	
	/**
	 * 用户已经启动过一次了
	 */
	void hasBootFinally();
	
	/**
	 * 请求app需要的权限
	 *
	 * @param activity    请求权限的activity实例
	 * @param requestCode 请求回调码
	 * @return 是否以获得全部权限
	 */
	boolean requestNeededPermission(Activity activity, int requestCode);
	
	
	/**
	 * 创建app所需要的资源目录
	 */
	void createAppDirectory(BusinessCaller businessCaller, Activity activity);
	
	/**
	 * 初始化app需要的数据
	 */
	void initAppData(BusinessCaller businessCaller);
}
