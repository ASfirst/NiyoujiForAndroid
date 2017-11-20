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
import com.jeramtough.niyouji.component.ui.MainNavigation;

/**
 * @author 11718
 */
public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener
{
	private MainNavigation mainNavigation;
	private ImageButton imageButtonPerform;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		toolbar.setTitle("");
		setSupportActionBar(toolbar);
		
		
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
				R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		
		RadioGroup radioGroupMainNavigation = findViewById(R.id.radioGroup_main_navigation);
		RadioButton radioButton0 = findViewById(R.id.radioButton_0);
		RadioButton radioButton1 = findViewById(R.id.radioButton_1);
		ViewPager viewPagerMainNavigation = findViewById(R.id.viewPager_main_navigation);
		imageButtonPerform = findViewById(R.id.imageButton_perform);
		
		mainNavigation =
				new MainNavigation(getSupportFragmentManager(), radioGroupMainNavigation,
						radioButton0, radioButton1, viewPagerMainNavigation);
		
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
	
	
	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		
		if (id == R.id.nav_camera)
		{
			// Handle the camera action
		}
		else if (id == R.id.nav_gallery)
		{
		
		}
		else if (id == R.id.nav_slideshow)
		{
		
		}
		else if (id == R.id.nav_manage)
		{
		
		}
		else if (id == R.id.nav_share)
		{
		
		}
		else if (id == R.id.nav_send)
		{
		
		}
		
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
	
	
	@Override
	public void onClick(View v)
	{
		int id = v.getId();
		switch (id)
		{
			case R.id.imageButton_perform:
				IntentUtil.toTheOtherActivity(this,CreateTravelnoteActivity.class);
				break;
		}
	}
}
