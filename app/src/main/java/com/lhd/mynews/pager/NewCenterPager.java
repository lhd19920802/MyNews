package com.lhd.mynews.pager;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lhd.mynews.activity.MainActivity;
import com.lhd.mynews.base.BasePager;
import com.lhd.mynews.base.MenuDetailBasePager;
import com.lhd.mynews.domain.NewsCenterBean;
import com.lhd.mynews.fragment.LeftMenuFragment;
import com.lhd.mynews.pager.detail.InteracMenuDetailPager;
import com.lhd.mynews.pager.detail.NewsMenuDetailPager;
import com.lhd.mynews.pager.detail.PhotosMenuDetailPager;
import com.lhd.mynews.pager.detail.TopicMenuDetailPager;
import com.lhd.mynews.utils.CacheUtils;
import com.lhd.mynews.utils.Url;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class NewCenterPager extends BasePager
{

    private static final String TAG = NewCenterPager.class.getSimpleName();

    private List<MenuDetailBasePager> menuDetailBasePagerList;
    private List<NewsCenterBean.DataBean> leftMenuData;


    public NewCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        super.initData();

        //把按钮显示出来
        ib_menu.setVisibility(View.VISIBLE);

        //设置标题
        tv_base_title.setText("新闻");
        //设置主页的内容
        System.out.println("新闻中心的数据被初始化了...");
        TextView textView = new TextView(mContext);
//        textView.setText("新闻中心的内容。。。");
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);

        //把当前TextView添加到FrameLayout中
        fl_base_content.addView(textView);


        String saveJson=CacheUtils.getString(mContext,Url.NEWS_CENTER_URL);
        if(!TextUtils.isEmpty(saveJson)) {
            processData(saveJson);
        }
        //获取数据
        getDataFromNet();





    }

    private void getDataFromNet()
    {
        RequestParams params=new RequestParams(Url.NEWS_CENTER_URL);
        x.http().post(params, new Callback.CacheCallback<String>()
        {
            @Override
            public boolean onCache(String result)
            {
                return false;

            }

            @Override
            public void onSuccess(String result)
            {
                Log.e(TAG, "result==" + result);
                Log.e(TAG, "线程=="+Thread.currentThread().getName());
                CacheUtils.putString(mContext,Url.NEWS_CENTER_URL,result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback)
            {

            }

            @Override
            public void onCancelled(CancelledException cex)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });
    }

    private void processData(String json)
    {
        NewsCenterBean newsCenterBean=parseData(json);
        Log.e(TAG, "title==" + newsCenterBean.getData().get(0).getTitle());

        leftMenuData = newsCenterBean.getData();
        //准备数据
        menuDetailBasePagerList = new ArrayList<>();
        menuDetailBasePagerList.add(new NewsMenuDetailPager(mContext,leftMenuData.get(0).getChildren()));
        menuDetailBasePagerList.add(new TopicMenuDetailPager(mContext));
        menuDetailBasePagerList.add(new PhotosMenuDetailPager(mContext));
        menuDetailBasePagerList.add(new InteracMenuDetailPager(mContext));


        MainActivity mainActivity= (MainActivity) mContext;
        LeftMenuFragment leftMenuFragment=mainActivity.getLeftMenuFragment();
        leftMenuFragment.setData(leftMenuData);

        switchPager(0);

    }

    private NewsCenterBean parseData(String json)
    {

        return new Gson().fromJson(json, NewsCenterBean.class);
    }


    public void switchPager(int position)
    {
        tv_base_title.setText(leftMenuData.get(position).getTitle());
        MenuDetailBasePager menuDetailBasePager = menuDetailBasePagerList.get(position);
        View rootView=menuDetailBasePager.rootView;
        menuDetailBasePager.initData();


        fl_base_content.removeAllViews();
        fl_base_content.addView(rootView);
    }
}
