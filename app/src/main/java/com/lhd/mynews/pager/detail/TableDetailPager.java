package com.lhd.mynews.pager.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.refreshlistview.RefreshListView;
import com.google.gson.Gson;
import com.lhd.mynews.R;
import com.lhd.mynews.activity.NewsDetailActivity;
import com.lhd.mynews.base.MenuDetailBasePager;
import com.lhd.mynews.domain.NewsCenterBean;
import com.lhd.mynews.domain.TabDetailBean;
import com.lhd.mynews.utils.CacheUtils;
import com.lhd.mynews.utils.DensityUtils;
import com.lhd.mynews.utils.Url;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

//页签页面
public class TableDetailPager extends MenuDetailBasePager
{


    private static final String TAG = TableDetailPager.class.getSimpleName();
    public static final String ID_STRING_KEY = "id_string";
    //动态的数据
    private final NewsCenterBean.DataBean.ChildrenBean childrenBean;
    private final ImageOptions imageOptions;
    //    private final List<NewsCenterBean.DataBean.ChildrenBean> children;
    private String url;


    private ViewPager vp_tab_detailpager;
    private TextView tv_detail_title;
    private LinearLayout ll_point_group;
    private RefreshListView lv_tab_detail;
    /**
     * 顶部新闻的数据
     */
    private List<TabDetailBean.DataBean.TopnewsBean> topnews;

    //上一次红点的位置
    private int lastPosition;
    /**
     * 列表新闻的数据
     */
    private List<TabDetailBean.DataBean.NewsBean> news;

    private String moreUrl;

    private boolean isLoadMore = false;
    private MyBaseAdapter adapter;

    public TableDetailPager(Context context, NewsCenterBean.DataBean.ChildrenBean childrenBean)
    {
        super(context);


        this.childrenBean = childrenBean;

        imageOptions = new ImageOptions.Builder().setSize(DensityUtil.dip2px(240), DensityUtil
                .dip2px(160)).setRadius(DensityUtil.dip2px(5))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true)
                        // 加载中或错误图片的ScaleType
                        //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP).setLoadingDrawableId(R
                        .drawable.home_scroll_default).setFailureDrawableId(R.drawable
                        .home_scroll_default).build();
    }

    @Override
    public View initView()
    {
        View view = View.inflate(mContext, R.layout.tab_detail_pager, null);
        View headerView = View.inflate(mContext, R.layout.listview_header_viewpager, null);
        vp_tab_detailpager = (ViewPager) headerView.findViewById(R.id.vp_tab_detailpager);
        tv_detail_title = (TextView) headerView.findViewById(R.id.tv_detail_title);
        ll_point_group = (LinearLayout) headerView.findViewById(R.id.ll_point_group);
        lv_tab_detail = (RefreshListView) view.findViewById(R.id.listview_detail);

        //添加为头部
        //        lv_tab_detail.addHeaderView(headerView);
        lv_tab_detail.addTopNewsView(headerView);

        //设置刷新的监听
        lv_tab_detail.setOnRefreshListener(new MyOnRefreshListener());

        //设置ListView的点击监听
        lv_tab_detail.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TabDetailBean.DataBean.NewsBean newsItem = news.get(position - 1);
                Log.e(TAG, "标题==" + newsItem.getTitle() + "id==" + newsItem.getId());

                //先取
                String id_array = CacheUtils.getString(mContext, ID_STRING_KEY);
                if (!id_array.contains(newsItem.getId() + ""))
                {
                    CacheUtils.putString(mContext, ID_STRING_KEY, id_array + newsItem.getId() +
                                                                  ",");
                }
                adapter.notifyDataSetChanged();

                //跳转到阅读页面
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("url", newsItem.getUrl());
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void initData()
    {
        super.initData();
        System.out.println("页签页面数据被初始化了...");


        //        textView.setText(childrenBean.getTitle());
        url = Url.BASE_URL + childrenBean.getUrl();
        Log.e(TAG, "url==" + url + "   title==" + childrenBean.getTitle());

        String saveJson = CacheUtils.getString(mContext, url);
        if (!TextUtils.isEmpty(saveJson))
        {
            processData(saveJson);
        }
        getDataFromNet();

    }

    private void getDataFromNet()
    {
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(2000);
        x.http().get(params, new Callback.CommonCallback<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                CacheUtils.putString(mContext, url, result);

                Log.e(TAG, "onSuccess==" + result);
                processData(result);


                //联网成功
                lv_tab_detail.onFinishRefresh(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback)
            {
                Log.e(TAG, "onError==" + ex.getMessage());
                lv_tab_detail.onFinishRefresh(false);
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

    class MyOnRefreshListener implements RefreshListView.OnRefreshListener
    {


        @Override
        public void onPullDownRefresh()
        {
            getDataFromNet();
        }

        @Override
        public void onLoadMore()
        {
            if (TextUtils.isEmpty(moreUrl))
            {
                lv_tab_detail.onFinishRefresh(false);
                isLoadMore = false;
            }
            else
            {
                getMoreDataFromNet();
            }
        }
    }

    private void getMoreDataFromNet()
    {
        RequestParams params = new RequestParams(moreUrl);
        params.setConnectTimeout(2000);
        x.http().get(params, new Callback.CommonCallback<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                CacheUtils.putString(mContext, url, result);

                Log.e(TAG, "onSuccess==" + result);
                isLoadMore = true;
                processData(result);

                //联网成功
                lv_tab_detail.onFinishRefresh(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback)
            {
                Log.e(TAG, "onError==" + ex.getMessage());
                lv_tab_detail.onFinishRefresh(false);
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

    //解析并显示数据
    private void processData(String json)
    {
        TabDetailBean tabDetailBean = parseJson(json);
        Log.e(TAG, "title=====" + tabDetailBean.getData().getTopnews().get(1).getTitle());

        String more = tabDetailBean.getData().getMore();
        if (TextUtils.isEmpty(more))
        {
            moreUrl = "";
        }
        else
        {
            moreUrl = Url.BASE_URL + more;
        }
        if (!isLoadMore)
        {
            //集合中使用原来的数据


            topnews = tabDetailBean.getData().getTopnews();
            for (int i = 0; i < topnews.size(); i++)
            {

                Log.e(TAG, "图片的url==" + topnews.get(i).getTopimage());
            }
            vp_tab_detailpager.setAdapter(new MyPagerAdapter());
            //添加向导点
            addGuidePoint();

            //监听ViewPager的页面变化
            vp_tab_detailpager.addOnPageChangeListener(new MyOnPageChangeListener());

            tv_detail_title.setText(topnews.get(lastPosition).getTitle());

            //列表新闻的数据
            news = tabDetailBean.getData().getNews();
            adapter = new MyBaseAdapter();
            lv_tab_detail.setAdapter(adapter);
        }
        else
        {
            isLoadMore = false;
            news.addAll(tabDetailBean.getData().getNews());
            //刷新适配器
            adapter.notifyDataSetChanged();
        }


        handler.sendEmptyMessageDelayed(0, 2000);
    }

    private Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            if (msg.what == 0)
            {
                int item = (vp_tab_detailpager.getCurrentItem() + 1) % topnews.size();
                vp_tab_detailpager.setCurrentItem(item);

                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(0, 2000);
            }
        }
    };

    class MyBaseAdapter extends BaseAdapter

    {
        @Override
        public int getCount()
        {
            return news.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder viewHolder = null;
            if (convertView == null)
            {
                convertView = View.inflate(mContext, R.layout.tab_detalpager_item, null);
                viewHolder = new ViewHolder();
                viewHolder.iv_detailpager_icon = (ImageView) convertView.findViewById(R.id
                        .iv_detailpager_icon);
                viewHolder.tv_item_title = (TextView) convertView.findViewById(R.id.tv_item_title);
                viewHolder.tv_item_time = (TextView) convertView.findViewById(R.id.tv_item_time);

                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();

            }
            TabDetailBean.DataBean.NewsBean newsBean = news.get(position);
            viewHolder.tv_item_time.setText(newsBean.getPubdate());
            viewHolder.tv_item_title.setText(newsBean.getTitle());
            x.image().bind(viewHolder.iv_detailpager_icon, newsBean.getListimage());

            String id_array = CacheUtils.getString(mContext, ID_STRING_KEY);
            if (id_array.contains(newsBean.getId() + ""))
            {
                //包含 说明点击过 要变色
                viewHolder.tv_item_title.setTextColor(Color.GRAY);
            }
            else
            {
                viewHolder.tv_item_title.setTextColor(Color.BLACK);
            }
            return convertView;
        }

        class ViewHolder
        {
            ImageView iv_detailpager_icon;
            TextView tv_item_title;
            TextView tv_item_time;

        }

    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener
    {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
            //设置文本
            tv_detail_title.setText(topnews.get(position).getTitle());
            ll_point_group.getChildAt(lastPosition).setEnabled(false);
            ll_point_group.getChildAt(position).setEnabled(true);
            lastPosition = position;
        }

        @Override
        public void onPageSelected(int position)
        {

        }

        private boolean isDragging=false;
        @Override
        public void onPageScrollStateChanged(int state)
        {


            if (state == ViewPager.SCROLL_STATE_DRAGGING)
            {
                isDragging=true;
                Log.e("TAG", "SCROLL_STATE_DRAGGING");
                if (handler != null)
                {

                    handler.removeCallbacksAndMessages(null);
                }

            }
            else if (state == ViewPager.SCROLL_STATE_IDLE&&isDragging)
            {
                isDragging=false;
                Log.e("TAG", "SCROLL_STATE_IDLE");
                if (handler != null)
                {
                    handler.removeCallbacksAndMessages(null);
                    handler.sendEmptyMessageDelayed(0, 2000);
                }
            }
            else if (state == ViewPager.SCROLL_STATE_SETTLING&&isDragging)
            {
                isDragging=false;
                Log.e("TAG", "SCROLL_STATE_SETTLING");

                if (handler != null)
                {
                    handler.removeCallbacksAndMessages(null);

                    handler.sendEmptyMessageDelayed(0, 2000);
                }
            }
        }
    }

    private void addGuidePoint()
    {
        //根据数据个数使用图片资源添加ViewPager的向导 红点
        //由于保存了请求的数据，解析的方法会执行两次，会重复添加红点，所以添加之前要进行移除，以免重复添加
        ll_point_group.removeAllViews();
        for (int i = 0; i < topnews.size(); i++)
        {
            ImageView imageview = new ImageView(mContext);
            imageview.setBackgroundResource(R.drawable.dot_selector);
            //设置间距
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dip2px
                    (mContext, 8), DensityUtils.dip2px(mContext, 8));
            imageview.setLayoutParams(params);
            if (i == 0)
            {
                //变成红点
                imageview.setEnabled(true);
            }
            else
            {
                //默认
                imageview.setEnabled(false);
                params.leftMargin = DensityUtils.dip2px(mContext, 8);
            }

            ll_point_group.addView(imageview);

        }
    }

    class MyPagerAdapter extends PagerAdapter

    {
        @Override
        public int getCount()
        {
            return topnews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            ImageView imageView = new ImageView(mContext);
            imageView.setBackgroundResource(R.drawable.home_scroll_default);

            container.addView(imageView);

            //联网请求图片
            x.image().bind(imageView, topnews.get(position).getTopimage(), imageOptions);

//            imageView.setOnTouchListener(new MyOnTouchListener());

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }


        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

    }

//    class MyOnTouchListener implements View.OnTouchListener
//
//    {
//        @Override
//        public boolean onTouch(View v, MotionEvent event)
//        {
//            switch (event.getAction())
//            {
//                case MotionEvent.ACTION_DOWN:
//                    if (handler != null)
//                    {
//                        handler.removeCallbacksAndMessages(null);
//                    }
//                    break;
//                case MotionEvent.ACTION_CANCEL:
//                    if (handler != null)
//                    {
//                        handler.sendEmptyMessageDelayed(0, 2000);
//                    }
//                    break;
//                case MotionEvent.ACTION_UP:
//                    if (handler != null)
//                    {
//                        handler.sendEmptyMessageDelayed(0, 2000);
//                    }
//                    break;
//            }
//            return true;
//        }
//    }

    private TabDetailBean parseJson(String json)
    {


        return new Gson().fromJson(json, TabDetailBean.class);
    }


}
