package com.readboy.magicbook.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.readboy.magicbook.ApplicationRendering.R;

import java.util.HashMap;
import java.util.Map;

@SuppressLint ("UseSparseArrays")
public class SoundPlayer {

    private SoundPool soundPool;
    private boolean soundSt = true; //音效开关
    private Context context;
    private Map<Integer, Integer> soundMap; //音效资源id与加载过后的音源id的映射关系表

    private Map<Integer, Integer> streamIdMap;        //音效streamid与资源的映射表

    public SoundPlayer (Context c) {
        init (c);
    }

    /**
     * 初始化方法? ??
     *
     * @param c ? ???
     */
    public void init (Context c) {
        context = c;
        initSound ();
    }

    //初始化音效播放器? ?
    @SuppressLint ("UseSparseArrays")
    private void initSound () {
        streamIdMap = new HashMap<Integer, Integer> ();
        soundPool = new SoundPool (10, AudioManager.STREAM_MUSIC, 50);
        soundMap = new HashMap<Integer, Integer> ();

        soundMap.put (R.raw.speak_practise_boom, soundPool.load (context, R.raw.speak_practise_boom, 1));
        soundMap.put (R.raw.speak_practise_over_tip, soundPool.load (context, R.raw.speak_practise_over_tip, 1));
        soundMap.put (R.raw.speak_practise_result_comeon,
                      soundPool.load (context, R.raw.speak_practise_result_comeon, 1));
        soundMap.put (R.raw.speak_practise_result_great,
                      soundPool.load (context, R.raw.speak_practise_result_great, 1));
        soundMap.put (R.raw.speak_practise_result_star, soundPool.load (context, R.raw.speak_practise_result_star, 1));
        soundMap.put (R.raw.speak_practise_reward_star, soundPool.load (context, R.raw.speak_practise_reward_star, 1));
        soundMap.put (R.raw.speak_practise_start_tip, soundPool.load (context, R.raw.speak_practise_start_tip, 1));
        soundMap.put (R.raw.lus_zkw_star, soundPool.load (context, R.raw.lus_zkw_star, 1));
        soundMap.put (R.raw.mfs_001, soundPool.load (context, R.raw.mfs_001, 1));

    }


    /**
     * ?* 播放音效? ??
     * ?* @param resId 音效资源id
     **/
    public void playSound (int resId) {
        int streamId = 0;

        if (soundSt == false) { return; }

        Integer soundId = soundMap.get (resId);

        if (soundId != null) {
            streamId = soundPool.play (soundId, 1, 1, 1, 0, 1);
        }
        streamIdMap.put (resId, streamId);
        this.resId = resId;
        LogUtil.d ("", "mhc---playSound()---resId = " + resId);
    }

    //停止音效
    public void stopSound () {
        if (streamIdMap.get (resId) != null) { soundPool.stop (streamIdMap.get (resId)); }
        LogUtil.d ("", "mhc---stopSound()---resId = " + resId);

    }

    private int resId = 0;

    //暂停音效
    public void pauseSound (int resId) {
        this.resId = resId;
        if (streamIdMap.get (resId) != null) { soundPool.pause (streamIdMap.get (resId)); }
    }

    //恢复音效
    public void resumeSound (int resId) {
        if (streamIdMap.get (resId) != null) { soundPool.resume (streamIdMap.get (resId)); }
    }


    public void releseSound () {
        if (streamIdMap != null) { streamIdMap.clear (); }

        if (soundMap != null) { soundMap.clear (); }

        if (soundPool != null) { soundPool.release (); }
    }


    public boolean isSoundSt () {
        return soundSt;
    }

    public void setSoundSt (boolean soundSt) {
        this.soundSt = soundSt;
    }
}
