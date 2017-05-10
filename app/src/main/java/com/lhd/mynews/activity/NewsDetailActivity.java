package com.lhd.mynews.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lhd.mynews.R;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsDetailActivity extends Activity implements View.OnClickListener
{

    private String url;
    private WebView webview_news;

    private ProgressBar pb_news_loading;
    private WebSettings settings;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        url = getIntent().getStringExtra("url");

        initView();

        webview_news.loadUrl(url);
        settings = webview_news.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setBuiltInZoomControls(true);//当加载的页面，是可以缩放的页面的时候，自动显示缩放按钮

        settings.setUseWideViewPort(true);//当加载的页面，是可以缩放的页面的时候，双击缩放

        settings.setTextSize(WebSettings.TextSize.NORMAL);
        webview_news.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                pb_news_loading.setVisibility(View.GONE);
            }
        });


    }

    private void initView()
    {
        webview_news = (WebView) findViewById(R.id.webview_news);
        pb_news_loading = (ProgressBar) findViewById(R.id.pb_news_loading);

        findViewById(R.id.tv_base_title).setVisibility(View.GONE);
        findViewById(R.id.ib_menu).setVisibility(View.GONE);
        findViewById(R.id.ib_back).setVisibility(View.VISIBLE);
        findViewById(R.id.ib_textsize).setVisibility(View.VISIBLE);
        findViewById(R.id.ib_share).setVisibility(View.VISIBLE);

        findViewById(R.id.ib_back).setOnClickListener(this);
        findViewById(R.id.ib_textsize).setOnClickListener(this);
        findViewById(R.id.ib_share).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_textsize:
                showAlertDialog();
                break;
            case R.id.ib_share:
                showShare();
                break;
        }
    }

    private void showShare()
    {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        // 分享时Notification的图标和文字
        oks.setText("很好用哦");
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");
        // 启动分享GUI
        oks.show(this);
    }

    private int realPosition = 2;

    private void showAlertDialog()
    {
        String[] items = {"超大字体", "大号字体", "正常字体", "小号字体", "超小字体"};
        new AlertDialog.Builder(this).setTitle("设置文字大小").setSingleChoiceItems(items,
                realPosition, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                realPosition = which;
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                changeTextSize(realPosition);
            }
        }).setNegativeButton("取消", null).show();
    }

    private void changeTextSize(int position)
    {
        switch (position)
        {
            case 0://超大字号
                settings.setTextSize(WebSettings.TextSize.LARGEST);
                //                webSettings.setTextZoom(WebSettings.TextSize.values().);
                break;
            case 1://大字号
                settings.setTextSize(WebSettings.TextSize.LARGER);
                break;
            case 2://正常字号
                settings.setTextSize(WebSettings.TextSize.NORMAL);
                break;
            case 3://小号
                settings.setTextSize(WebSettings.TextSize.SMALLER);
                break;
            case 4://超小号
                settings.setTextSize(WebSettings.TextSize.SMALLEST);
                break;
        }
    }
}
