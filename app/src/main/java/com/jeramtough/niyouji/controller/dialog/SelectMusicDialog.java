package com.jeramtough.niyouji.controller.dialog;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.jeramtough.jtandroid.adapter.JtTextItemAdapter;
import com.jeramtough.jtandroid.controller.dialog.BottomPopupDialog;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.ali.CameraMusic;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 11718
 *         on 2017  November 23 Thursday 16:32.
 */

public class SelectMusicDialog extends BottomPopupDialog
		implements View.OnClickListener, AdapterView.OnItemClickListener
{
	private List<CameraMusic> cameraMusics;
	private Button btnDone;
	private ListView listViewMusics;
	
	private MediaPlayer mediaPlayer;
	
	private CameraMusic cameraMusic;
	
	private JtTextItemAdapter adapter;
	
	private SelectMusicListener selectMusicListener;
	
	public SelectMusicDialog(@NonNull Context context, List<CameraMusic> cameraMusics)
	{
		super(context);
		this.cameraMusics = cameraMusics;
		
		initReources();
		initView();
	}
	
	protected void initReources()
	{
		
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	}
	
	protected void initView()
	{
		ViewGroup viewGroup =
				(ViewGroup) getInflater().inflate(R.layout.dialog_select_music, null);
		btnDone = viewGroup.findViewById(R.id.btn_done);
		listViewMusics = viewGroup.findViewById(R.id.listView_musics);
		
		String[] musicNames = new String[cameraMusics.size()];
		for (int i = 0; i < cameraMusics.size(); i++)
		{
			musicNames[i] = cameraMusics.get(i).getName();
		}
		
		adapter = new JtTextItemAdapter(getContext(), musicNames);
		listViewMusics.setAdapter(adapter);
		
		this.setContentView(viewGroup);
		
		btnDone.setOnClickListener(this);
		listViewMusics.setOnItemClickListener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		if (cameraMusic!=null)
		{
			if (selectMusicListener != null)
			{
				selectMusicListener.selectMusic(cameraMusic);
			}
			cancel();
		}
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		adapter.resetTextViewsStyle();
		TextView textView = adapter.getTextView(position);
		textView.setBackgroundResource(R.color.colorPrimaryDark);
		textView.setTextColor(Color.WHITE);
		
		cameraMusic = cameraMusics.get(position);
		
		Uri uri = Uri.fromFile(new File(cameraMusic.getPath()));
		try
		{
			if (mediaPlayer.isPlaying())
			{
				mediaPlayer.stop();
				mediaPlayer.reset();
			}
			
			mediaPlayer.setDataSource(getContext(), uri);
			mediaPlayer.prepare();
			mediaPlayer.start();
			
			if (selectMusicListener != null)
			{
				selectMusicListener.selectMusic(cameraMusic);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void cancel()
	{
		super.cancel();
		if (mediaPlayer.isPlaying())
		{
			mediaPlayer.stop();
		}
		mediaPlayer.release();
	}
	
	public void setSelectMusicListener(SelectMusicListener selectMusicListener)
	{
		this.selectMusicListener = selectMusicListener;
	}
	
	//{{{{{{{{{{{{{{}]]]]]]]]]]]]]]]]]
	public interface SelectMusicListener
	{
		void selectMusic(CameraMusic cameraMusic);
	}
}
