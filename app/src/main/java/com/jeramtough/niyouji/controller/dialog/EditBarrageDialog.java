package com.jeramtough.niyouji.controller.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.jeramtough.niyouji.R;

/**
 * @author 11718
 *         on 2018  January 06 Saturday 13:38.
 */

public class EditBarrageDialog extends Dialog implements View.OnClickListener
{
	private EditText editBarrage;
	private Button buttonCancelBarrage;
	private Button buttonSendBarrage;
	
	private EditBarrageListener editBarrageListener;
	
	public EditBarrageDialog(@NonNull Context context)
	{
		super(context);
		
		this.setContentView(R.layout.dialog_edit_barrage);
		
		editBarrage = findViewById(R.id.edit_barrage);
		buttonCancelBarrage = findViewById(R.id.button_cancel_barrage);
		buttonSendBarrage = findViewById(R.id.button_send_barrage);
		
		buttonSendBarrage.setOnClickListener(this);
		buttonCancelBarrage.setOnClickListener(this);
	}
	
	@Override
	public void show()
	{
		super.show();
		editBarrage.requestFocus();
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.button_send_barrage:
				if (editBarrageListener != null && editBarrage.getText().length() > 0)
				{
					editBarrageListener.onSentBarrage(editBarrage.getText().toString());
				}
				this.cancel();
				break;
			case R.id.button_cancel_barrage:
				this.cancel();
				break;
		}
	}
	
	public void setEditBarrageListener(EditBarrageListener editBarrageListener)
	{
		this.editBarrageListener = editBarrageListener;
	}
	
	//{{{{{{{{{{{{}}}}}}}}}}}
	public interface EditBarrageListener
	{
		void onSentBarrage(String content);
	}
}
