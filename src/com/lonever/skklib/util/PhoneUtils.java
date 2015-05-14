/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */
package com.lonever.skklib.util;

import com.lonever.skklib.AppContext;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.DisplayMetrics;

/**
 * 获取手机相关信息，必须获得相关权限。
 * 
 * @author Dengjy
 */
public class PhoneUtils {

	/**
	 * 获得手机屏幕高度
	 * 
	 * @param activity
	 *            activity
	 * @return 屏幕高度
	 */
	public static int getDefaultDisplayHeight(Activity activity) {
		return activity.getWindowManager().getDefaultDisplay().getHeight();
	}

	/**
	 * 获得手机屏幕宽度
	 * 
	 * @param activity
	 *            activity
	 * @return 屏幕宽度
	 */
	public static int getDefaultDisplayWidth(Activity activity) {
		return activity.getWindowManager().getDefaultDisplay().getWidth();
	}

	/**
	 * 用 DisplayMetrics 方式获得手机屏幕高度
	 * 
	 * @param activity
	 *            activity
	 * @return 屏幕高度，单位像素
	 */
	public static int getDisplayMetricsHeight(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * 用 DisplayMetrics 方式获得手机屏幕宽度
	 * 
	 * @param activity
	 *            activity
	 * @return 屏幕宽度，单位像素
	 */
	public static int getDisplayMetricsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 获得手机 IMEI
	 * 
	 * @param appContext
	 *            {@link cn.wap3.base.AppContext appContext}
	 * @return IMEI
	 * @throws IllegalArgumentException
	 */
	public static String getIMEI(AppContext appContext) {
		if (appContext == null) {
			throw new java.lang.IllegalArgumentException();
		}
		return appContext.getTelephonyManager().getDeviceId();
	}
	
	/**
	 * 
	 * @param appContext
	 * 			{@link cn.wap3.base.AppContext appContext}
	 * 
	 * @return IMSI
	 * @throws IllegalArgumentException
	 */
	public static String getIMSI(AppContext appContext){
		if (appContext == null) {
			throw new java.lang.IllegalArgumentException();
		}
		return appContext.getTelephonyManager().getSubscriberId();
	}

	/**
	 * 获得手机 wifi 上网的 ip 地址
	 * 
	 * @param appContext
	 *            {@link cn.wap3.base.AppContext appContext}
	 * @return ip 地址
	 * @throws IllegalArgumentException
	 */
	public static String getIp(AppContext appContext) {
		if (appContext == null) {
			throw new java.lang.IllegalArgumentException();
		}
		WifiInfo wifiInfo = appContext.getWifiManager().getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();

		// 格式化IP address，例如：格式化前：1828825280，格式化后：192.168.1.109
		String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
		        (ipAddress >> 24 & 0xff));
		return ip;
	}

	/**
	 * 获得手机 model 信息
	 * 
	 * @return model
	 */
	public static String getModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获得手机 os 信息
	 * 
	 * @return os
	 */
	public static String getOSVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获得手机号码
	 * 
	 * @param appContext
	 *            {@link cn.wap3.base.AppContext appContext}
	 * @return 手机号码
	 * @throws IllegalArgumentException
	 */
	public static String getPhoneNum(AppContext appContext) {
		if (appContext == null) {
			throw new java.lang.IllegalArgumentException();
		}
		return appContext.getTelephonyManager().getLine1Number();
	}

	/**
	 * 用 DisplayMetrics 方式获得手机屏幕高度，并经过密度换算
	 * 
	 * @param activity
	 * @return 屏幕高度，单位像素
	 */
	public static float getScreenHeight(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		float density = dm.density; // 320*480时为1.0,480*800时为1.5
		float screenHeight = dm.heightPixels * density;
		return screenHeight;
	}

	/**
	 * 用 DisplayMetrics 方式获得手机屏幕宽度，并经过密度换算
	 * 
	 * @param activity
	 * @return 屏幕宽度，单位像素
	 */
	public static float getScreenWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		float density = dm.density; // 320*480时为1.0,480*800时为1.5
		float screenWidth = dm.widthPixels * density;
		return screenWidth;
	}
	
	
	public static String getLocalMacAddress(Activity activity) {  
        WifiManager wifi = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);  
        WifiInfo info = wifi.getConnectionInfo();  
        return info.getMacAddress();  
    } 
	
	
}
