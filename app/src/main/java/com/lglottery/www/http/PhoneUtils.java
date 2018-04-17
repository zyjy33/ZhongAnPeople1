package com.lglottery.www.http;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;

import android.telephony.TelephonyManager;

public class PhoneUtils {
	/**
	 * ���ݴ����TelephonyManager��ȡ��ϵͳ��ITelephonyʵ��.
	 * 
	 * @param telephony
	 * @return ϵͳ��ITelephonyʵ��
	 * @throws Exception
	 */
	public static ITelephony getITelephony(TelephonyManager telephony)
			throws Exception {
		Method getITelephonyMethod = telephony.getClass().getDeclaredMethod(
				"getITelephony");
		getITelephonyMethod.setAccessible(true);// ˽�л�����Ҳ��ʹ��
		return (ITelephony) getITelephonyMethod.invoke(telephony);
	}
}
