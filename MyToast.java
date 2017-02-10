package com.readboy.magicbook.utils;

import android.content.Context;
import android.widget.Toast;

public class MyToast {

	
	public static boolean isPaused = false;
	private static Toast toast;
	
	public static void showInfo(Context context, String info)
	{
		showInfo(context, info, Toast.LENGTH_LONG);
	}
	
	public static void showInfo(Context context, String info, int time)
	{
		if(toast == null)
		{
			toast = Toast.makeText(context, info, Toast.LENGTH_LONG);
		}
		if(!isPaused)
		{
			toast.setText(info);
			if(time > 1)
			{
				toast.setDuration(time);
			}
			else
			{
				toast.setDuration(Toast.LENGTH_LONG);
			}
			toast.show();
		}
	}
	
	
}
