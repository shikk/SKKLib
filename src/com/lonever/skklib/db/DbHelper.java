/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */
package com.lonever.skklib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DbHelper
 * 
 * @author Gary Fu
 */
class DbHelper extends SQLiteOpenHelper {
	private DbInfo dbInfo;

	public DbHelper(Context context, DbInfo dbInfo) {
		super(context, dbInfo.getDbName(), dbInfo.getDbFactory(), dbInfo.getDbVersion());
		this.dbInfo = dbInfo;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (String tableName : dbInfo.getTables().keySet()) {
			db.execSQL(dbInfo.getTables().get(tableName));
		}
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop the old table.
		for (String tableName : dbInfo.getTables().keySet()) {
			db.execSQL("DROP TABLE IF EXISTS " + tableName);
		}
		// Create a new one.
		onCreate(db);
	}
}
