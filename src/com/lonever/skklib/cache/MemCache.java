/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */
package com.lonever.skklib.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lonever.skklib.util.TimeUtils;


/**
 * 内存Cache
 * 
 * @author Gary Fu
 */
public class MemCache implements Cache {
	protected Map<Object, CacheObjectWrapper> cache;

	/**
	 * Creates a new HashMap cache.
	 */
	public MemCache() {
		this.cache = new HashMap<Object, CacheObjectWrapper>();
	}

	/**
	 * @see com.lonever.skklib.cache.Cache#clear()
	 */
	@Override
	public void clear() throws CacheException {
		cache.clear();
	}

	/**
	 * @see com.lonever.skklib.cache.Cache#destroy()
	 */
	@Override
	public void destroy() throws CacheException {
		cache = null;
	}

	/**
	 * @see com.lonever.skklib.cache.Cache#get(java.lang.Object)
	 */
	@Override
	public Object get(Object key) throws CacheException {
		if (key == null) {
			return null;
		}
		CacheObjectWrapper cacheObjectWrapper = getCacheObjectWrapper(key);
		if (cacheObjectWrapper == null) {
			return null;
		} else {
			return cacheObjectWrapper.getObject();
		}
	}

	private CacheObjectWrapper getCacheObjectWrapper(Object key) {
		CacheObjectWrapper cacheObjectWrapper = cache.get(key);
		if (cacheObjectWrapper == null) {
			return null;
		} else {
			if (cacheObjectWrapper.getExpirTime() > 0) {
				long intervalSecond = TimeUtils.getIntervalInSecond(cacheObjectWrapper.getRefreshTime(), new Date());
				if (intervalSecond > cacheObjectWrapper.getExpirTime()) {
					remove(key);
					return null;
				}
			}
		}
		return cacheObjectWrapper;
	}

	/**
	 * @see com.lonever.skklib.cache.Cache#has(java.lang.Object)
	 */
	@Override
	public boolean has(Object key) throws CacheException {
		if (key == null) {
			return false;
		}
		if (!cache.containsKey(key)) {
			return false;
		}
		return getCacheObjectWrapper(key) == null ? false : true;
	}

	/**
	 * @see com.lonever.skklib.cache.Cache#keys()
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List keys() throws CacheException {
		List<Object> keyList = new ArrayList<Object>();
		for (Object object : cache.keySet()) {
			keyList.add(object);
		}
		return keyList;
	}

	/**
	 * @see com.lonever.skklib.cache.Cache#memSize()
	 */
	@Override
	public long memSize() throws CacheException {
		return 0;
	}

	/**
	 * @see com.lonever.skklib.cache.Cache#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void put(Object key, Object value) throws CacheException {
		put(key, value, null);
	}

	/**
	 * @see com.lonever.skklib.cache.Cache#put(java.lang.Object, java.lang.Object,
	 *      java.lang.String)
	 */
	@Override
	public void put(Object key, Object value, String expirTime) throws CacheException {
		if (key != null && value != null) {
			long expirTimeL = 0;
			try {
				expirTimeL = TimeUtils.parseToSecond(expirTime);
			} catch (IllegalArgumentException iae) {
			}
			CacheObjectWrapper cacheObjectWrapper = getCacheObjectWrapper(key);
			if (cacheObjectWrapper == null) {
				cache.put(key, new CacheObjectWrapper(value, new Date(), expirTimeL));
			} else {
				cacheObjectWrapper.setObject(value);
				cacheObjectWrapper.setRefreshTime(new Date());
				cacheObjectWrapper.setExpirTime(expirTimeL);
				cache.put(key, cacheObjectWrapper);
			}
		}
	}

	/**
	 * @see com.lonever.skklib.cache.Cache#remove(java.lang.Object)
	 */
	@Override
	public void remove(Object key) throws CacheException {
		cache.remove(key);
	}

	/**
	 * @see com.lonever.skklib.cache.Cache#size()
	 */
	@Override
	public int size() throws CacheException {
		return cache.size();
	}
}
