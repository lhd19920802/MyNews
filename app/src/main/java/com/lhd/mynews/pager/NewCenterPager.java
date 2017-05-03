package com.lhd.mynews.pager;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lhd.mynews.activity.MainActivity;
import com.lhd.mynews.base.BasePager;
import com.lhd.mynews.domain.NewsCenterBean;
import com.lhd.mynews.fragment.LeftMenuFragment;
import com.lhd.mynews.utils.Url;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;


public class NewCenterPager extends BasePager
{

    private static final String TAG = NewCenterPager.class.getSimpleName();



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
        textView.setText("新闻中心的内容。。。");
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);

        //把当前TextView添加到FrameLayout中
        fl_base_content.addView(textView);


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

        List<NewsCenterBean.DataBean> leftMenuData = newsCenterBean.getData();
        MainActivity mainActivity= (MainActivity) mContext;
        LeftMenuFragment leftMenuFragment=mainActivity.getLeftMenuFragment();
        leftMenuFragment.setData(leftMenuData);
    }

    private NewsCenterBean parseData(String json)
    {

        return new Gson().fromJson(json, NewsCenterBean.class);
    }


}
