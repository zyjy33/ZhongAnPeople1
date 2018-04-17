package com.hengyushop.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.hengyu.post.WorkIndexDo;

import java.util.ArrayList;

public class WorkDB {
	private SQLiteDatabase db;
	private Context context;

	public WorkDB(Context context) {
		this.context = context;
		db = context
				.openOrCreateDatabase("work.db", Context.MODE_PRIVATE, null);
	}

	public ArrayList<String> getWorks(String sql) {
		ArrayList<String> items = new ArrayList<String>();
		Cursor cursor = db.rawQuery(sql, null);

		items.add("不限");

		while (cursor.moveToNext()) {
			items.add(cursor.getString(0));

		}
		cursor.close();
		db.close();
		return items;
	}

	public String getId(String sql) {
		Cursor cursor = db.rawQuery(sql, null);
		String id = "";
		while (cursor.moveToNext()) {
			id = cursor.getString(0);

		}
		cursor.close();
		db.close();
		return id;
	}

	/**
	 * 得到所有职位列表
	 *
	 * @return
	 */
	public ArrayList<WorkIndexDo> getWorks() {
		ArrayList<WorkIndexDo> works = new ArrayList<WorkIndexDo>();
		String sql = "select * from company_jobcategory where parentid=0";
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			WorkIndexDo indexDo = new WorkIndexDo();
			indexDo.setId(cursor.getString(0));
			indexDo.setName(cursor.getString(1));
			indexDo.setPid(cursor.getString(2));
			works.add(indexDo);
		}
		cursor.close();
		db.close();
		return works;
	}
}
