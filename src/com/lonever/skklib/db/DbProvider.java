/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */
package com.lonever.skklib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * DbProvider，请继承本类以扩展更多数据库操作方法
 * 
 * @author Gary Fu
 */
public class DbProvider {
	protected SQLiteDatabase db;

	private DbHelper         dbHelper;

	public DbProvider(Context context, DbInfo dbInfo) {
		if (context == null || dbInfo == null || dbInfo.getTables().size() == 0) {
			throw new IllegalArgumentException("context : "+context +"  dbinfo"+ dbInfo + "  tablesize: "+ dbInfo.getTables().size());
		}
		dbHelper = new DbHelper(context, dbInfo);
	}

	public void close() {
		if (db!=null) {
			db.close();
		}
	}

	public SQLiteDatabase open() throws SQLiteException {
		db = dbHelper.getWritableDatabase();
		return db;
	}

	public SQLiteDatabase getDb(){
		return this.db;
	}
}
