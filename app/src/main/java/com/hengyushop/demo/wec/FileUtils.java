package com.hengyushop.demo.wec;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import com.lglottery.www.http.HttpUtils;


public class FileUtils {
	public static final String WIDE = "wide.pe";
	public static final String WARE= "ware.pe";
	
	private static final String TEXT_CACHE_PATH = "/data/data/com.zams.www/";
	/**
	 * д�뻺���ļ�
	 */
	public static void saveCache(String text,String name) {
		File file = new File(TEXT_CACHE_PATH);
		if(!file.exists()){
			file.mkdirs();
		}
		File textFile = new File(TEXT_CACHE_PATH+name);
		if(!textFile.exists()){
			try {
				textFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(textFile);
			fileWriter.write(text);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��ȡ�ļ�����
	 * @param name
	 * @return
	 */
	public static String readCache(String name) {
		File ip = new File(TEXT_CACHE_PATH+name);
		HttpUtils utils = new HttpUtils();
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return utils.convertStreamToString(fs, "utf-8");
	}
	/**
	 * ����ļ��ĳ���
	 * @param name
	 * @return
	 */
	public static long readFileSize(String name){
		File ip = new File(TEXT_CACHE_PATH+name);
		if(ip.exists()){
			return ip.length();
		}
		return 0;
	}
}
