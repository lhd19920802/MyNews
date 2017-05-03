package com.lhd.mynews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lhd.mynews.R;
import com.lhd.mynews.utils.CacheUtils;
import com.lhd.mynews.utils.Constants;
import com.lhd.mynews.utils.DensityUtils;

public class GuideActivity extends Activity implements View.OnClickListener
{

    private ViewPager vpGuide;
    private Button btnGuideStart;
    private LinearLayout llGuideGroups;
    private ImageView ivGuideRed;

    private int[] images = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};

    //两灰点间距
    private float marginLeft;
    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-05-02 09:45:20 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews()
    {
        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        btnGuideStart = (Button) findViewById(R.id.btn_guide_start);
        llGuideGroups = (LinearLayout) findViewById(R.id.ll_guide_groups);
        ivGuideRed = (ImageView) findViewById(R.id.iv_guide_red);

        btnGuideStart.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-05-02 09:45:20 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v)
    {
        if (v == btnGuideStart)
        {
            // Handle clicks for btnGuideStart
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        findViews();
        initViews();
        initData();

        initListener();


    }


    private void initListener()
    {
        //计算两灰点之间的间距
        ivGuideRed.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()

        {
            @Override
            public void onGlobalLayout()
            {
                marginLeft = llGuideGroups.getChildAt(1).getLeft() - llGuideGroups.getChildAt(0)
                        .getLeft();
            }
        });
        vpGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                float leftMarg=(position+positionOffset)*marginLeft;
                RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(DensityUtils.dip2px(GuideActivity.this,10),DensityUtils.dip2px(GuideActivity.this,10));
                params.leftMargin= (int) leftMarg;
                ivGuideRed.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position)
            {
                if(position==images.length-1) {
                    //显示按钮
                    btnGuideStart.setVisibility(View.VISIBLE);
                }
                else
                {
                    //隐藏按钮
                    btnGuideStart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

        //开始体验的监听
        btnGuideStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CacheUtils.saveBoolean(GuideActivity.this, Constants.START_MAIN,true);
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initData()
    {
        for (int i = 0; i < images.length; i++)
        {
            ImageView iv_gray_point = new ImageView(this);
            iv_gray_point.setBackgroundResource(R.drawable.iv_gray_shape);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dip2px(this,10), DensityUtils.dip2px(this,10));
            if (i != 0)
            {
                params.leftMargin = DensityUtils.dip2px(this,10);
            }
                iv_gray_point.setLayoutParams(params);
            llGuideGroups.addView(iv_gray_point);
        }
    }

    private void initViews()
    {
        vpGuide.setAdapter(new MyPagerAdapter());
    }

    class MyPagerAdapter extends PagerAdapter
    {

        @Override
        public int getCount()
        {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            ImageView imageView = new ImageView(GuideActivity.this);
            imageView.setBackgroundResource(images[position]);
            container.addView(imageView);
            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }
    }
}
