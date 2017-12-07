package com.jeramtough.jtandroid.ioc;

import android.content.res.Resources;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;

/**
 * @author 11718
 *         on 2017  December 06 Wednesday 00:10.
 */

public class IocConfig
{
	private static final String CONFIG_XML_NAME = "jtioc_config.xml";
	
	private String InjectedObjectsClassPath;
	
	private Resources resources;
	
	public IocConfig(Resources resources)
	{
		this.resources = resources;
		
		initResources();
	}
	
	protected void initResources()
	{
		try
		{
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(resources.getAssets().open(CONFIG_XML_NAME), "UTF-8");
			while (parser.next() != XmlPullParser.END_DOCUMENT)
			{
				int eventType = parser.getEventType();
				switch (eventType)
				{
					case XmlPullParser.START_TAG:
						String tagName = parser.getName();
						if (tagName.equals("InjectedObjects"))
						{
							InjectedObjectsClassPath = parser.nextText();
						}
						break;
					case XmlPullParser.END_TAG:
						break;
				}
				
			}
		}
		catch (XmlPullParserException | IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getInjectedObjectsClassPath()
	{
		return InjectedObjectsClassPath;
	}
}
