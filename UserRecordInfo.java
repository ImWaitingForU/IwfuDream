package com.readboy.magicbook.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 记录用户使用信息
 * @author mhc
 *
 */
public class UserRecordInfo {

	/** 记录用户的使用信息的文件"user_record" */
	private static final String user_record = "user_record";
	
	public static final String UserFirstOpen = "UserFirstOpen";
	/**
	 * 获取上一次用户使用痕迹
	 * @param context
	 * @param key 键名称
	 * @param strKeyValue 键值
	 * @return
	 */
	public static String getLastUserRecordInfo(Context context, String key, String defaultValue) {
		String strLastBook = "";
		SharedPreferences spfFtmTutor = null;
		try {
			// 获取SharedPreferences对象
			if (spfFtmTutor == null) {
				spfFtmTutor = context.getSharedPreferences(user_record,
				                                           Context.MODE_PRIVATE);
			}
			if (spfFtmTutor != null) {
				// 读取数据
				strLastBook = spfFtmTutor.getString(key, defaultValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strLastBook;
	}

	
	/**
	 * @aim 保存上一次用户使用痕迹。
	 * @param context
	 * @param key 键名称
	 * @param strKeyValue 键值
	 * @return
	 */
	public static boolean setLastUserRecordInfo(Context context, String key, String strKeyValue) {
		SharedPreferences spfFtmTutor = null;
		try {
			// 获取SharedPreferences对象
			if (spfFtmTutor == null) {
				spfFtmTutor = context.getSharedPreferences(user_record,
				                                           Context.MODE_PRIVATE);
			}
			if (spfFtmTutor != null) {
				// 存入数据
				Editor editor = spfFtmTutor.edit();
				// editor.clear();
				// editor.commit();
				editor.putString(key, strKeyValue);
				editor.commit();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}	
}
