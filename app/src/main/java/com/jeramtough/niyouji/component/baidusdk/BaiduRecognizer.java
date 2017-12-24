package com.jeramtough.niyouji.component.baidusdk;

import android.content.Context;
import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.jeramtough.jtandroid.jtlog2.P;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 11718
 *         on 2017  December 24 Sunday 19:05.
 */

public class BaiduRecognizer implements EventListener
{
	private EventManager eventManager;
	private String startJson;
	
	private RecognizeListener recognizeListener;
	
	public BaiduRecognizer(Context context)
	{
		eventManager = EventManagerFactory.create(context, "asr");
		eventManager.registerListener(this);
		
		initResources();
	}
	
	protected void initResources()
	{
		Map<String, Object> params = new LinkedHashMap<>();
		params.put(SpeechConstant.DECODER, 0);
		params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
		params.put(SpeechConstant.PID, 1537);
		params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT, 0);
		params.put(SpeechConstant.DISABLE_PUNCTUATION, true);
		startJson = new JSONObject(params).toString();
	}
	
	@Override
	public void onEvent(String name, String params, byte[] data, int offset, int length)
	{
		if (recognizeListener != null)
		{
			if (name.equals("asr.partial"))
			{
				try
				{
					JSONObject jsonObject = new JSONObject(params);
					jsonObject = jsonObject.getJSONObject("origin_result");
					jsonObject = jsonObject.getJSONObject("result");
					
					String text = jsonObject.getJSONArray("word").getString(0);
					
					P.debug(text);
					
					recognizeListener.onRecognizePartial(text);
					
				}
				catch (JSONException e)
				{
					e.printStackTrace();
					recognizeListener.onRecognizeError();
				}
			}
			else if (name.equals("asr.finish"))
			{
				try
				{
					JSONObject jsonObject = new JSONObject(params);
					jsonObject = jsonObject.getJSONObject("origin_result");
					String text = jsonObject.toString().split("best_result")[1];
					text = text.substring(5, text.length());
					text = text.split("result_type")[0];
					text = text.substring(0, text.length() - 5);
					recognizeListener.onRecognizeFinish(text);
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
					P.error("Baidu",e.getMessage());
					recognizeListener.onRecognizeError();
				}
			}
			else if (name.equals("asr.ready"))
			{
				recognizeListener.onRecognizeReady();
			}
			else if (name.equals("asr.begin"))
			{
				recognizeListener.onRecognizeBegin();
			}
		}
	}
	
	public void stop()
	{
		eventManager.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
		eventManager.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
	}
	
	public void start()
	{
		eventManager.send(SpeechConstant.ASR_START, startJson, null, 0, 0);
	}
	
	
	public void setRecognizeListener(RecognizeListener recognizeListener)
	{
		this.recognizeListener = recognizeListener;
	}
	
	//{{{{{{{{{{{{{}}}}}}}}}}}}}}}}}}}}}}}}}}
	public interface RecognizeListener
	{
		/**
		 * 引擎准备就绪，可以开始说话
		 */
		void onRecognizeReady();
		
		/**
		 * 检测到说话开始
		 */
		void onRecognizeBegin();
		
		/**
		 * 识别中
		 *
		 * @param text 识别结果
		 */
		void onRecognizePartial(String text);
		
		/**
		 * 完成一句话的识别
		 *
		 * @param text 识别结果
		 */
		void onRecognizeFinish(String text);
		
		/**
		 * 识别失败
		 */
		void onRecognizeError();
	}
}
