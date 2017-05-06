package com.lhd.mynews.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lihuaidong on 2017/5/2 9:39.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：数据缓存工具类
 */
public class CacheUtils
{
    public static boolean getBoolean(Context context,String key)
    {
        SharedPreferences sp = context.getSharedPreferences("news", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static void saveBoolean(Context context, String key, boolean value)
    {
        SharedPreferences sp = context.getSharedPreferences("news", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static void putString(Context context, String key, String value)
    {
        SharedPreferences sp = context.getSharedPreferences("news", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences("news", Context.MODE_PRIVATE);
        return sp.getString(key,"");

    }
}
