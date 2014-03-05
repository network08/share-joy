package cn.com.swpu.network08.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * @author xkk
 *
 */
public class LocationProvider {
	private LocationManager manager;  
	//定义一个GPSInfoProvider实例  
	private static LocationProvider myLocationProvider;  
	private static Context context;  
	private static MyLocationListener listener;  
	private LocationProvider() {  
	}  
	public static synchronized LocationProvider getInstance(Context context) {  
		if (myLocationProvider == null) {  
			myLocationProvider = new LocationProvider();  
			LocationProvider.context = context;  
		}  
		return myLocationProvider;  
	}  

	// 获取位置信息  
	public String getLocation() {  
		//通过上下文获得得到手机位置的系统服务，  
		manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);  
		//调用getProvider方法，获得最好的位置提供者  
		String provider = getProvider(manager);  
		//获得位置更新的操作  
		//manager.requestLocationUpdates(provider, minTime, minDistance, listener)  
		//其中的4个参数分别为：  
		//provider：使用的定位设备，基站定位、GPS定位、网络定位等  
		//minTime：多长时间更新一次定位信息，单位为毫秒，最少为一分钟  
		//minDistance：位置移动了多少米之后，重新获取一次定位信息  
		//listener：在位置发生变化时的回调方法。定义一个类（MyLocationListener），实现LocationListener接口  
		manager.requestLocationUpdates(provider, 60000, 10, getlistener());  
		SharedPreferences sp = context.getSharedPreferences("config",  
				Context.MODE_PRIVATE);
		String location = sp.getString("location", "30.827:104.189");//swpu  
		return location;  
	}  

	// 停止GPS监听  
	public void stopGPSListener() {  
		manager.removeUpdates(getlistener());  
	}  

	private synchronized MyLocationListener getlistener() {  
		if (listener == null) {  
			listener = new MyLocationListener();  
		}  
		return listener;  
	}  
	private class MyLocationListener implements LocationListener {  
		@Override  
		/** 
		 * 当位置发生变化时调用的方法 
		 */  
		public void onLocationChanged(Location location) {
			SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);  
			Editor editor = sp.edit();
			editor.putString("location", location.getLatitude() + ":" + location.getLongitude());  
			editor.commit();  
		}  

		@Override  
		/** 
		 * 设备被禁用时的回调方法 
		 */  
		public void onProviderDisabled(String provider) {  
		}  

		@Override  
		/** 
		 * 设备被打开时的回调方法 
		 */  
		public void onProviderEnabled(String provider) {  
		}  

		@Override  
		/** 
		 * 设备状态（可用、不可用）发生改变时回调的方法 
		 */  
		public void onStatusChanged(String provider, int status, Bundle extras) {  
		}  
	}  
	/** 
	 * 获得getLocation方法中 
	 * manager.requestLocationUpdates(provider, minTime, minDistance, listener) 
	 * 中的provider，即定位设备 
	 * @param manager 位置管理服务 
	 * @return 最好的位置提供者 
	 */  
	private String getProvider(LocationManager manager) {  
		Criteria criteria = new Criteria();  
		// 设置精准度  
		criteria.setAccuracy(Criteria.ACCURACY_FINE);  
		// 设置是否对海拔敏感  
		criteria.setAltitudeRequired(false);  
		// 设置对手机的耗电量，定位要求越高，越耗电    
		criteria.setPowerRequirement(Criteria.POWER_MEDIUM);  
		//设置对速度变化是否敏感   
		criteria.setSpeedRequired(true);  
		//设置在定位时，是否允许产生与运营商交换数据的开销  
		criteria.setCostAllowed(true);  
		//这个方法是用来得到最好的定位方式，它有两个参数  
		//1、Criteria(类似于Map集合)，一组关于定位的条件，速度、海拔、耗电量等  
		//2、enableOnly，布尔类型，false，有可能是已经关掉了的设备；true，就只会得到已经打开了的设备。  
		//如果手机中的GPS设备已经关闭，那么如果设置为false，则手机有可能仍然使用GPS设备提供定位，  
		//如果为true，则手机将不适应关闭的GPS设备定位，而是使用手机中开启的网络或其他设备提供定位  
		return manager.getBestProvider(criteria, true);  
	}  
}
