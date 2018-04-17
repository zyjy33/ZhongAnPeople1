package com.lglottery.www.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
public class SharedUtils {
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private static final String NAME = "name", NUMBER = "number",
			SORT_KEY = "sort_key",CON_TYPE = "con_type",CLIENT = "client";
	public SharedUtils(Context context, String NAME) {
		// TODO Auto-generated constructor stub
		preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		editor = preferences.edit();
	}
	/**list通訊錄
	 * 根据某种值查找
	 * @param key
	 * @param compare
	 * @return
	 */
	private String findBy(List<ContentValues> list, String compare,String value){
		for(int i=0;i<list.size();i++){
			if(list.get(i).getAsString(NUMBER).equals(compare)){
				return list.get(i).getAsString(value);
			}
		}
		return "";
	}
	public ArrayList<ContentValues> getStringParams(List<ContentValues> list ){
		Map<String, String> map = (Map<String, String>) preferences.getAll();
		Set<String> set = map.keySet();
		ArrayList<ContentValues> values = new ArrayList<ContentValues>();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			ContentValues v = new ContentValues();
			String number = it.next();
			String[] temp = getStringValue(number).split("_");
			v.put(NAME, temp[0]);
			v.put(NUMBER, number);
			v.put(SORT_KEY, temp[1]);
			values.add(v);
		}
		return values;
	}
	/**
	 * 清空
	 */
	public void clear() {
		editor.clear();
		editor.commit();
	}

	/**
	 * 判断是否存在
	 * 
	 * @param tag
	 * @return
	 */
	public boolean isHere(String tag) {
		return preferences.contains(tag);
	}

	/**
	 * 设置String类型的sharedpreferences
	 * 
	 * @param key
	 * @return
	 */
	public void setStringValue(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}
	public void setIntValue(String key,int value){
		editor.putInt(key, value);
		editor.commit();
	}
	public int getIntValue(String key){
		return preferences.getInt(key, 0);
	}
	/**
	 * 获得String类型的sharedpreferences
	 * 
	 * @param key
	 * @return
	 */
	public String getStringValue(String key) {
		return preferences.getString(key, "");
	}
	/**
	 * 获得金币
	 */
	public String getValue(String key) {
		return preferences.getString(key, "0");
	}
	
	/**
	 * 设置boolean类型的sharedpreferences
	 * 
	 * @param key
	 * @return
	 */
	public void setBooleanValue(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 获得boolean类型的sharedpreferences
	 * 
	 * @param key
	 * @return
	 */
	public Boolean getBooleanValue(String key) {
		// 默认为false
		return preferences.getBoolean(key, false);
	}
}
