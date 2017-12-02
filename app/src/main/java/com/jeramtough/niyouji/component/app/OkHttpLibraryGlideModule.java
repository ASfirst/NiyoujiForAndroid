package com.jeramtough.niyouji.component.app;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.LibraryGlideModule;

import java.io.InputStream;

/**
 * @author 11718
 * on 2017  December 02 Saturday 13:38.
 */

@GlideModule
public class OkHttpLibraryGlideModule extends LibraryGlideModule
{
	@Override
	public void registerComponents(Context context, Glide glide, Registry registry)
	{
		super.registerComponents(context, glide, registry);
		
		registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
	}
	
}
