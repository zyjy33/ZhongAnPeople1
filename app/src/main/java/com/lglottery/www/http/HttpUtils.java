package com.lglottery.www.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;

import com.lglottery.www.common.WLog;

import android.content.Context;
import android.net.ConnectivityManager;

public class HttpUtils {
	/**
	 * ��InputStream����ת����String����
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	/**
	 * MD5����
	 * @param param
	 * @return
	 */
	public static String MD5(String param) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = param.getBytes();
			// ���MD5ժҪ�㷨�� MessageDigest ����
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// ʹ��ָ�����ֽڸ���ժҪ
			mdInst.update(btInput);
			// �������
			byte[] md = mdInst.digest();
			// ������ת����ʮ�����Ƶ��ַ�����ʽ
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	final static int BUFFER_SIZE = 4096;
	/**
	 * 2����ת�����ַ�
	 * @param in
	 * @param charse
	 * @return
	 */
	public String convertStreamToString(InputStream in, String charse) {
		InputStreamReader reader = null;
		BufferedReader br = null;
		StringBuilder builder = new StringBuilder();
		;
		try {
			reader = new InputStreamReader(in, charse);
			br = new BufferedReader(reader, 2048);
			// ���������������ݵĶ���

			String line = br.readLine();
			while (line != null) {
				builder.append(line);
				line = br.readLine();
			}
			// �ر���
		} catch (IOException e) {
			WLog.v("�쳣");
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO: handle exception
			WLog.v("��ָ���쳣");
		} finally {
			try {
				in.close();
				reader.close();
				br.close();
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e2) {
				// TODO: handle exception
			}
		}
		// �������ֶ���
		return builder.toString().trim();

	}

	/**
	 * ��������Ƿ���ͨ
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNet(Context context) {
		boolean flag = false;
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager.getActiveNetworkInfo() != null) {
			flag = manager.getActiveNetworkInfo().isAvailable();
		}
		return flag;
	}

	/**
	 * �����ֻ��ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
