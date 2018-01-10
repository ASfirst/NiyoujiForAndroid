package com.jeramtough.niyouji.component.ali.camera;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * @author 11718
 */
public class AliyunVideoGlSurfaceView extends GLSurfaceView
{
	private Renderer mRenderer;
	private static final String TAG = AliyunVideoGlSurfaceView.class.getName();
	
	public AliyunVideoGlSurfaceView(Context context)
	{
		super(context);
	}
	
	public AliyunVideoGlSurfaceView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	@Override
	protected void onWindowVisibilityChanged(int visibility)
	{
		if (mRenderer == null)
		{
			return;
		}
		super.onWindowVisibilityChanged(visibility);
		Log.d(TAG, "onWindowVisibilityChanged");
	}
	
	@Override
	public void setRenderer(Renderer renderer)
	{
		super.setRenderer(renderer);
		mRenderer = renderer;
		Log.d(TAG, "setRender");
	}
	
}
