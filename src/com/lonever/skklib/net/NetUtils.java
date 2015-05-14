package com.lonever.skklib.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetUtils {

	static {
		setDefaultHostnameVerifier();
	}
	/**
	 * 代表是未知网络
	 */
	public static final int NETWORK_TYPE_UNKNOWN = -1;
	/**
	 * 代表是WIFI网络
	 */
	public static final int NETWORK_TYPE_WIFI = 0;
	/**
	 * 代表是WIFI网络
	 */
	public static final int NETWORK_TYPE_ETHERNET = 6;
	/**
	 * 代表是CMNET网络
	 */
	public static final int NETWORK_TYPE_CMNET = 1;
	/**
	 * 代表是CMWAP网络
	 */
	public static final int NETWORK_TYPE_CMWAP = 2;

	public static final int NETWORK_TYPE_3GNET = 3;
	/**
	 * 代表是CMWAP网络
	 */
	public static final int NETWORK_TYPE_3GWAP = 4;

	public static final int NETWORK_CLASS_UNKNOWN = -1;
	public static final int NETWORK_CLASS_2_G = 1;
	public static final int NETWORK_CLASS_3_G = 2;
	public static final int NETWORK_CLASS_4_G = 3;

	public static int getAPNType(Context appContext) {
		if (appContext == null) {
			// throw new IllegalArgumentException();
			return NETWORK_TYPE_UNKNOWN;
		}

		NetworkInfo networkInfo = getConnectivityManager(appContext)
				.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.getTypeName() != null
				&& networkInfo.getTypeName().equalsIgnoreCase("mobile")) {
			if (networkInfo.getExtraInfo().equalsIgnoreCase("cmwap")) {
				return NETWORK_TYPE_CMWAP;
			} else if (networkInfo.getExtraInfo().equalsIgnoreCase("cmnet")) {
				return NETWORK_TYPE_CMNET;
			} else if (networkInfo.getExtraInfo().equalsIgnoreCase("3gnet")) {
				return NETWORK_TYPE_3GNET;
			} else if (networkInfo.getExtraInfo().equalsIgnoreCase("3gwap")) {
				return NETWORK_TYPE_3GWAP;
			}
		}
		if (networkInfo != null && networkInfo.getTypeName() != null
				&& networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return NETWORK_TYPE_WIFI;
		}
		if (networkInfo != null && networkInfo.getTypeName() != null
				&& networkInfo.getType() == 9) {
			return NETWORK_TYPE_ETHERNET;
		}
		return NETWORK_TYPE_UNKNOWN;
	}

	public static boolean isDataAvailable(Context appContext) {
		String urlString = "http://www.baidu.com";
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn;
			if (NetUtils.getAPNType(appContext
					.getApplicationContext()) == NetUtils.NETWORK_TYPE_CMWAP) {// 如果是wap方式，要加网关
				java.net.Proxy p = new java.net.Proxy(java.net.Proxy.Type.HTTP,
						new InetSocketAddress("10.0.0.172", 80));
				url = new URL("http://10.0.0.172/");
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("X-Online-Host", "www.baidu.com");
			} else {
				conn = (HttpURLConnection) url.openConnection();
			}
			conn.connect();
			return true;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static int getNetWorkType(Context context) {

		return getTelephonyManager(context).getNetworkType();
	}

	public static int getNetWorkClass(int netWorkType) {
		switch (netWorkType) {
		case TelephonyManager.NETWORK_TYPE_GPRS:
		case TelephonyManager.NETWORK_TYPE_EDGE:
		case TelephonyManager.NETWORK_TYPE_CDMA:
		case TelephonyManager.NETWORK_TYPE_1xRTT:
		case 11:// TelephonyManager.NETWORK_TYPE_IDEN
			return NETWORK_CLASS_2_G;
		case TelephonyManager.NETWORK_TYPE_UMTS:
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
		case TelephonyManager.NETWORK_TYPE_HSDPA:
		case TelephonyManager.NETWORK_TYPE_HSUPA:
		case TelephonyManager.NETWORK_TYPE_HSPA:
		case 12:// TelephonyManager.NETWORK_TYPE_EVDO_B
		case 14:// TelephonyManager.NETWORK_TYPE_EHRPD
		case 15:// TelephonyManager.NETWORK_TYPE_HSPAP
			return NETWORK_CLASS_3_G;
		case 13://
			return NETWORK_CLASS_4_G;
		default:
			return NETWORK_CLASS_UNKNOWN;
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
	private static TelephonyManager telephonyManager;
	
	
	private static ConnectivityManager getConnectivityManager(Context context) {
		if (connectivityManager == null) {
			connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		return connectivityManager;
	}
	
	public static TelephonyManager getTelephonyManager(Context context) {
		if (telephonyManager == null) {
			telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		}
		return telephonyManager;
	}
}
