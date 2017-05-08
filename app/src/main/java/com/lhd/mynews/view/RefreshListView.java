package com.lhd.mynews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lhd.mynews.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lihuaidong on 2017/5/7 18:52.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：自定义ListView实现下拉刷新和加载更多
 */
public class RefreshListView extends ListView
{

    //包括下拉刷新布局和顶部新闻的布局
    private LinearLayout refresh_topNews;

    private ImageView iv_header_refresh;
    private ProgressBar pb_header_refresh;

    private TextView tv_header_status;
    private TextView tv_header_time;

    //下拉刷新控件
    private View pullDownRefresh;
    private int refreshViewHeight;
    //顶部新闻的控件
    private View topNewsView;


    /**
     * 下拉刷新状态
     */
    public static final int PULL_DOWN_REFRESH = 0;


    /**
     * 手松刷新状态
     */
    public static final int RELEASE_REFRESH = 1;


    /**
     * 正在刷新状态
     */
    public static final int REFRESHING = 2;

    private int currentStatus = PULL_DOWN_REFRESH;

    private RotateAnimation upAnimation;
    private RotateAnimation downAnimation;

    public RefreshListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initHeaderView(context);
        initAnimation();
    }

    //实例化下拉刷新布局
    private void initHeaderView(Context context)
    {
        refresh_topNews = (LinearLayout) View.inflate(context, R.layout.refresh_headerview, null);

        iv_header_refresh = (ImageView) refresh_topNews.findViewById(R.id.iv_header_refresh);
        pb_header_refresh = (ProgressBar) refresh_topNews.findViewById(R.id.pb_header_refresh);
        tv_header_status = (TextView) refresh_topNews.findViewById(R.id.tv_header_status);
        tv_header_time = (TextView) refresh_topNews.findViewById(R.id.tv_header_time);

        /**
         * 下拉刷新控件
         */
        pullDownRefresh = refresh_topNews.findViewById(R.id.ll_pull_down_refresh);


        //        view.setPadding(0,-控件的高，0,0);//下拉刷新控件完全隐藏
        //        view.setPadding(0,0，0,0);//下拉刷新控件完全显示
        //        view.setPadding(0,控件的高，0,0);//下拉刷新控件2倍完全显示


        pullDownRefresh.measure(0, 0);
        refreshViewHeight = pullDownRefresh.getMeasuredHeight();
        Log.e("TAG", "refreshViewHeight==" + refreshViewHeight);
        pullDownRefresh.setPadding(0, -refreshViewHeight, 0, 0);


        //添加到ListView的头部

        addHeaderView(refresh_topNews);


    }

    private void initAnimation()
    {
        //逆时针旋转
        upAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation
                .RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true);
        downAnimation = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true);
    }

    private float startY;

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())

        {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //当下拉刷新的时候，不让再刷新
                if (currentStatus == REFRESHING)
                {
                    break;
                }


                //                boolean isDisplayTopNews = isDisplayTopNews();
                //
                //                if (!isDisplayTopNews)
                //                {
                //
                //                    break;
                //
                //                }
                float endY = ev.getY();
                float dy = endY - startY;
                if (dy > 0)
                {
                    float padintTop = -refreshViewHeight + dy;

                    if (padintTop >= 0 && currentStatus != RELEASE_REFRESH)
                    {
                        currentStatus = RELEASE_REFRESH;
                        System.out.println("手松刷新...");
                        refreshHeanderViewStatus();
                    }
                    else if (padintTop < 0 && currentStatus != PULL_DOWN_REFRESH)
                    {
                        currentStatus = PULL_DOWN_REFRESH;
                        System.out.println("下拉刷新...");
                        refreshHeanderViewStatus();
                    }


                    pullDownRefresh.setPadding(0, (int) padintTop, 0, 0);//下拉刷新控件动态的隐藏和显示
                }
                break;
            case MotionEvent.ACTION_UP:
                if (currentStatus == PULL_DOWN_REFRESH)
                {
                    pullDownRefresh.setPadding(0, -refreshViewHeight, 0, 0);
                }
                else if (currentStatus == RELEASE_REFRESH)
                {
                    currentStatus = REFRESHING;
                    refreshHeanderViewStatus();
                    pullDownRefresh.setPadding(0, 0, 0, 0);

                    //让外界调用接口 进行联网请求
                    if (onRefreshListener != null)
                    {
                        onRefreshListener.onRefresh();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }


    //更新顶部刷新控件的状态
    private void refreshHeanderViewStatus()
    {
        switch (currentStatus)

        {
            case PULL_DOWN_REFRESH:
                iv_header_refresh.startAnimation(downAnimation);
                tv_header_status.setText("下拉刷新");

                break;
            case RELEASE_REFRESH:
                iv_header_refresh.startAnimation(upAnimation);
                tv_header_status.setText("手松刷新");
                break;
            case REFRESHING:
                iv_header_refresh.clearAnimation();
                iv_header_refresh.setVisibility(View.GONE);
                tv_header_status.setText("正在刷新");
                pb_header_refresh.setVisibility(View.VISIBLE);
                break;
        }
    }

    private boolean isDisplayTopNews()
    {

        return false;
    }

    public void addTopNews_refresh(View topNewsView)
    {
        if (topNewsView != null)
        {

            this.topNewsView = topNewsView;
            refresh_topNews.addView(topNewsView);
        }
    }

    public void onFinishRefresh(boolean isSuccess)
    {
        pb_header_refresh.setVisibility(View.GONE);
        iv_header_refresh.clearAnimation();
        iv_header_refresh.setVisibility(View.VISIBLE);
        currentStatus=PULL_DOWN_REFRESH;
        pullDownRefresh.setPadding(0,-refreshViewHeight,0,0);
        if(isSuccess) {
            tv_header_time.setText(getSystemTime());
        }
    }
    //得到系统时间

    private String getSystemTime()
    {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    //定义接口
    public interface OnRefreshListener
    {
        public void onRefresh();
    }

    private OnRefreshListener onRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener)
    {
        this.onRefreshListener = onRefreshListener;
    }
}
