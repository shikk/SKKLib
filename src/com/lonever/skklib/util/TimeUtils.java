/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */
package com.lonever.skklib.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间处理工具类
 * 
 * @author Gary Fu
 */
public class TimeUtils {
	/**
	 * 按格式输出时间
	 * 
	 * @param date
	 *            需要输出的时间
	 * @param formatString
	 *            输出的格式，参照{@link java.text.SimpleDateFormat SimpleDateFormat}
	 * @return 按格式输出的时间
	 */
	public static String formatDate(Date date, String formatString) {
		if (StringUtils.isBlank(formatString)) {
			formatString = "yyyy-MM-dd HH:mm:ss";
		}
		Format format = new SimpleDateFormat(formatString);
		return format.format(date);
	}

	/**
	 * 返回两个 java.util.Date 对象之间相差的秒数
	 * 
	 * @param prevDate
	 *            第一个 java.util.Date 对象
	 * @param prevDate
	 *            第二个 java.util.Date 对象
	 * @return 相差的秒数，负数表示第一个比第二个所表示的时间要晚
	 */
	public static long getIntervalInSecond(Date prevDate, Date nextDate) {
		if (prevDate == null || nextDate == null) {
			throw new IllegalArgumentException();
		}
		return (nextDate.getTime() - prevDate.getTime()) / 1000;
	}

	/**
	 * 将字符串所含的时间格式转为实际的秒数 目前支持的格式 "2s" 秒 / "2m" 分钟 / "2h" 小时
	 * 
	 * @param time
	 *            含时间格式的字符串
	 * @return 时间格式对应的秒数
	 * @throws IllegalArgumentException
	 *             不支持的时间格式
	 */
	public static long parseToSecond(String time) throws IllegalArgumentException {
		if (StringUtils.isBlank(time)) {
			return 0;
		}
		time = time.trim().toLowerCase();
		long numPrefix = 0;
		if (time.length() == 1) {
			try {
				numPrefix = Long.parseLong(time);
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException();
			}
		} else {
			try {
				numPrefix = Long.parseLong(time.substring(0, time.length() - 1));
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException();
			}
			if (time.endsWith("s")) {
			} else if (time.endsWith("m")) {
				numPrefix *= 60;
			} else if (time.endsWith("h")) {
				numPrefix *= 3600;
			} else {
				throw new IllegalArgumentException();
			}
		}
		return numPrefix;
	}
}
