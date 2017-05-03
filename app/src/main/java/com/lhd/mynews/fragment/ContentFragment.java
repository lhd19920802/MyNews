package com.lhd.mynews.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lhd.mynews.R;
import com.lhd.mynews.activity.MainActivity;
import com.lhd.mynews.base.BaseFragment;
import com.lhd.mynews.base.BasePager;
import com.lhd.mynews.pager.GovaffairPager;
import com.lhd.mynews.pager.HomePager;
import com.lhd.mynews.pager.NewCenterPager;
import com.lhd.mynews.pager.SettingPager;
import com.lhd.mynews.pager.SmartServicePager;
import com.lhd.mynews.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihuaidong on 2017/5/2 21:14.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：
 */
public class ContentFragment extends BaseFragment
{
    private static final String TAG = ContentFragment.class.getSimpleName();
    private NoScrollViewPager vp_content_fragment;
    private RadioGroup rg_content_fragment;

    private List<BasePager> basePagerList;

    @Override
    public View initView()
    {
        View view = View.inflate(mContext, R.layout.content_fragment, null);
        vp_content_fragment = (NoScrollViewPager) view.findViewById(R.id.vp_content_fragment);
        rg_content_fragment = (RadioGroup) view.findViewById(R.id.rg_content_fragment);

        rg_content_fragment.check(R.id.rb_home);
        basePagerList = new ArrayList<>();


        return view;

    }

    @Override
    public void initData()
    {
        super.initData();
        Log.e("TAG", "主页的数据被初始化了");
        //准备数据
        basePagerList.add(new HomePager(getActivity()));//添加首页页面
        basePagerList.add(new NewCenterPager(getActivity()));//添加新闻页面
        basePagerList.add(new SmartServicePager(getActivity()));//添加智慧服务页面
        basePagerList.add(new GovaffairPager(getActivity()));//添加政要页面
        basePagerList.add(new SettingPager(getActivity()));//添加设置页面

        vp_content_fragment.setAdapter(new MyPagerAdapter());

        //设置监听RadioGroup选中状态的改变
        rg_content_fragment.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

        //监听ViewPager页面改变
        vp_content_fragment.addOnPageChangeListener(new MyOnPageChangeListener());

        //初始化首页的数据
        basePagerList.get(0).initData();

        //设置首页不可以滑动
        isEnableSlidingMenu(false);


    }



    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener
    {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {

        }

        @Override
        public void onPageSelected(int position)
        {
            switch (position)
            {
                case 0://主页面
                    rg_content_fragment.check(R.id.rb_home);
                    break;
                case 1:// 新闻
                    rg_content_fragment.check(R.id.rb_newscenter);
                    break;
                case 2://服务
                    rg_content_fragment.check(R.id.rb_smartservice);
                    break;
                case 3://政要
                    rg_content_fragment.check(R.id.rb_govaffair);
                    break;
                case 4://设置
                    rg_content_fragment.check(R.id.rb_setting);
                    break;
            }

            basePagerList.get(position).initData();


        }

        @Override
        public void onPageScrollStateChanged(int state)
        {

        }
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener
    {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {

            switch (checkedId)
            {
                case R.id.rb_home://切换到Viewpager第0个页面
                    vp_content_fragment.setCurrentItem(0, false);
                    isEnableSlidingMenu(false);
                    break;
                case R.id.rb_newscenter://切换到Viewpager第1个页面
                    vp_content_fragment.setCurrentItem(1, false);
                    isEnableSlidingMenu(true);

                    break;
                case R.id.rb_smartservice://切换到Viewpager第2个页面
                    vp_content_fragment.setCurrentItem(2, false);
                    isEnableSlidingMenu(false);

                    break;
                case R.id.rb_govaffair://切换到Viewpager第3个页面
                    vp_content_fragment.setCurrentItem(3, false);

                    isEnableSlidingMenu(false);
                    break;
                case R.id.rb_setting://切换到Viewpager第4个页面
                    vp_content_fragment.setCurrentItem(4, false);

                    isEnableSlidingMenu(false);
                    break;
            }
        }
    }

    /**
     * 是否让SlidingMenu可以滑动
     *
     * @param isEnableSlidingMenu
     */
    private void isEnableSlidingMenu(boolean isEnableSlidingMenu)
    {
        MainActivity mainActivity = (MainActivity) mContext;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        if (isEnableSlidingMenu)
        {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }
        else
        {
            //不可以滑动
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }

    }

    class MyPagerAdapter extends PagerAdapter

    {

        @Override
        public int getCount()
        {
            return basePagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            BasePager basePager = basePagerList.get(position);
            container.addView(basePager.rootView);
            //            basePager.initData();
            return basePager.rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            //            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }


}
