package com.lhd.mynews.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.lhd.mynews.R;
import com.lhd.mynews.fragment.ContentFragment;
import com.lhd.mynews.fragment.LeftMenuFragment;
import com.lhd.mynews.utils.DensityUtils;

public class MainActivity extends SlidingFragmentActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置左侧菜单
        setBehindContentView(R.layout.left_menu);

        //设置模式
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT);

        //设置滑动区域
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        //设置主页占200dp
        slidingMenu.setBehindOffset(DensityUtils.dip2px(this,200));

        initFragments();
    }

    private void initFragments()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_main, new ContentFragment(), "content_fragment");
        transaction.replace(R.id.fl_menu, new LeftMenuFragment(), "menu_fragment");

        transaction.commit();
    }

    public LeftMenuFragment getLeftMenuFragment()
    {

        return (LeftMenuFragment) getSupportFragmentManager().findFragmentByTag("menu_fragment");
    }
}
