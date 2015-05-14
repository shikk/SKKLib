/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */
package com.lonever.skklib.util;

import java.util.Random;

/**
 * 字符串处理工具类
 * 
 * @author Gary Fu
 */
public class StringUtils {
	// 生成机器唯一ID，用做自动生成的userId
	// public static String getUid(Context context) {
	// TelephonyManager tm =
	// (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	// return tm.getDeviceId();
	// }

	/**
	 * 检查字符串是否为空
	 * 
	 * @param s
	 *            要检查的字符串
	 * @return null/空字符串/全空格字符串 均返回true，其他返回false
	 */
	public static boolean isBlank(String s) {
		if (s == null) {
			return true;
		}
		if (s.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 检查字符串是否不为空
	 * 
	 * @param s
	 *            要检查的字符串
	 * @return null/空字符串/全空格字符串 均返回false，其他返回true
	 */
	public static boolean isNotBlank(String s) {
		if (s == null) {
			return false;
		}
		if (s.trim().length() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 将正常url解析为cmwap联网需要的domain和path两部分
	 * 
	 * @param urlStr
	 *            要解析的url
	 * 
	 * @return 解析好的含domain和path两部分的数组，index=0是domain,
	 *         index=1是path，如发生异常，两个都返回空字符串
	 */
	public static String[] parseUrlForCmwap(String urlStr) {
		String[] ss = new String[] { "", "" };

		int index = urlStr.indexOf("http://");
		if (index >= 0) {
			urlStr = urlStr.substring(index + 7);
			index = urlStr.indexOf("/");
			if (index > 0) {
				ss[0] = urlStr.substring(0, index);
				ss[1] = urlStr.substring(index);
			} else {
				ss[0] = urlStr;
				ss[1] = "/";
			}
		}

		return ss;
	}

	public static final int RANDOM_ALL       = 1;
	public static final int RANDOM_CHARACTER = 2;
	public static final int RANDOM_DIGIT     = 3;

	/**
	 * 获得随机字符串
	 * 
	 * @param length
	 *            随机字符串的长度
	 * @param type
	 *            随机字符串的类型
	 * 
	 * @return 随机字符串
	 */
	public static String getRandomString(int length, int type) {
		StringBuffer sb = new StringBuffer();
		int range = 0;
		if (type == RANDOM_ALL) {
			range = ALL_BUFFER.length();
		} else if (type == RANDOM_CHARACTER) {
			range = C_BUFFER.length();
		} else if (type == RANDOM_DIGIT) {
			range = D_BUFFER.length();
		}

		for (int i = 0; i < length; i++) {
			if (type == RANDOM_ALL) {
				sb.append(ALL_BUFFER.charAt(random.nextInt(range)));
			} else if (type == RANDOM_CHARACTER) {
				sb.append(C_BUFFER.charAt(random.nextInt(range)));
			} else if (type == RANDOM_DIGIT) {
				sb.append(D_BUFFER.charAt(random.nextInt(range)));
			}
		}

		return sb.toString();
	}

	private static Random             random     = new Random();
	private static final StringBuffer D_BUFFER   = new StringBuffer("0123456789");
	private static final StringBuffer C_BUFFER   = new StringBuffer(
	                                                     "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
	private static final StringBuffer ALL_BUFFER = new StringBuffer(
	                                                     "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
}
