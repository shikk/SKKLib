/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */
package com.lonever.skklib.cache;

import java.util.List;

/**
 * Cache类
 * 
 * @author Gary Fu
 */
public interface Cache {
	/**
	 * Clear the cache
	 * 
	 * @throws CacheException
	 */
	public void clear() throws CacheException;

	/**
	 * Clean up
	 * 
	 * @throws CacheException
	 */
	public void destroy() throws CacheException;

	/**
	 * Get an item from the cache, nontransactionally
	 * 
	 * @param key
	 * @return the cached object or <tt>null</tt>
	 * @throws CacheException
	 */
	public Object get(Object key) throws CacheException;

	/**
	 * test has object in cache
	 * 
	 * @param key
	 * @return true has this key; false hasn't this key
	 * @throws CacheException
	 */
	public boolean has(Object key) throws CacheException;

	/**
	 * get keys
	 * 
	 * @return keys
	 * @throws CacheException
	 */
	@SuppressWarnings("rawtypes")
	public List keys() throws CacheException;

	/**
	 * get cache memory size
	 * 
	 * @return memory size used by cache
	 * @throws CacheException
	 */
	public long memSize() throws CacheException;

	/**
	 * Add an item to the cache, nontransactionally, with failfast semantics,
	 * and never expired
	 * 
	 * @param key
	 * @param value
	 * @throws CacheException
	 */
	public void put(Object key, Object value) throws CacheException;

	/**
	 * Add an item to the cache, nontransactionally, with failfast semantics,
	 * and set expiration time
	 * 
	 * @param key
	 * @param value
	 * @param expirTime
	 *            : 2s / 20m / 2h
	 * @throws CacheException
	 */
	public void put(Object key, Object value, String expirTime) throws CacheException;

	/**
	 * Remove an item from the cache
	 * 
	 * @param key
	 * @throws CacheException
	 */
	public void remove(Object key) throws CacheException;

	/**
	 * get cache size
	 * 
	 * @return cache size
	 * @throws CacheException
	 */
	public int size() throws CacheException;
}
