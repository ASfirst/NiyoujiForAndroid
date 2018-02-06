package com.jeramtough.niyouji.component.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import com.jeramtough.jtlog3.P;
import com.jeramtough.jtlog3.WithLogger;
import com.jeramtough.niyouji.component.app.AppConfig;

/**
 * @author 11718
 *         on 2018  February 06 Tuesday 20:44.
 */

public class NiyoujiWebView extends WebView implements WithLogger
{
	
	private final String baseUrl = "http://"+AppConfig.NIYOUJI_SERVER_HOST +
			"/niyouji/travelnote.html";
	
	public NiyoujiWebView(Context context)
	{
		super(context);
	}
	
	public NiyoujiWebView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	public void loadTravelnoteWebpage(String travelnoteId)
	{
		String url=baseUrl + "?travelnoteId=" + travelnoteId;
		getP().info("loading "+url);
		this.loadUrl(url);
	}
}
