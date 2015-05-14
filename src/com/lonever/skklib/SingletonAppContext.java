package com.lonever.skklib;
/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */


import com.lonever.skklib.cache.CacheProvider;
import com.lonever.skklib.cache.MemCacheProvider;
import com.lonever.skklib.db.DbProvider;
import com.lonever.skklib.util.LogUtils;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

/**
 * 通过Singleton方式实现{@link cn.wap3.base.AppContext AppContext}接口的全局服务类（唯一实例）<br/>
 * 在自己的应用中，如需扩展，请继承自本类，并扩展方法
 * 
 * @author Gary Fu
 */
public class SingletonAppContext implements AppContext {
	private Context context;

	/**
	 * 初始化全局服务类，请务必在应用的第一个Activity中的onCreate()方法的最前面调用
	 * 
	 * @param context
	 *            请务必传入应用的全局Context，而不要使用Activity的Context
	 */
	public void init(Context context) {
		if (!(context instanceof Application)) {
			throw new IllegalArgumentException("context must be application context");
		}
		this.context = context;
	}

	/**
	 * @see cn.wap3.base.AppContext#getApplicationContext()
	 */
	@Override
	public Context getApplicationContext() {
		return context;
	}

	private CacheProvider       cacheProvider;
	private DbProvider          dbProvider;
	private ConnectivityManager connectivityManager;
	private NotificationManager notificationManager;
	private TelephonyManager    telephonyManager;
	private LocationManager     locationManager;
	private PackageManager      packageManager;
	private WifiManager         wifiManager;
	private WindowManager       windowManager;

	private boolean             isDataFetched = false;
	private int                 sid           = 0;
	private int                 cid           = 0;
	private int                 versionCode   = 0;

	/**
	 * @see cn.wap3.base.AppContext#getCacheProvider()
	 */
	@Override
	public CacheProvider getCacheProvider() {
		if (cacheProvider == null) {
			cacheProvider = new MemCacheProvider();
		}
		return cacheProvider;
	}

	/**
	 * @see cn.wap3.base.AppContext#getConnectivityManager()
	 */
	@Override
	public ConnectivityManager getConnectivityManager() {
		if (connectivityManager == null) {
			connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		return connectivityManager;
	}

	/**
	 * @see cn.wap3.base.AppContext#getGlobalPackageManager()
	 */
	@Override
	public PackageManager getGlobalPackageManager() {
		if (packageManager == null) {
			packageManager = context.getPackageManager();
		}
		return packageManager;
	}

	/**
	 * @see cn.wap3.base.AppContext#initDb(com.lonever.skklib.db.DbProvider)
	 */
	@Override
	public void initDb(DbProvider dbProvider) {
		this.dbProvider = dbProvider;
	}

	/**
	 * @see cn.wap3.base.AppContext#getDbProvider()
	 */
	@Override
	public DbProvider getDbProvider() {
		return dbProvider;
	}

	/**
	 * @see cn.wap3.base.AppContext#getNotificationManager()
	 */
	@Override
	public NotificationManager getNotificationManager() {
		if (notificationManager == null) {
			notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		}
		return notificationManager;
	}

	/**
	 * @see cn.wap3.base.AppContext#getTelephonyManager()
	 */
	@Override
	public TelephonyManager getTelephonyManager() {
		if (telephonyManager == null) {
			telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		}
		return telephonyManager;
	}

	/**
	 * @see cn.wap3.base.AppContext#getLocationManager()
	 */
	@Override
	public LocationManager getLocationManager() {
		if (locationManager == null) {
			locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		}
		return locationManager;
	}

	/**
	 * @see cn.wap3.base.AppContext#fetchData()
	 */
	@Override
	public void fetchData() {
		if (isDataFetched) {
			return;
		}

		PackageManager packageManager = getGlobalPackageManager();
		if (packageManager != null) {
			PackageInfo packageInfo = null;
			try {
				packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			} catch (NameNotFoundException e) {
				// e.printStackTrace();
				LogUtils.e("BaseLib", e.getMessage());
			}
			if (packageInfo != null) {
				versionCode = packageInfo.versionCode;
			} else {
				LogUtils.e("BaseLib", "packageInfo is null");
			}

			ApplicationInfo appInfo = null;
			try {
				appInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			} catch (NameNotFoundException e) {
				// e.printStackTrace();
				LogUtils.e("BaseLib", e.getMessage());
			}
			if (appInfo != null) {
				Bundle bundle = appInfo.metaData;
				if (bundle != null) {
					sid = bundle.getInt("SID");
					cid = bundle.getInt("UMENG_CHANNEL");
					LogUtils.i("BaseLib", "获得ID => SID:" + sid + " / CID:" + cid);
				} else {
					LogUtils.e("BaseLib", "appInfo.metaData is null");
				}
			} else {
				LogUtils.e("BaseLib", "appInfo is null");
			}
		}

		isDataFetched = true;
	}

	/**
	 * @see cn.wap3.base.AppContext#getSid()
	 */
	@Override
	public int getSid() {
		return sid;
	}

	/**
	 * @see cn.wap3.base.AppContext#getCid()
	 */
	@Override
	public int getCid() {
		return cid;
	}

	/**
	 * @see cn.wap3.base.AppContext#getVersionCode()
	 */
	@Override
	public int getVersionCode() {
		return versionCode;
	}

	/**
	 * @see cn.wap3.base.AppContext#getWifiManager()
	 */
	@Override
	public WifiManager getWifiManager() {
		if (wifiManager == null) {
			wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		}
		return wifiManager;
	}

	/**
	 * @see cn.wap3.base.AppContext#getWindowManager()
	 */
	@Override
	public WindowManager getWindowManager() {
		if (windowManager == null) {
			windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		}
		return windowManager;
	}
}
