/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */
package com.lonever.skklib.cache;

import java.util.Date;

/**
 * cache object wrapper类
 * 
 * @author Gary Fu
 */
public class CacheObjectWrapper {
	private Object object;
	private Date   refreshTime;
	private long   expirTime = 0; // 0 - never expire

	/**
	 * 构造一个Wrapper类
	 * 
	 * @param object
	 *            要cache的object
	 * @param refreshTime
	 *            当前时间
	 * @param expirTime
	 *            object存活时间，单位秒
	 */
	public CacheObjectWrapper(Object object, Date refreshTime, long expirTime) {
		this.object = object;
		if (refreshTime == null) {
			this.refreshTime = new Date();
		} else {
			this.refreshTime = refreshTime;
		}
		this.expirTime = expirTime;
	}

	/**
	 * @return the expirTime
	 */
	public long getExpirTime() {
		return expirTime;
	}

	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @return the refreshTime
	 */
	public Date getRefreshTime() {
		return refreshTime;
	}

	/**
	 * @param expirTime
	 *            the expirTime to set
	 */
	public void setExpirTime(long expirTime) {
		this.expirTime = expirTime;
	}

	/**
	 * @param object
	 *            the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * @param refreshTime
	 *            the refreshTime to set
	 */
	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

}
