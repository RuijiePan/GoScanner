package com.jb.goscanner.function.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by liuyue on 2017/9/3.
 */
public class ContactDetailDBHelper extends SQLiteOpenHelper {
	// 数据库版本
	private static final int DB_VERSION = 1;
	private static final String TAG = "ContactDetailDBHelper";

	static final String TABLE_NAME_DETAIL = "table_contact_detail";
	static final String DETAIL_ID = "detail_id";
	static final String DETAIL_CONTACTID = "detail_contactid";
	static final String DETAIL_TAG = "detail_tag";
	static final String DETAIL_VALUE = "detail_value";
	static final String DETAIL_GROUP = "detail_group";
	public static final String SQL_PRAISE = "create table if not exists " + TABLE_NAME_DETAIL + " ("
			+ DETAIL_ID + " TEXT primary key, "
			+ DETAIL_CONTACTID + " TEXT, "
			+ DETAIL_TAG + " TEXT, "
			+ DETAIL_VALUE + " TEXT, "
			+ DETAIL_GROUP + " TEXT " + ")";

	public ContactDetailDBHelper(Context context) {
		super(context, DBConstant.DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(this.SQL_PRAISE);
		Log.d(TAG, "onCreate: create detail db");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + TABLE_NAME_DETAIL);

		onCreate(db);
	}
}
