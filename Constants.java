package com.readboy.magicbook.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.readboy.magicbook.ApplicationRendering.R;


public class Constants {


    public final static String ASSETS_SP = "speak_practise/";
    public final static String SP_OVER_TIP = ASSETS_SP + "speak_practise_over_tip.mp3";
    public final static String SP_START_TIP = ASSETS_SP + "speak_practise_start_tip.mp3";

    public final static String SP_RESULT_TIP_GREAT = ASSETS_SP + "speak_practise_result_great.mp3";
    public final static String SP_RESULT_TIP_COMEON = ASSETS_SP + "speak_practise_result_comeon.mp3";

    public final static String SP_START_BOOM = ASSETS_SP + "speak_practise_boom.mp3";

    /**
     * 能力测验
     */
    public static String NLCY = "";
    /**
     * 重点难点
     */
    public static String ZDND = "";
    /**
     * 做习题
     */
    public static String ZXT = "";
    /**
     * 趣味测验
     */
    public static String QWCY = "";
    /**
     * 全文朗诵
     */
    public static String QWLS = "";

    public static void initAppString (Context context) {
        NLCY = context.getResources ().getString (R.string.nlcy);
        ZDND = context.getResources ().getString (R.string.zdnd);
        ZXT = context.getResources ().getString (R.string.zxt);
        QWCY = context.getResources ().getString (R.string.qwcy);
        QWLS = context.getResources ().getString (R.string.qwls);
    }

	/*
     * 检查练习跳转到游戏是否存在
	 */

    /**
     * 汉字达人
     */
    public static final String HanziAce = "com.readboy.hanziace";
    /**
     * 单词达人
     */
    public static final String WordGame = "com.readboy.wordgame";
    /**
     * 成语大会
     */
    public static final String IdiomGame = "com.readboy.idiomdoyen";

    /**
     * 检查应用是否存在
     *
     * @param context
     * @param packageName
     * @return
     */
    public boolean checkApkExist (Context context, String packageName) {
        if (packageName == null || "".equals (packageName)) { return false; }
        try {
            ApplicationInfo info = context.getPackageManager ()
                                          .getApplicationInfo (packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
