package com.readboy.magicbook.utils;

import android.util.Log;

/**
 * 用于控制是否显示打印消息的类
 * @author mhc
 *
 */
public class LogUtil
{
	/**
	 * 是否显示debug打印消息
	 */
	static boolean debugFlag = true;
	
	
	/**
     * Send an {@link #INFO} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void i(String TAG, String msg) {
        Log.i(TAG, msg);
    }
	
	/**
     * Send a {@link #DEBUG} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
	public static void d(String TAG, String msg)
	{
		if(debugFlag)
		{
			Log.d(TAG, msg);
		}
	}
	
	public static void w(String TAG, String msg)
	{
		if(debugFlag)
		{
			Log.w(TAG, msg);
		}
	}
	/**
     * Send an {@link #ERROR} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
	public static void e(String TAG, String msg)
	{
		if(debugFlag)
		{
			Log.e(TAG, msg);
		}
	}
}
