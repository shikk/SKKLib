package com.lonever.skklib;
/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */


import com.lonever.skklib.cache.CacheProvider;
import com.lonever.skklib.db.DbProvider;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

/**
 * 代表一个App全局服务类的抽象接口
 * 
 * @author Gary Fu
 */
public interface AppContext {
	/**
	 * 获得唯一的CacheProvider实例
	 * 
	 * @return {@link com.lonever.skklib.cache.CacheProvider CacheProvider}
	 */
	public CacheProvider getCacheProvider();

	/**
	 * 初始化数据库
	 * 
	 * @param dbProvider
	 *            构造一个{@link com.lonever.skklib.db.DbProvider DbProvider}传入
	 */
	public void initDb(DbProvider dbProvider);

	/**
	 * 获得唯一的DbProvider实例 在自己的应用中，请转换为继承自{@link com.lonever.skklib.db.DbProvider
	 * DbProvider}的类 <blockquote>
	 * 
	 * <pre>
	 * MyProvider myProvider = (MyProvider) appContext.getDbProvider();
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @return 返回DbProvider实例
	 */
	public DbProvider getDbProvider();

	/**
	 * 获得唯一的ConnectivityManager实例
	 * 
	 * @return 返回ConnectivityManager实例
	 */
	public ConnectivityManager getConnectivityManager();

	/**
	 * 获得唯一的NotificationManager实例
	 * 
	 * @return 返回NotificationManager实例
	 */
	public NotificationManager getNotificationManager();

	/**
	 * 获得唯一的TelephonyManager实例
	 * 
	 * @return 返回TelephonyManager实例
	 */
	public TelephonyManager getTelephonyManager();

	/**
	 * 获得唯一的LocationManager实例
	 * 
	 * @return 返回LocationManager实例
	 */
	public LocationManager getLocationManager();

	/**
	 * 获得唯一的PackageManager实例
	 * 
	 * @return 返回PackageManager实例
	 */
	public PackageManager getGlobalPackageManager();

	/**
	 * 获得唯一的WindowManager实例
	 * 
	 * @return 返回WindowManager实例
	 */
	public WindowManager getWindowManager();

	/**
	 * 获得唯一的WifiManager实例
	 * 
	 * @return 返回WifiManager实例
	 */
	public WifiManager getWifiManager();

	/**
	 * 获得全局Context
	 * 
	 * @return 返回全局Context
	 */
	public Context getApplicationContext();

	/**
	 * 获取配置参数信息
	 */
	public void fetchData();

	/**
	 * 获得sid
	 * 
	 * @return 返回sid
	 */
	public int getSid();

	/**
	 * 获得cid
	 * 
	 * @return 返回cid
	 */
	public int getCid();

	/**
	 * 获得versionCode
	 * 
	 * @return 返回versionCode
	 */
	public int getVersionCode();
}
