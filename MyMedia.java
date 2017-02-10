package com.readboy.magicbook.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * 播放声音的类，包含一个MediaPlayer对象，用于播放各种音频文件（包括背景音乐，旁白，音效等）
 * @author mhc
 *
 */
public class MyMedia {

	/**
	 *  是否暂停播放音乐
	 */
	private boolean isPaused = false;

	private MediaPlayer mp;
	private AssetManager assetManager;
	private AssetFileDescriptor descriptor;
	
	final String TAG = "MyMedia";
	public MyMedia(Context context)
	{
		assetManager = context.getAssets(); 
	}
	
	/**
	 * 播放背景音乐
	 * @param path 背景音乐存储路径
	 * @param looping 是否循环播放
	 *//*
	public void playBGM(String path, boolean looping) {
		if(mp == null) {
			mp = new MediaPlayer();
			mp.setOnCompletionListener(new OnCompletionListener() 
			{
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					stopBGM();
				}
			});
		}
		
		try {
			mp.reset(); 
            descriptor = assetManager.openFd(path);  
            mp.setDataSource(descriptor.getFileDescriptor(),  
                    descriptor.getStartOffset(), descriptor.getLength()); 
            descriptor.close();
//			mp.setDataSource(path);
			mp.prepare();
			mp.setLooping(looping);
			mp.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	*//**
	 * 继续播放背景音乐
	 *//*
	public void continueBGM()
	{
		if(mp != null && this.isPaused)
		{
			mp.start();
			this.isPaused = false;
		}
	}
	
	*//**
	 * 暂停背景音乐
	 *//*
	public void pauseBGM()
	{
		if(mp != null && mp.isPlaying())
		{
			mp.pause();
			this.isPaused = true;
		}
	}
	
	*//**
	 * 停止背景音乐并释放播放器资源
	 *//*
	public void stopBGM() {
		if(mp != null) {
			mp.reset();
			mp.release();
			mp = null;
		}
	}
	*//**
	 * 播放器是否停止
	 * @return 停止返回true，否则返回false
	 *//*
	public boolean isStoped()
	{
		if(mp == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}*/
	
	
	
	
	/**
	 *	保存当前音频文件播放的位置
	 */
	private int position;
	/**
	 * 保存当前播放的音频文件的文件名
	 */
	private String path = null;
	/**
	 * 是否循环播放
	 */
	private boolean looping;
	/**
	 * 获取音频文件长度
	 */
	private int duration;
	

	/**
	 * 播放背景音乐
	 * @param path 背景音乐存储路径
	 * @param looping 是否循环播放
	 */
	public void playBGM(String path, boolean looping) {
		position = 0;
		this.path = path;
		this.looping = looping;
		play();
	}
	
	/**
	 * 继续播放背景音乐
	 */
	public void continueBGM()
	{
		if(this.isPaused)
		{
			LogUtil.i(TAG,"continueBGM()---seekTo------1--position = "+position+"  duration = "+duration+"------");
			if(path != null && position < duration)
			{
				play();
			}
			this.isPaused = false;
		}
		LogUtil.i(TAG,"continueBGM()-----------isPaused = "+isPaused);
	}
	
	/**
	 * 暂停背景音乐
	 */
	public void pauseBGM()
	{
		if(mp != null && mp.isPlaying())
		{
			mp.pause();
			position = mp.getCurrentPosition();
			this.isPaused = true;
			LogUtil.i(TAG, "pauseBGM()---position = "+position);
		}
		else
		{
			//this.isPaused = false;
			//path = null;
		}
		LogUtil.i(TAG, "pauseBGM()----isPaused = "+isPaused);
	}
	
	
	public boolean isPaused()
	{
		return isPaused;
	}
	
	public void stopBGM()
	{
		if(mp != null)
		{
			mp.stop();
			path = null;
		}
	}
	/**
	 * 停止背景音乐并释放播放器资源
	 */
	public void release() {
		if(mp != null) {
			mp.reset();
			mp.release();
			mp = null;
		}
	}
	/**
	 * 播放器是否停止
	 * @return 停止返回true，否则返回false
	 */
	public boolean isStoped()
	{
		return path == null;
	}
	/**
	 * meida实例是否为null
	 * @return media为null返回true，否则返回false
	 */
	public boolean mediaIsNull()
	{
		return mp == null;
	}
	
	public MediaPlayer getMediaPlayer()
	{
		return mp;
	}
	
	
	/**
	 * 播放声音
	 */
	private void play()
	{
		if(mp == null) {
			mp = new MediaPlayer ();
			mp.setLooping(looping);
//			mp.setOnCompletionListener(new OnCompletionListener() 
//			{
//				public void onCompletion(MediaPlayer mp) {
//					// TODO Auto-generated method stub
//					LogUtil.i("mp.setOnCompletionListener", "-------path = "+path);
//					if(!looping)
//					{
//						path = null;
//						//release();
//					}
//				}
//			});
		}
		
		try {
			mp.reset(); 
            descriptor = assetManager.openFd(path);  
            mp.setDataSource(descriptor.getFileDescriptor(),  
                    descriptor.getStartOffset(), descriptor.getLength()); 
            descriptor.close();
//			mp.setDataSource(path);
			mp.prepare();
			mp.setLooping(looping);
			if(position > 0)
			{
				LogUtil.i(TAG,"play()---position = "+position+"------path = "+path+"------");
				mp.seekTo(position);
			}
			else
			{
				duration = mp.getDuration();
				LogUtil.i(TAG,"play()---duration = "+duration+"------path = "+path+"------");
			}
			mp.start();
																																																																																						
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
