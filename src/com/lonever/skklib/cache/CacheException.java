/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */
package com.lonever.skklib.cache;

/**
 * CacheException
 * 
 * @author Gary Fu
 */
public class CacheException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance of <code>CacheException</code> without detail
	 * message.
	 */
	public CacheException() {
	}

	/**
	 * Constructs an instance of <code>CacheException</code> with the specified
	 * detail message.
	 * 
	 * @param msg
	 *            the detail message.
	 */
	public CacheException(String msg) {
		super(msg);
	}

	/**
	 * Constructs an instance of <code>CacheException</code> with the specified
	 * detail message and Throwable.
	 * 
	 * @param msg
	 *            the detail message.
	 * @param e
	 *            .
	 */
	public CacheException(String msg, Throwable e) {
		super(msg, e);
	}

	/**
	 * Constructs an instance of <code>CacheException</code> with Throwable.
	 * 
	 * @param e
	 */
	public CacheException(Throwable e) {
		super(e);
	}
}
