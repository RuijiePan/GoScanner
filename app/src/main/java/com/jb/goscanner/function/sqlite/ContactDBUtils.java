package com.jb.goscanner.function.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jb.goscanner.function.bean.ContactInfo;
import com.jb.goscanner.function.bean.DetailItem;
import com.jb.goscanner.util.log.Loger;

import java.util.ArrayList;
import java.util.List;

import static com.jb.goscanner.function.sqlite.ContactDBHelper.TABLE_NAME_CONTACT;

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
			synchronized (ContactDBUtils.class) {
				if (instance == null) {
					instance = new ContactDBUtils(context);
				}
			}
		}
		return instance;
	}

	public void insertContact(ContactInfo info) {
		openDB();
		ContactInfo existContact = queryContactById(info.getId());
		if (existContact == null) {
			ContentValues cv = new ContentValues();
			cv.put(ContactDBHelper.CONTACT_ID, info.getId());
			cv.put(ContactDBHelper.CONTACT_NAME, info.getName());
			cv.put(ContactDBHelper.CONTACT_IMG, info.getImgUrl());
			cv.put(ContactDBHelper.CONTACT_REMARK, info.getRemark());
			db.insert(TABLE_NAME_CONTACT, null, cv);

			Log.d(TAG, "insertContact: insert");
		} else {
			ContentValues cv = new ContentValues();
			cv.put(ContactDBHelper.CONTACT_ID, info.getId());
			cv.put(ContactDBHelper.CONTACT_NAME, info.getName());
			cv.put(ContactDBHelper.CONTACT_IMG, info.getImgUrl());
			cv.put(ContactDBHelper.CONTACT_REMARK, info.getRemark());
			String where = ContactDBHelper.CONTACT_ID + "=" + info.getId();
			db.update(TABLE_NAME_CONTACT, cv, where, null);
			Log.d(TAG, "insertContact: update");
		}
	}

	public void deleteContactById(int id) {
		openDB();
		String sql = "delete from " + TABLE_NAME_CONTACT + " where " + ContactDBHelper.CONTACT_ID + " = " + id;
		db.execSQL(sql);

		sql = "delete from " + ContactDetailDBHelper.TABLE_NAME_DETAIL + " where " + ContactDetailDBHelper.DETAIL_CONTACTID + " = " + id;
		db.execSQL(sql);
	}

	public ContactInfo queryContactById(String id) {
		Cursor cursor = null;
		try {
			openDB();

			String sql = "select * from " + TABLE_NAME_CONTACT +" where " + ContactDBHelper.CONTACT_ID +"='" + id+"'";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				ContactInfo info = new ContactInfo();

				info.setId(id);
				info.setName(cursor.getString(cursor.getColumnIndex(ContactDBHelper.CONTACT_NAME)));
				info.setImgUrl(cursor.getString(cursor.getColumnIndex(ContactDBHelper.CONTACT_IMG)));
				info.setRemark(cursor.getString(cursor.getColumnIndex(ContactDBHelper.CONTACT_REMARK)));

				ContactDetailDBUtils detail = ContactDetailDBUtils.getInstance(mContext);
				info.setEmail(detail.queryDetailByContactId(id, DetailItem.GROUP_EMAIL));
				info.setPhone(detail.queryDetailByContactId(id, DetailItem.GROUP_PHONE));
				info.setWechat(detail.queryDetailByContactId(id, DetailItem.GROUP_WECHAT));
				info.setOther(detail.queryDetailByContactId(id, DetailItem.GROUP_OTHER));
				return info;
			}
		} catch (Exception e) {
			if (cursor != null) {
				cursor.close();
			}
			e.printStackTrace();
		}
		return null;
	}

	public List<ContactInfo> queryExistContact() {
		Cursor cursor = null;
		try {
			openDB();

			String sql = "select * from " + TABLE_NAME_CONTACT;
			cursor = db.rawQuery(sql, null);
			List<ContactInfo> contactInfoList = new ArrayList<>();
			while (cursor.moveToNext()) {
				ContactInfo info = new ContactInfo();
				String id = cursor.getString(cursor.getColumnIndex(ContactDBHelper.CONTACT_ID));
				info.setId(id);
				info.setName(cursor.getString(cursor.getColumnIndex(ContactDBHelper.CONTACT_NAME)));
				info.setImgUrl(cursor.getString(cursor.getColumnIndex(ContactDBHelper.CONTACT_IMG)));
				info.setRemark(cursor.getString(cursor.getColumnIndex(ContactDBHelper.CONTACT_REMARK)));
				ContactDetailDBUtils detail = ContactDetailDBUtils.getInstance(mContext);
				info.setEmail(detail.queryDetailByContactId(id, DetailItem.GROUP_EMAIL));
				info.setPhone(detail.queryDetailByContactId(id, DetailItem.GROUP_PHONE));
				info.setWechat(detail.queryDetailByContactId(id, DetailItem.GROUP_WECHAT));
				info.setOther(detail.queryDetailByContactId(id, DetailItem.GROUP_OTHER));
				contactInfoList.add(info);
			}
			cursor.close();
			return contactInfoList;
		} catch (Exception e) {
			if (cursor != null) {
				cursor.close();
			}
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
