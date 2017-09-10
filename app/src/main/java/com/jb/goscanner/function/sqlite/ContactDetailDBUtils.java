package com.jb.goscanner.function.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jb.goscanner.function.bean.DetailItem;
import com.jb.goscanner.util.log.Loger;

import java.util.ArrayList;

/**
 * Created by liuyue on 2017/9/3.
 */
public class ContactDetailDBUtils {

	private static final String TAG = "ContactDetailDBUtils";

	SQLiteDatabase db;

	Context mContext;

	private static ContactDetailDBUtils instance;

	public ContactDetailDBUtils(Context context) {
		mContext = context.getApplicationContext();
		openDB();
	}

	public static ContactDetailDBUtils getInstance(Context context) {
		if (instance == null) {
			instance = new ContactDetailDBUtils(context);
		}
		return instance;
	}

	public void insertDetail(DetailItem item) {
		openDB();
		ContentValues cv = new ContentValues();
		cv.put(ContactDetailDBHelper.DETAIL_ID, item.getId());
		cv.put(ContactDetailDBHelper.DETAIL_CONTACTID, item.getContactId());
		cv.put(ContactDetailDBHelper.DETAIL_TAG, item.getTag());
		cv.put(ContactDetailDBHelper.DETAIL_VALUE, item.getValue());
		cv.put(ContactDetailDBHelper.DETAIL_GROUP, item.getGroup());
		db.insert(ContactDetailDBHelper.TABLE_NAME_DETAIL, null, cv);
	}

	public void deleteDetailByContactId(String id) {
		openDB();
		String sql = "delete from " + ContactDetailDBHelper.TABLE_NAME_DETAIL + " where " + ContactDetailDBHelper.DETAIL_CONTACTID + " = '" + id + "'";
		db.execSQL(sql);
	}

	public ArrayList<DetailItem> queryDetailByContactId(String id, String group) {
		try {
			openDB();

			String sql = "select * from " + ContactDetailDBHelper.TABLE_NAME_DETAIL +" where " + ContactDetailDBHelper.DETAIL_CONTACTID +"='" + id +"' and " + ContactDetailDBHelper.DETAIL_GROUP +"='" + group + "'";
			Cursor cursor = db.rawQuery(sql, null);
			ArrayList list = new ArrayList<DetailItem>();
			while (cursor.moveToNext()) {
				DetailItem item = new DetailItem();
				item.setId(cursor.getString(cursor.getColumnIndex(ContactDetailDBHelper.DETAIL_ID)));
				item.setValue(cursor.getString(cursor.getColumnIndex(ContactDetailDBHelper.DETAIL_VALUE)));
				item.setContactId(cursor.getString(cursor.getColumnIndex(ContactDetailDBHelper.DETAIL_CONTACTID)));
				item.setGroup(cursor.getString(cursor.getColumnIndex(ContactDetailDBHelper.DETAIL_GROUP)));
				item.setTag(cursor.getString(cursor.getColumnIndex(ContactDetailDBHelper.DETAIL_TAG)));
				list.add(item);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private void openDB() {
		if (db == null) {
			try {
				ContactDetailDBHelper dbHelper = new ContactDetailDBHelper(mContext);
				db = dbHelper.getReadableDatabase();
			} catch (Throwable e) {
				e.printStackTrace();
				Loger.e(TAG, "openDB fail : ", e);
			}
		}
	}


}
