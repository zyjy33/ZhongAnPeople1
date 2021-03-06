package com.hengyushop.demo.train;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class HczDB {
	private Context context;
	private SQLiteDatabase db;

	public HczDB(Context context) {

		this.context = context;
		db = context.openOrCreateDatabase("hcz.db", Context.MODE_PRIVATE, null);
	}

	public String getCode(String name) {
		String sql = "select code from hcz where name='" + name + "'";
		Cursor cursor = db.rawQuery(sql, null);
		String result = "";
		while (cursor.moveToNext()) {
			result = cursor.getString(0);

		}
		cursor.close();
		db.close();
		return result;
	}

	/**
	 * 解析数据
	 *
	 * @param like
	 * @return
	 */
	public ArrayList<String> getZ(String like) {
		String sql = "select name from hcz where name like '" + like
				+ "%' or py like '" + like + "%'";
		Cursor cursor = db.rawQuery(sql, null);
		ArrayList<String> trains = new ArrayList<String>();
		while (cursor.moveToNext()) {
			// TZrainDao dao = new TZrainDao();
			/*
			 * dao.setName(cursor.getString(0));
			 * dao.setCode(cursor.getString(1));
			 */
			trains.add(cursor.getString(0));
		}
		cursor.close();
		db.close();
		return trains;
	}
}
