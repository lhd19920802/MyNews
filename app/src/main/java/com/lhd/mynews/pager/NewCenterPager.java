package com.lhd.mynews.pager;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.lhd.mynews.activity.MainActivity;
import com.lhd.mynews.base.BasePager;
import com.lhd.mynews.base.MenuDetailBasePager;
import com.lhd.mynews.domain.NewsCenterBean;
import com.lhd.mynews.domain.TestBean;
import com.lhd.mynews.fragment.LeftMenuFragment;
import com.lhd.mynews.pager.detail.InteracMenuDetailPager;
import com.lhd.mynews.pager.detail.NewsMenuDetailPager;
import com.lhd.mynews.pager.detail.PhotosMenuDetailPager;
import com.lhd.mynews.pager.detail.TopicMenuDetailPager;
import com.lhd.mynews.utils.CacheUtils;
import com.lhd.mynews.utils.Url;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class NewCenterPager extends BasePager
{

    private static final String TAG = NewCenterPager.class.getSimpleName();

    private List<MenuDetailBasePager> menuDetailBasePagerList;
    private List<NewsCenterBean.DataBean> leftMenuData;


    public NewCenterPager(Activity activity)
    {
        super(activity);
    }

    @Override
    public void initData()
    {
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


        String saveJson = CacheUtils.getString(mContext, Url.NEWS_CENTER_URL);
        if (!TextUtils.isEmpty(saveJson))
        {
            processData(saveJson);
        }
        //获取数据
        //                getDataFromNet();

        //        getDataFromNet2();
        getDataFromNetByVolley();

    }

    private void getDataFromNetByVolley()
    {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest request = new StringRequest(Url.NEWS_CENTER_URL, new Response
                .Listener<String>()
        {
            @Override
            public void onResponse(String result)
            {
                //                Log.e("TAG", "volley  onResponse==" + s);
                Log.e(TAG, "result==" + result);
                Log.e(TAG, "线程==" + Thread.currentThread().getName());
                CacheUtils.putString(mContext, Url.NEWS_CENTER_URL, result);
                processData(result);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                Log.e("TAG", "volley onErrorResponse==" + volleyError);
            }
        })
        {
            //解决乱码问题
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response)
            {
                try
                {
                    String parsed = new String(response.data, "utf-8");
                    return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();

                }
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(request);
    }

    private void getDataFromNet2()
    {
        RequestParams params = new RequestParams("http://api.bilibili" + ""
                                                 + ".com/online_list?_device=android&platform=android&typeid=13&sign=a520d8d8f7a7240013006e466c8044f7");
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
                Log.e(TAG, "getDataFromNet2  result==" + result);
                Log.e(TAG, "线程==" + Thread.currentThread().getName());
                //                CacheUtils.putString(mContext, Url.NEWS_CENTER_URL, result);
                TestBean testBean = processData2(result);
                Log.e("TAG", "手动解析json成功===" + testBean.getList().get(1).getTitle());
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

    private void getDataFromNet()
    {
        RequestParams params = new RequestParams(Url.NEWS_CENTER_URL);
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
                Log.e(TAG, "线程==" + Thread.currentThread().getName());
                CacheUtils.putString(mContext, Url.NEWS_CENTER_URL, result);
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

    private TestBean processData2(String json)
    {
        TestBean testBean = new TestBean();
        try
        {
            List<TestBean.ListBean> list = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.optInt("code");

            testBean.setCode(code);
            JSONObject listObject = jsonObject.optJSONObject("list");
            testBean.setList(list);
            if (listObject != null)
            {
                for (int i = 0; i < 12; i++)
                {

                    JSONObject dataobject = listObject.optJSONObject("" + i);
                    if (dataobject != null)
                    {
                        TestBean.ListBean listBean = new TestBean.ListBean();
                        String aid = dataobject.optString("aid");
                        listBean.setAid(aid);
                        String author = dataobject.optString("author");
                        listBean.setAuthor(author);
                        String create = dataobject.optString("create");
                        listBean.setCreate(create);
                        String description = dataobject.optString("description");
                        listBean.setDescription(description);
                        String duration = dataobject.optString("duration");
                        listBean.setDuration(duration);
                        String pic = dataobject.optString("pic");
                        listBean.setPic(pic);
                        String title = dataobject.optString("title");
                        listBean.setTitle(title);
                        String typename = dataobject.optString("typename");
                        listBean.setTypename(typename);
                        int mid = dataobject.optInt("mid");
                        listBean.setMid(mid);

                        list.add(listBean);

                    }

                }
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();

        }

        return testBean;
    }

    private void processData(String json)
    {
        NewsCenterBean newsCenterBean = parseData(json);
        Log.e(TAG, "title==" + newsCenterBean.getData().get(0).getTitle());

        leftMenuData = newsCenterBean.getData();
        //准备数据
        menuDetailBasePagerList = new ArrayList<>();
        menuDetailBasePagerList.add(new NewsMenuDetailPager(mContext, leftMenuData.get(0)
                .getChildren()));
        menuDetailBasePagerList.add(new TopicMenuDetailPager(mContext, leftMenuData.get(0)
                .getChildren()));
        menuDetailBasePagerList.add(new PhotosMenuDetailPager(mContext));
        menuDetailBasePagerList.add(new InteracMenuDetailPager(mContext));


        MainActivity mainActivity = (MainActivity) mContext;
        LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();
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
        View rootView = menuDetailBasePager.rootView;
        menuDetailBasePager.initData();


        fl_base_content.removeAllViews();
        fl_base_content.addView(rootView);

        if (position == 2)
        {
            //显示切换按钮
            ib_switche_mode.setVisibility(View.VISIBLE);
        }
        else
        {
            //隐藏切换按钮
            ib_switche_mode.setVisibility(View.GONE);
        }


        ib_switche_mode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PhotosMenuDetailPager photosMenuDetailPager= (PhotosMenuDetailPager) menuDetailBasePagerList.get(2);
                photosMenuDetailPager.switchListAndGrid(ib_switche_mode);
            }
        });
    }
}
