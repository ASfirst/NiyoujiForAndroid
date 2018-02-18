package com.jeramtough.jtandroid.function;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

/**
 * @author 11718
 *         on 2018  February 18 Sunday 11:39.
 */
@JtComponent
public class LocationHolder
{
	private LocationManager locationManager;
	private Context context;
	
	@IocAutowire
	public LocationHolder(Context context)
	{
		this.context = context;
		String serviceString = Context.LOCATION_SERVICE;// 获取的是位置服务
		locationManager = (LocationManager) context.getSystemService(serviceString);
	}
	
	public Location getLocation()
	{
		if (ActivityCompat
				.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
				PackageManager.PERMISSION_GRANTED && ActivityCompat
				.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
				PackageManager.PERMISSION_GRANTED)
		{
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return null;
		}
		Location location = null;
		if (locationManager != null)
		{
			location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);// 调用
			if (location == null)
			{
				location =
						locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}
		}
		return location;
	}
	
}
