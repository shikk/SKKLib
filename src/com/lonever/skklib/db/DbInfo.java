/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */
package com.lonever.skklib.db;

import java.util.HashMap;

import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.lonever.skklib.util.StringUtils;

/**
 * 数据库信息类
 * 
 * @author Gary Fu
 */
public class DbInfo {
	private String                  dbName    = "";
	private int                     dbVersion = 1;
	private CursorFactory           dbFactory ;
	private HashMap<String, String> tableMap  = new HashMap<String, String>();

	
	public DbInfo(String dbName){
		this(dbName, 1);
	}
	
	public DbInfo(String dbName,int dbVersion){
		this(dbName, null, dbVersion);
	}
	
	
	/**
	 * 用数据库名、版本构造一个信息类
	 * 
	 * @param dbName
	 *            数据库名
	 * @param dbVersion
	 *            数据库版本
	 */
	public DbInfo(String dbName,CursorFactory dbFactory, int dbVersion) {
		if (StringUtils.isBlank(dbName) || dbVersion < 1) {
			throw new IllegalArgumentException();
		}
		this.dbName = dbName;
		this.dbFactory = dbFactory;
		this.dbVersion = dbVersion;
	}

	/**
	 * 添加表
	 * 
	 * @param tableName
	 *            表名
	 * @param createSql
	 *            表对应的建表sql
	 */
	public void addTable(String tableName, String createSql) {
		if (StringUtils.isBlank(tableName) || StringUtils.isBlank(createSql)) {
			throw new IllegalArgumentException();
		}

		tableMap.put(tableName, createSql);
	}

	/**
	 * dbName
	 * 
	 * @return dbName
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * dbVersion
	 * 
	 * @return dbVersion
	 */
	public int getDbVersion() {
		return dbVersion;
	}

	/**
	 * 获得所有表的HashMap
	 * 
	 * @return 包含所有表的HashMap，不会返回<tt>null</tt>
	 */
	public HashMap<String, String> getTables() {
		return tableMap;
	}

	public CursorFactory getDbFactory() {
		return dbFactory;
	}

}
