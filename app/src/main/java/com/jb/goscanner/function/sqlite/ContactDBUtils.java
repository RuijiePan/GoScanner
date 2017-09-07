package com.jb.goscanner.function.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jb.goscanner.function.bean.ContactInfo;
import com.jb.goscanner.function.bean.DetailItem;
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
		cv.put(ContactDBHelper.CONTACT_ID, info.getId());
		cv.put(ContactDBHelper.CONTACT_NAME, info.getName());
		cv.put(ContactDBHelper.CONTACT_IMG, info.getImgUrl());
		cv.put(ContactDBHelper.CONTACT_REMARK, info.getRemark());
		db.insert(ContactDBHelper.TABLE_NAME_CONTACT, null, cv);
	}

	public void deleteContactById(int id) {
		openDB();
		String sql = "delete from " + ContactDBHelper.TABLE_NAME_CONTACT + " where " + ContactDBHelper.CONTACT_ID + " = " + id;
		db.execSQL(sql);
	}

	public ContactInfo queryContactById(String id) {
		try {
			openDB();

			String sql = "select * from " + ContactDBHelper.TABLE_NAME_CONTACT +" where " + ContactDBHelper.CONTACT_ID +"='" + id+"'";
			Cursor cursor = db.rawQuery(sql, null);
			ContactInfo info = new ContactInfo();
			while (cursor.moveToNext()) {
				info.setId(id);
				info.setName(cursor.getString(cursor.getColumnIndex(ContactDBHelper.CONTACT_NAME)));
				info.setImgUrl(cursor.getString(cursor.getColumnIndex(ContactDBHelper.CONTACT_IMG)));
				info.setRemark(cursor.getString(cursor.getColumnIndex(ContactDBHelper.CONTACT_REMARK)));
			}
			ContactDetailDBUtils detail = ContactDetailDBUtils.getInstance(mContext);
			info.setEmail(detail.queryContactByContactId(id, DetailItem.GROUP_EMAIL));
			info.setPhone(detail.queryContactByContactId(id, DetailItem.GROUP_PHONE));
			info.setWechat(detail.queryContactByContactId(id, DetailItem.GROUP_WECHAT));
			info.setOther(detail.queryContactByContactId(id, DetailItem.GROUP_OTHER));
			return info;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
