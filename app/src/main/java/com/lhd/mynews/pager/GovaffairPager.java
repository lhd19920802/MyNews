package com.lhd.mynews.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.lhd.mynews.base.BasePager;


/**

 */
public class GovaffairPager extends BasePager
{

    public GovaffairPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        super.initData();

        //设置标题
        tv_base_title.setText("政要");
        //设置主页的内容
        System.out.println("政要的数据被初始化了...");
        TextView textView = new TextView(mContext);
        textView.setText("政要的内容。。。");
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);

        //把当前TextView添加到FrameLayout中
        fl_base_content.addView(textView);
    }
}
