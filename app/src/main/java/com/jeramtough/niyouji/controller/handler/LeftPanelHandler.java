package com.jeramtough.niyouji.controller.handler;

import android.app.Activity;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.jeramtough.jtandroid.controller.handler.JtIocHandler;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.RoundImageView;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.business.LeftPanelBusiness;
import com.jeramtough.niyouji.business.LeftPanelService;
import com.jeramtough.niyouji.controller.activity.AppBaseActivity;
import com.jeramtough.niyouji.controller.activity.LoginActivity;

/**
 * @author 11718
 *         on 2017  December 30 Saturday 01:12.
 */

public class LeftPanelHandler extends JtIocHandler
		implements NavigationView.OnNavigationItemSelectedListener
{
	public static final int ACTIVITY_REQUEST_CODE_LOGIN = 0;
	
	public static final int BUSINESS_CODE_CLEAR_CACHES = 0;
	
	private RoundImageView imageViewSurface;
	private TextView textViewLoginOrRegister;
	private TextView textViewUsername;
	private TextView textViewGoldCount;
	
	@InjectService(service = LeftPanelService.class)
	private LeftPanelBusiness leftPanelBusiness;
	
	public LeftPanelHandler(Activity activity)
	{
		super(activity);
		
		//初始化左边抽屉菜单
		Toolbar toolbar = findViewById(R.id.toolbar);
		toolbar.setTitle("");
		AppBaseActivity appBaseActivity = (AppBaseActivity) getActivity();
		appBaseActivity.setSupportActionBar(toolbar);
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle =
				new ActionBarDrawerToggle(appBaseActivity, drawer, toolbar,
						R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		NavigationView navigationView = findViewById(R.id.nav_view);
		
		imageViewSurface =
				navigationView.getHeaderView(0).findViewById(R.id.imageView_surface);
		textViewLoginOrRegister =
				navigationView.getHeaderView(0).findViewById(R.id.textView_login_or_register);
		textViewUsername =
				navigationView.getHeaderView(0).findViewById(R.id.textView_username);
		textViewGoldCount =
				navigationView.getHeaderView(0).findViewById(R.id.textView_gold_count);
		
		textViewUsername.setVisibility(View.INVISIBLE);
		
		navigationView.setNavigationItemSelectedListener(this);
		textViewLoginOrRegister.setOnClickListener(this);
		
		initResources();
	}
	
	protected void initResources()
	{
		boolean hasLogined = leftPanelBusiness.hasLogined();
		if (hasLogined)
		{
			//将这句注释掉就是自动登录了
			//			loginFinally();
		}
	}
	
	
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item)
	{
		int id = item.getItemId();
		
		if (id == R.id.nav_settings)
		{
		}
		else if (id == R.id.nav_clear_caches)
		{
			leftPanelBusiness.clearTravelnoteCaches(this);
		}
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
	
	@Override
	public void onClick(View view, int viewId)
	{
		switch (viewId)
		{
			case R.id.textView_login_or_register:
				IntentUtil.toTheOtherActivity(getActivity(), LoginActivity.class,
						ACTIVITY_REQUEST_CODE_LOGIN);
				break;
		}
	}
	
	@Override
	public void handleMessage(Message msg)
	{
		switch (msg.what)
		{
			case BUSINESS_CODE_CLEAR_CACHES:
				int size = msg.getData().getInt("size");
				Toast.makeText(getContext(), "清除了" + size + "MB的缓存", Toast.LENGTH_SHORT)
						.show();
				break;
		}
	}
	
	public void loginFinally()
	{
		textViewLoginOrRegister.setVisibility(View.INVISIBLE);
		textViewUsername.setVisibility(View.VISIBLE);
		textViewUsername.setText(leftPanelBusiness.getUserNickname());
		imageViewSurface.setImageResource(R.mipmap.surface_image);
	}
}
