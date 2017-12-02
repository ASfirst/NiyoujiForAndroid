package com.jeramtough.niyouji.component.app;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @author 11718
 *         on 2017  December 02 Saturday 13:28.
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule
{
	@Override
	public boolean isManifestParsingEnabled()
	{
		return false;
	}
	
	
}
