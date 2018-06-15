package com.zams.www.utils;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import com.android.hengyu.web.Location;


/**
 * Created by Administrator on 2018/6/14.
 */

public class UiUtils {
    private static int sScreenWidth = 0;
    private static int sScreenHeight = 0;

    public static int getScreenWidth() {
        if (sScreenWidth != 0) {
            return sScreenWidth;
        }
        Resources resources = Location.getInstance().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        sScreenWidth = dm.widthPixels;
        sScreenHeight = dm.heightPixels;
        return sScreenWidth;
    }

    public static int getScreenHeight() {
        if (sScreenHeight != 0) {
            return sScreenHeight;
        }
        Resources resources = Location.getInstance().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        sScreenWidth = dm.widthPixels;
        sScreenHeight = dm.heightPixels;
        return sScreenHeight;
    }
    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }
}
