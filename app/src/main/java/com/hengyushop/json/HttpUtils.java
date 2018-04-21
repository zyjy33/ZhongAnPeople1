package com.hengyushop.json;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpUtils {
	/**
	 * 将InputStream对象转换成String对象
	 *
	 * @param in
	 * @return
	 * @throws IOException
	 */
	final static int BUFFER_SIZE = 4096;

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
			// WLog.v("异常");
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO: handle exception
			// WLog.v("空指针异常");
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

	public static String getSimpleTime(String fromTime, String targetTime)
			throws ParseException {
		SimpleDateFormat dateformat1 = new SimpleDateFormat(
				"yyyy/MM/dd hh:mm:ss");
		Date d = dateformat1.parse(fromTime);
		SimpleDateFormat dateformat2 = new SimpleDateFormat(targetTime);
		return dateformat2.format(d);
	}

	public static String getTrainSimpleTime(String fromTime, String targetTime)
			throws ParseException {
		SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyyMMddhh:mm");
		Date d = dateformat1.parse(fromTime);
		SimpleDateFormat dateformat2 = new SimpleDateFormat(targetTime);
		return dateformat2.format(d);
	}
}
