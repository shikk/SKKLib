/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */
package com.lonever.skklib.cache;

import java.util.HashMap;

/**
 * 内存CacheProvider
 * 
 * @author Gary Fu
 */
public class MemCacheProvider implements CacheProvider {
	private static HashMap<String, Cache> regionCache = new HashMap<String, Cache>();

	/**
	 * @see com.lonever.skklib.cache.CacheProvider#buildCache(java.lang.String,
	 *      java.lang.String, boolean)
	 */
	@Override
	public Cache buildCache(String cacheClassName, String regionName, boolean autoCreate) throws CacheException {
		MemCache memCache = null;
		if (regionCache.containsKey(regionName)) {
			memCache = (MemCache) regionCache.get(regionName);
		} else {
			if (autoCreate) {
				try {
					memCache = (MemCache) Class.forName(cacheClassName).newInstance();
				} catch (ClassNotFoundException cnfe) {
					throw new CacheException(cacheClassName + " not found!");
				} catch (InstantiationException ie) {
					throw new CacheException(cacheClassName + " initialize failed!");
				} catch (IllegalAccessException iae) {
					throw new CacheException(cacheClassName + " can not access!");
				}
				regionCache.put(regionName, memCache);
			}
		}
		return memCache;
	}

	/**
	 * @see com.lonever.skklib.cache.CacheProvider#removeCache(java.lang.String)
	 */
	@Override
	public void removeCache(String regionName) throws CacheException {
		MemCache memCache = (MemCache) regionCache.get(regionName);
		memCache.destroy();
		regionCache.remove(regionName);
	}

	/**
	 * @see com.lonever.skklib.cache.CacheProvider#start()
	 */
	@Override
	public void start() throws CacheException {
	}

	/**
	 * @see com.lonever.skklib.cache.CacheProvider#stop()
	 */
	@Override
	public void stop() {
	}

}
