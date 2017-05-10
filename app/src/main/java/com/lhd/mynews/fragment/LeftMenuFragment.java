package com.lhd.mynews.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lhd.mynews.R;
import com.lhd.mynews.activity.MainActivity;
import com.lhd.mynews.base.BaseFragment;
import com.lhd.mynews.domain.NewsCenterBean;
import com.lhd.mynews.pager.NewCenterPager;
import com.lhd.mynews.utils.DensityUtils;

import java.util.List;

/**
 * Created by lihuaidong on 2017/5/2 21:14.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：
 */
public class LeftMenuFragment extends BaseFragment
{
    private static final String TAG = LeftMenuFragment.class.getSimpleName();
    private List<NewsCenterBean.DataBean> leftMenuData;
    private ListView listView;
    private int currentPosition;
    private MyAdapter adapter;

    @Override
    public View initView()
    {
        listView = new ListView(mContext);

        listView.setPadding(0, DensityUtils.dip2px(mContext, 40), 0, 0);
        listView.setBackgroundColor(Color.BLACK);//设置ListView的背景
        listView.setDividerHeight(0);
        listView.setSelector(android.R.color.transparent);//把ListVeiw中某一个条按下效果屏幕



        return listView;

    }



    @Override
    public void initData()
    {
        super.initData();
//        switcPager(0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //变色
                currentPosition = position;
                adapter.notifyDataSetChanged();
                //关闭左侧菜单
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.getSlidingMenu().toggle();

                //切换新闻里面的页面到对应的部分

                Log.e(TAG, "currentPosition==" + currentPosition);
                switcPager(currentPosition);
            }
        });

        Log.e("TAG", "左侧菜单的数据被初始化了");
    }


    private void switcPager(int position)
    {
//        adapter.notifyDataSetChanged();
        MainActivity mainActivity= (MainActivity) mContext;
        ContentFragment contentFragment=mainActivity.getContentFragment();
        NewCenterPager newsCenterPager=contentFragment.getNewCenterPager();
        newsCenterPager.switchPager(position);
    }
    public void setData(List<NewsCenterBean.DataBean> leftMenuData)
    {

        this.leftMenuData = leftMenuData;
        for (int i = 0; i < leftMenuData.size(); i++)
        {
            Log.e(TAG, "title" + i + leftMenuData.get(i).getTitle());
        }

        //设置适配器
        adapter = new MyAdapter();
        listView.setAdapter(adapter);


    }

    class MyAdapter extends BaseAdapter
    {


        @Override
        public int getCount()
        {
            return leftMenuData.size();
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
            TextView textView = (TextView) View.inflate(mContext, R.layout.leftmenu_item, null);
            textView.setText(leftMenuData.get(position).getTitle());
//            if (currentPosition == position)
//            {
//                textView.setEnabled(true);
//            }
//            else
//            {
//                textView.setEnabled(false);
//            }

            textView.setEnabled(currentPosition == position);
            return textView;
        }
    }
}
