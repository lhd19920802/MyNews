package com.lhd.mynews.base;

import android.content.Context;
import android.view.View;

/**
 * Created by lihuaidong on 2017/5/4 8:36.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：
 */
public abstract class MenuDetailBasePager
{
    public final Context mContext;
    public View rootView;

    public MenuDetailBasePager(Context context)
    {
        mContext=context;
        rootView=initView();
    }

    public abstract View initView();

    public void initData()
    {

    }
}
