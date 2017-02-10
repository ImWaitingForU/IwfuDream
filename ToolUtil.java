package com.readboy.magicbook.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;

import java.io.InputStream;

public class ToolUtil {

	
	/**
	 * 使用输入流来读取图片
	 * @param context
	 * @param resId 图片的ID
	 * @return Bitmap对象
	 */
	public static Bitmap readBitmap(Context context , int resId)
	{
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		//获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}
	
	
	public static boolean isNetworkAvailable(Context mContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		
		return (activeNetInfo != null && activeNetInfo.isAvailable() && 
				activeNetInfo.isConnected());	
	}
	
	
	/**
	 * 判断网络是否连接
	 * @param context 
	 * @return 0未连接， 1移动网络， 2wifi
	 */
	public static int getNetworkConnectionType(Context context){
		final ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        State gprs = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        State wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if(wifi == State.CONNECTED || wifi == State.CONNECTING){
//        	Toast.makeText(this, "network is open! wifi", Toast.LENGTH_SHORT).show();
        	return 2;
        } 
        if(gprs == State.CONNECTED || gprs == State.CONNECTING){
//            Toast.makeText(this, "network is open! gprs", Toast.LENGTH_SHORT).show();
            return 1;
        }  
        return 0;
		
	}
	
	private static int count = 0;
	private static long lastUsedMemory;
	public static void printMemory(int step) {
		Log.e("mhc--memory---data", "mhc--------step =" + step + "----add = "+(Runtime.getRuntime().totalMemory() - lastUsedMemory));
		Log.d("mhc--memory---data", "mhc------------printMemory  used="
				+ Runtime.getRuntime().totalMemory()/1024/1024 + " M");
		Log.d("mhc--memory---data", "mhc------------printMemory  max="
				+ Runtime.getRuntime().maxMemory()/1024/1024 + " M");
		lastUsedMemory = Runtime.getRuntime().totalMemory();
		count++;
	}
	
	public static void printMemory(String method) {
		Log.e("mhc--memory---data", "mhc--------method =" + method + "----add = "+(Runtime.getRuntime().totalMemory() - lastUsedMemory));
		Log.d("mhc--memory---data", "mhc------------printMemory  used="
				+ Runtime.getRuntime().totalMemory()/1024/1024 + " M");
		Log.d("mhc--memory---data", "mhc------------printMemory  max="
				+ Runtime.getRuntime().maxMemory()/1024/1024 + " M");
		lastUsedMemory = Runtime.getRuntime().totalMemory();
		count++;
	}
	
	public static String getUsedMemory()
	{
		return Runtime.getRuntime().totalMemory()/1024/1024 + " M";
	}
}
