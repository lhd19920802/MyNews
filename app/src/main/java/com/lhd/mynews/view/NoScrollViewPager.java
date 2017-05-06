package com.lhd.mynews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lihuaidong on 2017/5/3 10:05.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：
 */
public class NoScrollViewPager extends ViewPager
{
    public NoScrollViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        return true;
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {

        return false;

    }
}
