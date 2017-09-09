package com.jb.goscanner.function.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by liuyue on 2017/9/3.
 */
public class ContactDBHelper extends SQLiteOpenHelper {
	private static final String TAG = "ContactDBHelper";
	// 数据库版本
	private static final int DB_VERSION = 1;

	static final String TABLE_NAME_CONTACT = "table_contact";
	static final String CONTACT_ID = "id";
	static final String CONTACT_NAME = "name";
	static final String CONTACT_IMG = "img";
	static final String CONTACT_REMARK = "remark";
	private static final String SQL_PRAISE = "create table if not exists " + TABLE_NAME_CONTACT + " ("
			+ CONTACT_ID + " TEXT primary key, "
		+ CONTACT_NAME + " TEXT, "
			+ CONTACT_IMG + " TEXT, "
			+ CONTACT_REMARK + " TEXT " + ")";

	public ContactDBHelper(Context context) {
		super(context, DBConstant.DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_PRAISE);
		Log.d(TAG, "onCreate: cantact db");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + TABLE_NAME_CONTACT);

		onCreate(db);
	}
}
