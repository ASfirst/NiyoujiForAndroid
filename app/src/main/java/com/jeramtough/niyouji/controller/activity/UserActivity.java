package com.jeramtough.niyouji.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.business.UserBusiness;
import com.jeramtough.niyouji.business.UserService;

/**
 * @author 11718
 */
public class UserActivity extends AppBaseActivity
{
	
	private Button buttonLogout;
	
	@InjectService(service = UserService.class)
	private UserBusiness userBusiness;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		buttonLogout = findViewById(R.id.button_logout);
		
		buttonLogout.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.button_logout:
				userBusiness.userLogout();
				this.finish();
				break;
		}
	}
}
