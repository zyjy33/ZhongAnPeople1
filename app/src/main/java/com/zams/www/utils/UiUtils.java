package com.zams.www.utils;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.android.hengyu.web.Location;

import java.security.MessageDigest;


/**
 * Created by Administrator on 2018/6/14.
 */

public class UiUtils {
    private static int sScreenWidth = 0;
    private static int sScreenHeight = 0;
    private static String strMd5;

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

    public static String getIMSI(Context context) {
        try {
            if (context == null) {
                return "";
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getSubscriberId();
        } catch (Exception exception1) {
        }
        return "";
    }

    public static String getIMEI(Context context) {
        try {
            if (context == null) {
                return "";
            }
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId();
            if (imei != null && !imei.equals("")) {
                return imei;
            }
        } catch (Exception exception1) {
        }

        return "";
    }

    /**
     * 需要申请权限
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        if (strMd5 != null) {
            return strMd5;
        }
        try {
            String strIMEI = getIMEI(context);
            if (strIMEI == null || strIMEI.equals("")) {
                strIMEI = getIMSI(context);
                if (strIMEI == null || strIMEI.equals("")) {
                    return "";
                }
            }
            String strTemp = strIMEI + strIMEI + strIMEI;
            strMd5 = getMD5(strTemp.getBytes());
            return strMd5;
        } catch (Exception exception1) {
        }
        return "";
    }

    public static String getMD5(byte[] bytes) {
        String strTemp = "";
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(bytes);
            return toHexString(algorithm.digest(), "");
        } catch (Exception e) {
            strTemp = "";
        }

        return strTemp;
    }

    public static String toHexString(byte[] bytes, String separator) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            if (Integer.toHexString(0xFF & b).length() == 1)
                hexString.append("0").append(Integer.toHexString(0xFF & b));
            else
                hexString.append(Integer.toHexString(0xFF & b));
        }
        return hexString.toString();
    }

}
