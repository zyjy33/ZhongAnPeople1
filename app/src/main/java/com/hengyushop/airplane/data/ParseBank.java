package com.hengyushop.airplane.data;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hengyushop.json.HttpUtils;
import com.zams.www.R;

import android.content.Context;

public class ParseBank {
	public static String parseBank(String code, Context context) {
		InputStream in = context.getResources().openRawResource(R.raw.banks);
		HttpUtils utils = new HttpUtils();
		String result = utils.convertStreamToString(in, "UTF-8");
		String bankName = "";
		try {
			JSONArray array = new JSONArray(result);
			int len = array.length();
			for (int i = 0; i < len; i++) {
				JSONObject object = array.getJSONObject(i);
				if (object.getString("tag").equals(code)) {
					bankName = object.getString("name");
				}
				;
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return bankName;

	}

	public static String paseName(String name) {
		if (name.equals("CREDITCARD")) {
			return "信用卡";
		} else if (name.equals("DEBITCARD")) {
			return "借记卡";
		}
		return null;
	}

}
