package com.readboy.magicbook.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by admin on 2016/9/8.
 */
public class BitmapUtils {
    private static final String TAG = "BitmapUtils";
    public static Bitmap compressImage(Bitmap image){

        ByteArrayOutputStream baos = new ByteArrayOutputStream ();

        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        int options =100;
        Log.d(TAG, "图片大小(MB)："+baos.toByteArray().length / 1024);
        while (baos.toByteArray().length / 1024 /1024> 4) {
            //循环判断如果压缩后图片是否大于200kb,大于继续压缩

            baos.reset();//重置baos即清空baos

            options -= 10;//每次都减少10

            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }

        ByteArrayInputStream isBm = new ByteArrayInputStream (baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中

        Bitmap bitmap= BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片

        return bitmap;

    }

}
