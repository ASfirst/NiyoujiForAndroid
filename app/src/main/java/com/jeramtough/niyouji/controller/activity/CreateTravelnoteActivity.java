package com.jeramtough.niyouji.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.JtVideoView;
import com.jeramtough.jtandroid.util.BitmapUtil;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.business.CreateTravelnoteBusiness;
import com.jeramtough.niyouji.business.CreateTravelnoteService;
import com.jeramtough.niyouji.controller.dialog.SelectTakephotoOrAlbumDialog;
import com.jeramtough.niyouji.controller.dialog.SelectTakephotoOrVideoDialog;

/**
 * @author 11718
 */
public class CreateTravelnoteActivity extends AppBaseActivity implements View.OnTouchListener
{
	private static final int COVER_TYPE_NONE = 0;
	private static final int COVER_TYPE_PHOTO = 1;
	private static final int COVER_TYPE_VIDEO = 2;
	
	private static final int BUSINESS_CODE_CREATE_TRAVELNOTE = 0;
	private static final int BUSINESS_CODE_CONNECT_SERVER = 1;
	private static final int BUSINESS_CODE_UPLOAD_COVER_RESOURCE = 2;
	
	private TextView textViewTjyjfm;
	private ImageView imageViewAddTravelnoteCover;
	private EditText editTravelnoteTitle;
	private Button btnStartPerforming;
	private JtVideoView videoViewTravelnoteCover;
	private ImageView imageViewTravelnoteCover;
	private AppCompatImageView viewBack;
	private LinearLayout layoutWaitingCreateTravelnote;
	private TextView textViewCreateTravelnoteInfo;
	
	private String coverPath;
	private String title;
	private int coverType = COVER_TYPE_NONE;
	
	@InjectService(service = CreateTravelnoteService.class)
	private CreateTravelnoteBusiness createTravelnoteBusiness;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_travelnote);
		
		textViewTjyjfm = findViewById(R.id.textView_tjyjfm);
		imageViewAddTravelnoteCover = findViewById(R.id.imageView_add_travelnote_cover);
		editTravelnoteTitle = findViewById(R.id.edit_travelnote_title);
		btnStartPerforming = findViewById(R.id.btn_start_performing);
		videoViewTravelnoteCover = findViewById(R.id.videoView_travelnote_cover);
		imageViewTravelnoteCover = findViewById(R.id.imageView_travelnote_cover);
		viewBack = findViewById(R.id.view_back);
		layoutWaitingCreateTravelnote = findViewById(R.id.layout_waiting_create_travelnote);
		textViewCreateTravelnoteInfo = findViewById(R.id.textView_create_travelnote_info);
		
		imageViewTravelnoteCover.setClickable(false);
		layoutWaitingCreateTravelnote.setVisibility(View.INVISIBLE);
		
		imageViewAddTravelnoteCover.setOnClickListener(this);
		imageViewTravelnoteCover.setOnClickListener(this);
		btnStartPerforming.setOnClickListener(this);
		viewBack.setOnClickListener(this);
		videoViewTravelnoteCover.setOnTouchListener(this);
		videoViewTravelnoteCover.setOnCompletionListener(mp ->
		{
			videoViewTravelnoteCover.start();
		});
	}
	
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.view_back:
				this.finish();
				break;
			
			case R.id.imageView_travelnote_cover:
			case R.id.imageView_add_travelnote_cover:
				SelectTakephotoOrAlbumDialog dialog = new SelectTakephotoOrAlbumDialog(this);
				dialog.show();
				break;
			case R.id.btn_start_performing:
				title = editTravelnoteTitle.getText().toString();
				if (title.length() == 0)
				{
					Toast.makeText(this, "请输入游记标题！", Toast.LENGTH_SHORT).show();
					break;
				}
				if (coverPath == null)
				{
					Toast.makeText(this, "没有拍摄游记封面哦！", Toast.LENGTH_SHORT).show();
					break;
				}
				
				layoutWaitingCreateTravelnote.setVisibility(View.VISIBLE);
				
				BusinessCaller createBusinessCaller = new BusinessCaller(getActivityHandler(),
						BUSINESS_CODE_CREATE_TRAVELNOTE);
				BusinessCaller connectBusinessCaller =
						new BusinessCaller(getActivityHandler(), BUSINESS_CODE_CONNECT_SERVER);
				BusinessCaller uploadBusinessCaller = new BusinessCaller(getActivityHandler(),
						BUSINESS_CODE_UPLOAD_COVER_RESOURCE);
				
				createTravelnoteBusiness
						.createTravelnote(title, coverPath, createBusinessCaller,
								connectBusinessCaller, uploadBusinessCaller);
				break;
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			SelectTakephotoOrVideoDialog dialog = new SelectTakephotoOrVideoDialog(this);
			dialog.show();
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null)
		{
			if (requestCode == resultCode &&
					resultCode == TakePhotoActivity.TAKE_PHOTO_RESULT_CODE)
			{
				this.coverPath = data.getStringExtra(TakePhotoActivity.PHOTO_PATH_NAME);
			}
			else if (requestCode ==
					SelectTakephotoOrAlbumDialog.REQUEST_CODE_SELECT_IMAGE_FROM_ALIBUM &&
					resultCode == Activity.RESULT_OK)
			{
				Uri selectedImage = data.getData();
				String[] filePathColumns = {MediaStore.Images.Media.DATA};
				Cursor c = getContentResolver()
						.query(selectedImage, filePathColumns, null, null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePathColumns[0]);
				this.coverPath = c.getString(columnIndex);
				c.close();
			}
			
			if (coverPath != null)
			{
				recycleTheCoverResource();
				
				Bitmap bitmap = BitmapFactory.decodeFile(coverPath);
				imageViewTravelnoteCover.setImageBitmap(bitmap);
				imageViewTravelnoteCover.setVisibility(View.VISIBLE);
				
				textViewTjyjfm.setVisibility(View.INVISIBLE);
				imageViewAddTravelnoteCover.setVisibility(View.INVISIBLE);
				imageViewTravelnoteCover.setClickable(true);
				
				coverType = COVER_TYPE_PHOTO;
			}
		}
	}
	
	
	@Override
	public void handleActivityMessage(Message message)
	{
		switch (message.what)
		{
			case BUSINESS_CODE_CONNECT_SERVER:
				boolean connectSuccessfully =
						message.getData().getBoolean("connectSuccessfully");
				if (connectSuccessfully)
				{
					textViewCreateTravelnoteInfo.setText("连接服务器成功！\n正在创建房间...");
				}
				else
				{
					textViewCreateTravelnoteInfo.setText("连接超时！\n请检查网络后重试！");
					layoutWaitingCreateTravelnote.postDelayed(() ->
					{
						layoutWaitingCreateTravelnote.setVisibility(View.INVISIBLE);
					}, 4000);
				}
				break;
			case BUSINESS_CODE_UPLOAD_COVER_RESOURCE:
				boolean uploadSuccessfully =
						message.getData().getBoolean("uploadSuccessfully");
				if (uploadSuccessfully)
				{
					textViewCreateTravelnoteInfo.setText("上传资源成功");
				}
				else
				{
					textViewCreateTravelnoteInfo.setText("上传资源失败！");
				}
				break;
			case BUSINESS_CODE_CREATE_TRAVELNOTE:
				textViewCreateTravelnoteInfo.setText("开始您的游记直播！！！");
				layoutWaitingCreateTravelnote.postDelayed(() ->
				{
					IntentUtil.toTheOtherActivity(CreateTravelnoteActivity.this,
							PerformingActivity.class);
					CreateTravelnoteActivity.this.finish();
				}, 500);
				break;
		}
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		recycleTheCoverResource();
	}
	
	//************************************
	private void recycleTheCoverResource()
	{
		if (coverType == COVER_TYPE_PHOTO)
		{
			BitmapUtil.releaseDrawableResouce(imageViewTravelnoteCover.getDrawable());
			imageViewTravelnoteCover.setImageResource(R.color.transparent);
			imageViewAddTravelnoteCover.setVisibility(View.INVISIBLE);
		}
		else if (coverType == COVER_TYPE_VIDEO)
		{
			videoViewTravelnoteCover.stopPlayback();
			videoViewTravelnoteCover.destroyDrawingCache();
		}
	}
	
	
}
