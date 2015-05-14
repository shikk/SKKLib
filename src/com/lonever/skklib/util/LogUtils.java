/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */
package com.lonever.skklib.util;

import android.util.Log;

/**
 * 日志类，使用方法
 * <p>
 * <blockquote>
 * 
 * <pre>
 * LogUtils.enableLogging(true);
 * LogUtils.setLevel(Log.DEBUG);
 * ...
 * LogUtils.d("test", "test");
 * </pre>
 * 
 * </blockquote>
 * </p>
 * 
 * @author Gary Fu
 */
public class LogUtils {
	private static boolean isShowLog = false;
	private static int     level     = Log.VERBOSE;
	private static String  tag       = "BaseLib";

	/**
	 * 设置是否输出日志
	 * 
	 * @param enable
	 *            ture 输出；false 不输出
	 */
	public static void enableLogging(boolean enable) {
		isShowLog = enable;
	}

	/**
	 * 返回目前的日志输出级别
	 * 
	 * @return 返回日志输出级别，例如{@link android.util.Log Log.DEBUG}
	 */
	public static int getLevel() {
		return level;
	}

	/**
	 * 判断日志是否输出
	 * 
	 * @return 返回日志是否输出的状态
	 */
	public static boolean isLogEnabled() {
		return isShowLog;
	}

	/**
	 * 设置输出等级
	 * 
	 * @param level
	 *            输出等级(例如:{@link android.util.Log Log.DEBUG})，只有高于或等于这个等级的才会最终输出
	 */
	public static void setLevel(int level) {
		LogUtils.level = level;
	}

	public static void setTag(String tag) {
		LogUtils.tag = tag;
	}

	public static void d(String tag, String msg) {
		if (isShowLog && Log.DEBUG >= level)
			Log.d(tag, msg);
	}

	public static void d(String msg) {
		if (isShowLog && Log.DEBUG >= level)
			Log.d(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (isShowLog && Log.ERROR >= level)
			Log.e(tag, msg);
	}

	public static void e(String msg) {
		if (isShowLog && Log.ERROR >= level)
			Log.e(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (isShowLog && Log.VERBOSE >= level)
			Log.v(tag, msg);
	}

	public static void v(String msg) {
		if (isShowLog && Log.VERBOSE >= level)
			Log.v(tag, msg);
	}

	public static void w(String tag, String msg) {
		if (isShowLog && Log.WARN >= level)
			Log.w(tag, msg);
	}

	public static void w(String msg) {
		if (isShowLog && Log.WARN >= level)
			Log.w(tag, msg);
	}

	public static void i(String tag, String msg) {
		if (isShowLog && Log.INFO >= level)
			Log.i(tag, msg);
	}

	public static void i(String msg) {
		if (isShowLog && Log.INFO >= level)
			Log.i(tag, msg);
	}
}
