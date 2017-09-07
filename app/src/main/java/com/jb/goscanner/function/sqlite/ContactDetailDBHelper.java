package com.jb.goscanner.function.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liuyue on 2017/9/3.
 */
public class ContactDetailDBHelper extends SQLiteOpenHelper {
	// 数据库名称
	private static final String DB_NAME = "contact_detail_db";
	// 数据库版本
	private static final int DB_VERSION = 1;

	static final String TABLE_NAME_DETAIL = "table_contact_detail";
	static final String DETAIL_ID = "detail_id";
	static final String DETAIL_CONTACTID = "detail_contactid";
	static final String DETAIL_TAG = "detail_tag";
	static final String DETAIL_VALUE = "detail_value";
	static final String DETAIL_GROUP = "detail_group";
	private static final String SQL_PRAISE = "create table if not exists " + TABLE_NAME_DETAIL + " ("
			+ DETAIL_ID + "TEXT"
			+ DETAIL_CONTACTID + " TEXT, "
			+ DETAIL_TAG + " TEXT, "
			+ DETAIL_VALUE + " TEXT, "
			+ DETAIL_GROUP + " TEXT " + ")";

	public ContactDetailDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_PRAISE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
