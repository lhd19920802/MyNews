package com.lhd.mynews.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lhd.mynews.R;
import com.lhd.mynews.activity.MainActivity;

/**
 * Created by lihuaidong on 2017/5/3 9:11.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：
 */
public class BasePager
{
    public Context mContext;
    public View rootView;

    public TextView tv_base_title;
    public ImageButton ib_menu;
    public FrameLayout fl_base_content;
    public ImageButton ib_switche_mode;
    public BasePager(Context context)
    {
        mContext=context;
        rootView=initView();
    }

    private View initView()
    {
        View view = View.inflate(mContext, R.layout.base_pager, null);


        tv_base_title = (TextView) view.findViewById(R.id.tv_base_title);
        ib_menu = (ImageButton) view.findViewById(R.id.ib_menu);
        fl_base_content = (FrameLayout) view.findViewById(R.id.fl_base_content);
        ib_switche_mode = (ImageButton) view.findViewById(R.id.ib_switche_mode);

        ib_menu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity mainActivity= (MainActivity) mContext;
                mainActivity.getSlidingMenu().toggle();
            }
        });
        return view;

    }

    public void initData()
    {

    }
}
