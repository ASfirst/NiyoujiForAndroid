package com.jeramtough.jtandroid.function;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtandroid.ioc.annotation.JtObjectType;

import java.io.File;
import java.io.IOException;

/**
 * @author 11718
 *         on 2017  December 08 Friday 21:44.
 */
@JtComponent(pattern = JtObjectType.Prototype)
public class MusicPlayer implements MediaPlayer.OnCompletionListener
{
	private MediaPlayer mediaPlayer;
	private Context context;
	
	private boolean isRepeated = false;
	private String currentMusicPath;
	
	public MusicPlayer(Context context)
	{
		this.context = context;
		
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setOnCompletionListener(this);
	}
	
	@Override
	public void onCompletion(MediaPlayer mp)
	{
		if (isRepeated)
		{
			playMusic(currentMusicPath);
		}
	}
	
	public void setRepeated(boolean repeated)
	{
		isRepeated = repeated;
	}
	
	public void playMusic(String musicPath, boolean isRepeated)
	{
		this.isRepeated = isRepeated;
		
		playMusic(musicPath);
	}
	
	public void playMusic(String musicPath)
	{
		this.currentMusicPath = musicPath;
		Uri uri = Uri.fromFile(new File(musicPath));
		try
		{
			if (mediaPlayer.isPlaying())
			{
				mediaPlayer.stop();
			}
			mediaPlayer.reset();
			
			mediaPlayer.setDataSource(context, uri);
			mediaPlayer.prepare();
			mediaPlayer.start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void end()
	{
		if (mediaPlayer.isPlaying())
		{
			mediaPlayer.stop();
		}
		mediaPlayer.reset();
		mediaPlayer.release();
	}
	
	public MediaPlayer getMediaPlayer()
	{
		return mediaPlayer;
	}
}
