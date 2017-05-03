package com.lhd.mynews.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.lhd.mynews.base.BasePager;


public class SmartServicePager extends BasePager
{

    public SmartServicePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        super.initData();

        //设置标题
        tv_base_title.setText("服务");
        //设置主页的内容
        System.out.println("智慧服务的数据被初始化了...");
        TextView textView = new TextView(mContext);
        textView.setText("智慧服务的内容。。。");
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);

        //把当前TextView添加到FrameLayout中
        fl_base_content.addView(textView);
    }
}
