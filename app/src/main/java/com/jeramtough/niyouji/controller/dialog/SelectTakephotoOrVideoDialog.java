package com.jeramtough.niyouji.controller.dialog;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.jeramtough.jtandroid.controller.dialog.BottomPopupDialog;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.controller.activity.TakePhotoActivity;
import com.jeramtough.niyouji.controller.activity.VideoActivity;

/**
 * @author 11718
 *         on 2017  November 20 Monday 20:36.
 */

public class SelectTakephotoOrVideoDialog extends BottomPopupDialog
		implements View.OnClickListener
{
	private ImageView imageViewTakephoto;
	private ImageView imageViewVideo;
	private Activity activity;
	
	public SelectTakephotoOrVideoDialog(@NonNull Activity activity)
	{
		super(activity);
		this.activity = activity;
	}
	
	@Override
	public View loadView(LayoutInflater inflater)
	{
		View view = inflater.inflate(R.layout.dialog_select_takephoto_or_video, null);
		return view;
	}
	
	@Override
	public void onViewIsCreated(View view)
	{
		imageViewTakephoto = findViewById(R.id.imageView_takephoto);
		imageViewVideo = findViewById(R.id.imageView_video);
		
		imageViewTakephoto.setOnClickListener(this);
		imageViewVideo.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		int id = v.getId();
		switch (id)
		{
			case R.id.imageView_takephoto:
				IntentUtil.toTheOtherActivity(activity, TakePhotoActivity.class,
						TakePhotoActivity.TAKE_PHOTO_RESULT_CODE);
				break;
			case R.id.imageView_video:
				IntentUtil.toTheOtherActivity(activity, VideoActivity.class,
						VideoActivity.VIDEO_RESULT_CODE);
				break;
		}
		cancel();
	}
}
