package com.zams.www.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.hengyu.web.Constant;
import com.android.hengyu.web.Location;

/**
 * Created by Administrator on 2018/6/14.
 */

public class AccountUtils {
    private static boolean mHasBound;

    /**
     * 是否绑定手机
     *
     * @return
     */
    public static boolean hasBoundPhone() {
        if (mHasBound) {
            return true;
        }
        SharedPreferences sp = Location.getInstance().getSharedPreferences(Constant.LONGUSERSET, Context.MODE_PRIVATE);
        String userId = sp.getString(Constant.USER_ID, "");
        if (TextUtils.isEmpty(userId) || "0".equals(userId)) {
            mHasBound = false;
            return false;
        } else {
            mHasBound = true;
            return true;
        }
    }
}
