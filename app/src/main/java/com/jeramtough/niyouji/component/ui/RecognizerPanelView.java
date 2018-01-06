package com.jeramtough.niyouji.component.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.baidusdk.BaiduRecognizer;

/**
 * @author 11718
 *         on 2017  December 25 Monday 00:11.
 */

public class RecognizerPanelView extends FrameLayout
		implements BaiduRecognizer.RecognizeListener, View.OnClickListener
{
	private AppCompatImageButton btnCloseRecognize;
	private TextView textViewRecognisingInfo;
	private RecognizerPanelListener recognizerPanelListener;
	
	private BaiduRecognizer baiduRecognizer;
	
	public RecognizerPanelView(@NonNull Context context)
	{
		super(context);
		initViews();
		initResources();
	}
	
	public RecognizerPanelView(@NonNull Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
		initViews();
		initResources();
	}
	
	protected void initViews()
	{
		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
		ViewGroup viewGroup = (ViewGroup) layoutInflater
				.inflate(R.layout.view_recognizer_panel, this, false);
		this.addView(viewGroup);
		
		btnCloseRecognize = findViewById(R.id.btn_close_recognize);
		textViewRecognisingInfo = findViewById(R.id.textView_recognising_info);
		
		btnCloseRecognize.setOnClickListener(this);
	}
	
	protected void initResources()
	{
		baiduRecognizer = new BaiduRecognizer(getContext());
		baiduRecognizer.setRecognizeListener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.btn_close_recognize:
				baiduRecognizer.stop();
				if (recognizerPanelListener != null)
				{
					recognizerPanelListener.onRecognizeCancel();
				}
				break;
			default:
		}
	}
	
	@Override
	public void onRecognizeReady()
	{
		textViewRecognisingInfo.setText("语音引擎准备就绪！请开始说话");
	}
	
	@Override
	public void onRecognizeBegin()
	{
		textViewRecognisingInfo.setText("检测到正在说话，识别中。。。");
	}
	
	@Override
	public void onRecognizePartial(String text)
	{
		textViewRecognisingInfo.setText("识别中");
		if (recognizerPanelListener != null)
		{
			recognizerPanelListener.onRecognizePartial(text);
		}
	}
	
	@Override
	public void onRecognizeFinish(String text)
	{
		textViewRecognisingInfo.setText("识别完成一句话");
		if (recognizerPanelListener != null)
		{
			recognizerPanelListener.onRecognizeFinish(text);
		}
	}
	
	@Override
	public void onRecognizeError(String errorMessage)
	{
		textViewRecognisingInfo.setText(errorMessage);
	}
	
	public void startRecognize()
	{
		baiduRecognizer.start();
	}
	
	public void setRecognizerPanelListener(RecognizerPanelListener recognizerPanelListener)
	{
		this.recognizerPanelListener = recognizerPanelListener;
	}
	
	//{{{{{{{{{{{{{{{{}}}}}}}}}}}}}}}
	public interface RecognizerPanelListener
	{
		void onRecognizePartial(String text);
		
		void onRecognizeFinish(String text);
		
		void onRecognizeCancel();
	}
	
}
