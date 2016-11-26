package com.rock.teachlibrary.utils;

import android.content.Context;

import com.rock.teachlibrary.ImageLoader;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SDCardUtil {
    private static final String TAG = SDCardUtil.class.getSimpleName();
    private static Context context = ImageLoader.getContext();
    /**
     * 获取缓存路径
     */
    public static File getCacheDir(){
        if (context == null) {
            throw new NullPointerException("ImageLoader 还没进行初始化");
        }
        return context.getCacheDir();
    }

    /**
     * url 转换成了它对应的文件存储的绝对路径
     * @param url
     * @return
     */
    public static File urlToPath(String url){
        return  new File(getCacheDir().getAbsolutePath() + File.separator + md5(url));

    }

    private static String subString(String url){
        int lastIndexOf = url.lastIndexOf("/");
        int lastIndexOfDot = url.lastIndexOf(".");
        return  url.substring(lastIndexOf,lastIndexOfDot);
    }

    /**
     * md5 是一种摘要算法
     *  单向的，不可逆，所以它呢不属于加密的范畴
     *  它属于一个验证
     *  MD5通常用来验证文件或数据完整
     */
    private static String md5(String url){

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // 将传进来的String 变成byte数组进行摘要获取
            md5.update(url.getBytes());
            // 将获取的摘要信息 获取 出来，byte中存的默认是16进制，进行转换 转换为Android
            // 直接可以使用的String ,char
            byte[] digest = md5.digest();
            return toHex(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String toHex(byte[] data){
        String ret = null;
        if (data != null) {
            StringBuilder sb = new StringBuilder();
            for (byte b : data) {
                // %2x 代表的就是 16进制的格式
                sb.append(String.format("%2x", b));
            }
            ret = sb.toString();
        }
        return ret;
    }

}
