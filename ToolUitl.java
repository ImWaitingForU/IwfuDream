package com.readboy.util;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;

public class ToolUtil {

	
	/**
	 * ʹ������������ȡͼƬ
	 * @param context
	 * @param resId ͼƬ��ID
	 * @return Bitmap����
	 */
	public static Bitmap readBitmap(Context context , int resId)
	{
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		//��ȡ��ԴͼƬ
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
	 * �ж������Ƿ�����
	 * @param context 
	 * @return 0δ���ӣ� 1�ƶ����磬 2wifi
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
