package com.jeramtough.niyouji.controller.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.jeramtough.jtandroid.controller.dialog.BottomPopupDialog;
import com.jeramtough.niyouji.R;

/**
 * @author 11718
 *         on 2017  November 23 Thursday 01:32.
 */

public class SelectDecalDialog extends BottomPopupDialog implements View.OnClickListener
{
	private ImageView imageViewDecal1;
	private ImageView imageViewDecal2;
	private ImageView imageViewDecal3;
	private ImageView imageViewDecal4;
	private ImageView imageViewDecal5;
	private ImageView imageViewDecal6;
	
	public SelectDecalDialog(@NonNull Context context)
	{
		super(context);
	}
	
	@Override
	public View loadView(LayoutInflater inflater)
	{
		View view = inflater.inflate(R.layout.dialog_select_decals, null);
		return view;
	}
	
	@Override
	public void onViewIsCreated(View view)
	{
		imageViewDecal1 = findViewById(R.id.imageView_decal1);
		imageViewDecal2 = findViewById(R.id.imageView_decal2);
		imageViewDecal3 = findViewById(R.id.imageView_decal3);
		imageViewDecal4 = findViewById(R.id.imageView_decal4);
		imageViewDecal5 = findViewById(R.id.imageView_decal5);
		imageViewDecal6 = findViewById(R.id.imageView_decal6);
		
		imageViewDecal1.setOnClickListener(this);
		imageViewDecal2.setOnClickListener(this);
		imageViewDecal3.setOnClickListener(this);
		imageViewDecal4.setOnClickListener(this);
		imageViewDecal5.setOnClickListener(this);
		imageViewDecal6.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		this.cancel();
		AlertDialog alertDialog=new AlertDialog.Builder(getContext()).setMessage
				("由于作者无法支付短视频SDK收费功能，所以此功能暂时无法使用，给您带来不便在此致歉。")
				.create();
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定", (dialog, which) ->
		{
		
		});
		
		alertDialog.show();
	}
}
