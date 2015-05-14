/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */
package com.lonever.skklib.cache;

/**
 * CacheProvider Interface
 * 
 * @author Gary Fu
 */
public interface CacheProvider {

	/**
	 * Build the cache
	 * 
	 * @param cacheClassName
	 *            the cache class name which you want initialize
	 * @param regionName
	 *            the name of the cache region
	 * @param autoCreate
	 *            autoCreate settings
	 * @throws CacheException
	 */
	public Cache buildCache(String cacheClassName, String regionName, boolean autoCreate) throws CacheException;

	/**
	 * remove the cache
	 * 
	 * @param regionName
	 *            the name of the cache region
	 * @throws CacheException
	 */
	public void removeCache(String regionName) throws CacheException;

	/**
	 * start cache
	 * 
	 * @throws CacheException
	 */
	public void start() throws CacheException;

	/**
	 * stop cache
	 */
	public void stop();

}
