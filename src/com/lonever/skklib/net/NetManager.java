/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */
package com.lonever.skklib.net;

import java.net.InetSocketAddress;
import java.net.Proxy;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

// import com.sun.org.apache.bcel.internal.generic.RETURN;

/**
 * 网络处理工具类
 *
 * @author Gary Fu
 */
public class NetManager {
	static {
		setDefaultHostnameVerifier();
	}

	// 下载一个文件，要能获取下载的百分比
	// public static void uploadFile() {}
	// 上传一个文件，要能获取上传的百分比
	// public static void downloadFile() {}

	/**
	 * 代表是未知网络
	 */
	public static final int NETWORK_TYPE_UNKNOWN = -1;
	/**
	 * 代表是WIFI网络
	 */
	public static final int NETWORK_TYPE_WIFI    = 1;
	/**
	 * 代表是CMNET网络
	 */
	public static final int NETWORK_TYPE_CMNET   = 2;
	/**
	 * 代表是CMWAP网络
	 */
	public static final int NETWORK_TYPE_CMWAP   = 3;

	/**
	 * 获得手机当前上网的网络方式，需要权限 android.permission.ACCESS_NETWORK_STATE
	 *
	 * @return {@link cn.wap3.net.NetManager#NETWORK_TYPE_WIFI WIFI};
	 *         {@link cn.wap3.net.NetManager#NETWORK_TYPE_CMNET CMNET};
	 *         {@link cn.wap3.net.NetManager#NETWORK_TYPE_CMWAP CMWAP};
	 *         {@link cn.wap3.net.NetManager#NETWORK_TYPE_UNKNOWN UNKNOWN}
	 */
	public static int checkNetworkType(Context context) {
		if (context == null) {
			// throw new IllegalArgumentException();
			return NETWORK_TYPE_UNKNOWN;
		}

		NetworkInfo networkInfo = getConnectivityManager(context).getActiveNetworkInfo();

		if (networkInfo != null) {
		}

		if (networkInfo != null
		        && networkInfo.getTypeName() != null
		        && networkInfo.getTypeName().equalsIgnoreCase("mobile")
		        && (networkInfo.getExtraInfo().equalsIgnoreCase("cmnet") || networkInfo.getExtraInfo()
		                .equalsIgnoreCase("3gnet"))) {
			return NETWORK_TYPE_CMNET;
		} else if (networkInfo != null
		        && networkInfo.getTypeName() != null
		        && networkInfo.getTypeName().equalsIgnoreCase("mobile")
		        && (networkInfo.getExtraInfo().equalsIgnoreCase("cmwap") || networkInfo.getExtraInfo()
		                .equalsIgnoreCase("3gwap"))) {
			return NETWORK_TYPE_CMWAP;
		} else {
			if (networkInfo != null && networkInfo.getTypeName() != null && networkInfo.getTypeName().equals("WIFI")) {
				return NETWORK_TYPE_WIFI;
			}
			return NETWORK_TYPE_UNKNOWN;
		}
	}

	/**
	 * 根据手机当前上网的方式获得 Proxy ，需要权限 android.permission.ACCESS_NETWORK_STATE
	 *
	 * @param appContext
	 *            {@link cn.wap3.base.AppContext appContext}
	 * @return {@link java.net.Proxy Proxy}
	 */
	public static Proxy detectProxy(Context context) {
		if (context == null) {
			return null;
		}

		Proxy proxy = null;
		ConnectivityManager cm = getConnectivityManager(context);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isAvailable() && ni.getType() == ConnectivityManager.TYPE_MOBILE) {
			String proxyHost = android.net.Proxy.getDefaultHost();
			int port = android.net.Proxy.getDefaultPort();
			if (proxyHost != null) {
				final InetSocketAddress sa = new InetSocketAddress(proxyHost, port);
				proxy = new Proxy(Proxy.Type.HTTP, sa);
			}
		}
		return proxy;
	}

	private static void setDefaultHostnameVerifier() {
		//
		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		HttpsURLConnection.setDefaultHostnameVerifier(hv);
	}

	private static ConnectivityManager connectivityManager;
	
	private static ConnectivityManager getConnectivityManager(Context context) {
		if (connectivityManager == null) {
			connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		return connectivityManager;
	}
}
