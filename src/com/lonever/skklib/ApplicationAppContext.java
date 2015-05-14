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
 * 通过Application方式实现{@link cn.wap3.base.AppContext AppContext}接口的全局服务类（唯一实例）<br/>
 * 具体有两种实现方式：<br/>
 * （一）直接使用<br/>
 * 首先在项目文件 AndroidManifest.xml 的 application 标签中添加属性
 * android:name="cn.wap3.base.ApplicationAppContext" <blockquote>
 *
 * <pre>
 * {@code <application android:name="cn.wap3.base.ApplicationAppContext" android:icon="@drawable/icon" android:label="@string/app_name">}
 * </pre>
 *
 * </blockquote> 然后在Activity中可通过如下方式获得唯一实例 <blockquote>
 *
 * <pre>
 * ApplicationAppContext applicationAppContext = (ApplicationAppContext) getApplicationContext();
 * </pre>
 *
 * </blockquote> <br/>
 * （二）继承使用<br/>
 * 首先要实现类并继承自本类 <blockquote>
 *
 * <pre>
 * public class App extends ApplicationAppContext {
 * }
 * </pre>
 *
 * </blockquote> 其次要在项目文件 AndroidManifest.xml 的 application 标签中添加属性
 * android:name=".App" <blockquote>
 *
 * <pre>
 * {@code <application android:name=".App" android:icon="@drawable/icon" android:label="@string/app_name">}
 * </pre>
 *
 * </blockquote> 最后在Activity中可通过如下方式获得唯一实例 <blockquote>
 *
 * <pre>
 * App app = (App) getApplicationContext();
 * </pre>
 *
 * </blockquote> <br/>
 * 其他，如果需要数据库，请在应用的第一个Activity的onCreate()方法中初始化一下数据库，以继承方式来说，如下 <blockquote>
 *
 * <pre>
 * {@link com.lonever.skklib.db.DbInfo DbInfo} dbInfo = new {@link com.lonever.skklib.db.DbInfo DbInfo}();
 * ...... 设置数据库相关信息 ......
 * {@link com.lonever.skklib.db.DbProvider DbProvider} dbProvider = new {@link com.lonever.skklib.db.DbProvider DbProvider}(app.getApplicationContext(), dbInfo);
 * app.initDb(dbProvider);
 * </pre>
 *
 * </blockquote> 在自己的应用中，请用继承自{@link com.lonever.skklib.db.DbProvider DbProvider}
 * 的类替换上面的DbProvider
 *
 * @author Gary Fu
 */
public class ApplicationAppContext extends Application implements AppContext {
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
			connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		return connectivityManager;
	}

	/**
	 * @see cn.wap3.base.AppContext#getGlobalPackageManager()
	 */
	@Override
	public PackageManager getGlobalPackageManager() {
		if (packageManager == null) {
			packageManager = this.getPackageManager();
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
	 * @see cn.wap3.base.AppContext#getApplicationContext()
	 */
	@Override
	public Context getApplicationContext() {
		return this;
	}

	/**
	 * @see cn.wap3.base.AppContext#getNotificationManager()
	 */
	@Override
	public NotificationManager getNotificationManager() {
		if (notificationManager == null) {
			notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		}
		return notificationManager;
	}

	/**
	 * @see cn.wap3.base.AppContext#getTelephonyManager()
	 */
	@Override
	public TelephonyManager getTelephonyManager() {
		if (telephonyManager == null) {
			telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		}
		return telephonyManager;
	}

	/**
	 * @see cn.wap3.base.AppContext#getLocationManager()
	 */
	@Override
	public LocationManager getLocationManager() {
		if (locationManager == null) {
			locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
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
				packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
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
				appInfo = packageManager.getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
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
			wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		}
		return wifiManager;
	}

	/**
	 * @see cn.wap3.base.AppContext#getWindowManager()
	 */
	@Override
	public WindowManager getWindowManager() {
		if (windowManager == null) {
			windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		}
		return windowManager;
	}
}
