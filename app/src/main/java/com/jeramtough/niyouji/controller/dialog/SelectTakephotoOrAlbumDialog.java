package com.jeramtough.niyouji.controller.dialog;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.jeramtough.jtandroid.controller.dialog.BottomPopupDialog;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.jtlog3.P;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.controller.activity.TakePhotoActivity;
import com.jeramtough.niyouji.controller.activity.VideoActivity;

/**
 * @author 11718
 *         on 2017  November 20 Monday 20:36.
 */

public class SelectTakephotoOrAlbumDialog extends BottomPopupDialog
		implements View.OnClickListener
{
	public static final int REQUEST_CODE_SELECT_IMAGE_FROM_ALIBUM = 12;
	
	private ImageView imageViewTakephoto;
	private ImageView imageViewImage;
	private Activity activity;
	
	public SelectTakephotoOrAlbumDialog(@NonNull Activity activity)
	{
		super(activity);
		this.activity = activity;
	}
	
	@Override
	public View loadView(LayoutInflater inflater)
	{
		View view = inflater.inflate(R.layout.dialog_select_takephoto_or_album, null);
		return view;
	}
	
	@Override
	public void onViewIsCreated(View view)
	{
		imageViewTakephoto = findViewById(R.id.imageView_takephoto);
		imageViewImage = findViewById(R.id.imageView_image);
		
		imageViewTakephoto.setOnClickListener(this);
		imageViewImage.setOnClickListener(this);
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
			case R.id.imageView_image:
				Intent intent = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				activity.startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE_FROM_ALIBUM);
				break;
		}
		cancel();
	}
}
