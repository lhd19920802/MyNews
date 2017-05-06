package com.lhd.mynews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lihuaidong on 2017/5/6 10:43.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：自定义ViewPager 解决内部ViewPager第一个页面不能向左滑动的bug
 */
public class HorizontalViewPager extends ViewPager
{
    public HorizontalViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }


    private float lastX;
    private float lastY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {

        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                // down的时候要先把事件传到最里面 然后根据情况是否是父亲拦截
                getParent().requestDisallowInterceptTouchEvent(true);
                lastX = ev.getRawX();
                lastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = ev.getRawX() - lastX;
                float dy = ev.getRawY() - lastY;
                //判断是水平滑动还是垂直滑动
                if (Math.abs(dx) > Math.abs(dy))
                {
                    //水平滑动
                    if (getCurrentItem() == 0 && dx > 0)
                    {
                        //从左向右滑 不请求
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    else if (getCurrentItem() == getAdapter().getCount() - 1 && dx < 0)
                    {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    else
                    {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                else
                {
                    //垂直滑动 不请求
                    getParent().requestDisallowInterceptTouchEvent(false);

                }

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
