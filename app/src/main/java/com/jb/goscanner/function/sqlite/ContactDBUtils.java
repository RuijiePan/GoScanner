package com.jb.goscanner.function.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jb.goscanner.function.bean.ContactInfo;
import com.jb.goscanner.util.log.Loger;

/**
 * Created by liuyue on 2017/9/3.
 */
public class ContactDBUtils {

	private static final String TAG = "DBUtils";

	SQLiteDatabase db;

	Context mContext;

	private static ContactDBUtils instance;

	public ContactDBUtils(Context context) {
		mContext = context.getApplicationContext();
		openDB();
	}

	public static ContactDBUtils getInstance(Context context) {
		if (instance == null) {
			instance = new ContactDBUtils(context);
		}
		return instance;
	}

	public void insertContact(ContactInfo info) {
		openDB();
		ContentValues cv = new ContentValues();
		cv.put(ContactDBHelper.CONTACT_NAME, info.getName());
		cv.put(ContactDBHelper.CONTACT_PHONE, info.getPhoneNum());
		cv.put(ContactDBHelper.CONTACT_SKYPE, info.getSkype());
		cv.put(ContactDBHelper.CONTACT_FACEBOOK, info.getFacebook());
		db.insert(ContactDBHelper.TABLE_NAME_CONTACT, null, cv);
	}

	public void deleteContactById(int id) {
		openDB();
		String sql = "delete from " + ContactDBHelper.TABLE_NAME_CONTACT + " where " + ContactDBHelper.CONTACT_ID + " = " + id;
		db.execSQL(sql);
	}
	private void openDB() {
		if (db == null) {
			try {
				ContactDBHelper dbHelper = new ContactDBHelper(mContext);
				db = dbHelper.getReadableDatabase();
			} catch (Throwable e) {
				e.printStackTrace();
				Loger.e(TAG, "openDB fail : ", e);
			}
		}
	}


}
