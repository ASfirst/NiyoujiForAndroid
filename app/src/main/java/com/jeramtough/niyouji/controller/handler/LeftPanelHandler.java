package com.jeramtough.niyouji.controller.handler;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.jeramtough.jtandroid.controller.handler.JtBaseHandler;
import com.jeramtough.jtandroid.ui.RoundImageView;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.controller.activity.AppBaseActivity;
import com.jeramtough.niyouji.controller.activity.LoginActivity;

/**
 * @author 11718
 *         on 2017  December 30 Saturday 01:12.
 */

public class LeftPanelHandler extends JtBaseHandler
		implements NavigationView.OnNavigationItemSelectedListener
{
	private RoundImageView imageViewSurface;
	private TextView textViewLoginOrRegister;
	private TextView textViewUsername;
	private TextView textViewGoldCount;
	
	
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
				IntentUtil.toTheOtherActivity(getActivity(), LoginActivity.class);
				break;
		}
	}
}
