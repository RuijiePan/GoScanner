package com.jb.goscanner.function.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liuyue on 2017/9/3.
 */
public class ContactDBHelper extends SQLiteOpenHelper {
	// 数据库名称
	private static final String DB_NAME = "contact_db";
	// 数据库版本
	private static final int DB_VERSION = 1;

	static final String TABLE_NAME_CONTACT = "table_contact";
	static final String CONTACT_ID = "id";
	static final String CONTACT_NAME = "name";
	static final String CONTACT_PHONE = "phone";
	static final String CONTACT_SKYPE = "skype";
	static final String CONTACT_FACEBOOK = "facebook";
	private static final String SQL_PRAISE = "create table if not exists " + TABLE_NAME_CONTACT + " ("
			+ CONTACT_ID + " INTEGER primary key autoincrement,"
			+ CONTACT_NAME + " TEXT, "
			+ CONTACT_PHONE + " TEXT, "
			+ CONTACT_SKYPE + " TEXT, "
			+ CONTACT_FACEBOOK + " TEXT " + ")";

	public ContactDBHelper(Context context) {
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
