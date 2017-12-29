package com.jeramtough.niyouji.controller.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.niyouji.R;
import com.jeramtough.niyouji.controller.handler.LeftPanelHandler;
import com.jeramtough.niyouji.controller.handler.MainNavigation;

/**
 * @author 11718
 */
public class MainActivity extends AppBaseActivity
{
	private MainNavigation mainNavigation;
	private ImageButton imageButtonPerform;
	
	private LeftPanelHandler leftPanelHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		RadioGroup radioGroupMainNavigation = findViewById(R.id.radioGroup_main_navigation);
		RadioButton radioButton0 = findViewById(R.id.radioButton_0);
		RadioButton radioButton1 = findViewById(R.id.radioButton_1);
		ViewPager viewPagerMainNavigation = findViewById(R.id.viewPager_main_navigation);
		imageButtonPerform = findViewById(R.id.imageButton_perform);
		
		mainNavigation =
				new MainNavigation(getSupportFragmentManager(), radioGroupMainNavigation,
						radioButton0, radioButton1, viewPagerMainNavigation);
		leftPanelHandler = new LeftPanelHandler(this);
		
		imageButtonPerform.setOnClickListener(this);
	}
	
	@Override
	public void onBackPressed()
	{
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START);
		}
		else
		{
			super.onBackPressed();
		}
	}
	
	
	@Override
	public void onClick(View v, int viewId)
	{
		switch (viewId)
		{
			case R.id.imageButton_perform:
				IntentUtil.toTheOtherActivity(this, CreateTravelnoteActivity.class);
				break;
		}
	}
}
