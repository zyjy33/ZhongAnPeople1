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
	 * 将InputStream对象转换成String对象
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	/**
	 * MD5加密
	 * @param param
	 * @return
	 */
	public static String MD5(String param) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = param.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
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
	 * 2进制转换成字符
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
			// 创建保存文字数据的对象

			String line = br.readLine();
			while (line != null) {
				builder.append(line);
				line = br.readLine();
			}
			// 关闭流
		} catch (IOException e) {
			WLog.v("异常");
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO: handle exception
			WLog.v("空指针异常");
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
		// 返回文字对象
		return builder.toString().trim();

	}

	/**
	 * 检查网络是否连通
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
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
