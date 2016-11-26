package com.rock.teachlibrary.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.rock.teachlibrary.utils.SDCardUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Rock on 2016/8/15.
 */
public class DiskCache {

    private static final String TAG = DiskCache.class.getName();

    /**
     * 向 SD 卡中缓存图片
     * @param url
     * @param bitmap
     *
     * png缓存的图片会偏大(基本等于无损)
     * jpeg缓存的图片可以进行质量压缩，但是当它缓存png图片，可能会丢失透明像素
     *
     */
    public void put(String url, Bitmap bitmap){
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(SDCardUtil.urlToPath(url));
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);

        } catch (FileNotFoundException e) {
            Log.e(TAG, "put: 文件缓存异常" );
        }

    }

    /**
     * 从 SD 卡中获取图片
     * @param url
     */
    public Bitmap get(String url){
        // 先判断是否存在，不存在直接返回null，不进行decode
        File file = new File(SDCardUtil.urlToPath(url).getAbsolutePath());
        if (!file.exists()) {
            return  null;
        }
        // 在没有图片的时候，会返回null，并且给咱们进行Log
        Bitmap bitmap = BitmapFactory.decodeFile(SDCardUtil.urlToPath(url).getAbsolutePath());

        return bitmap;
    }

}
