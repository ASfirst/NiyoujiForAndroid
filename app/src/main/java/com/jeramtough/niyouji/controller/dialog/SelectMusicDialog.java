package com.jeramtough.niyouji.controller.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.jeramtough.jtandroid.adapter.JtTextItemAdapter;
import com.jeramtough.jtandroid.controller.dialog.BottomPopupDialog;
import com.jeramtough.jtandroid.function.MusicPlayer;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.component.ali.camera.CameraMusic;

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
	
	private MusicPlayer musicPlayer;
	
	private CameraMusic cameraMusic;
	
	private JtTextItemAdapter adapter;
	
	private SelectMusicListener selectMusicListener;
	
	public SelectMusicDialog(@NonNull Context context, List<CameraMusic> cameraMusics)
	{
		super(context);
		this.cameraMusics = cameraMusics;
		
		initResources();
		initView();
	}
	
	protected void initResources()
	{
		musicPlayer = new MusicPlayer(this.getContext());
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
		if (cameraMusic != null)
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
		
		musicPlayer.playMusic(cameraMusic.getPath(),true);
	}
	
	@Override
	public void cancel()
	{
		super.cancel();
		musicPlayer.end();
	}
	
	public void setSelectMusicListener(SelectMusicListener selectMusicListener)
	{
		this.selectMusicListener = selectMusicListener;
	}
	
	//{{{{{{{{{{{{{{}}}}}}}}}}}}}}}}}}}}}}}}}}
	public interface SelectMusicListener
	{
		void selectMusic(CameraMusic cameraMusic);
	}
}
