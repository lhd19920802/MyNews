package com.lhd.mynews;

import android.app.Application;

import org.xutils.x;

/**
 * Created by lihuaidong on 2017/5/3 11:14.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：代表整个软件
 */
public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
