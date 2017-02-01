package com.linsaya.heima_googleplay.UI.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.FrameLayout;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.UI.hodler.AppDetailHolder;
import com.linsaya.heima_googleplay.UI.hodler.AppSafeinfoHolder;
import com.linsaya.heima_googleplay.UI.view.LoadingPager;
import com.linsaya.heima_googleplay.domain.AppInfo;
import com.linsaya.heima_googleplay.http.protocol.HomeDetailPortocol;
import com.linsaya.heima_googleplay.utils.UIUtils;

/**
 * Created by Administrator on 2017/2/1.
 */

public class HomeDetailActivity extends BaseActivity {

    private String packagename;
    private AppInfo data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setcontentview可以直接填充一个view对象
        LoadingPager mLoadingPager = new LoadingPager(this) {
            @Override
            public View onCreateSuccessPager() {
                return HomeDetailActivity.this.onCreateSuccessPager();
            }

            @Override
            public ResultState initData() {
                return HomeDetailActivity.this.initData();
            }
        };
        setContentView(mLoadingPager);
        //在actionbar中显示应用图标
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        //intent内携带参数传递数据
        packagename = getIntent().getStringExtra("packageName");
        System.out.println("包名为：" + packagename);
        //开始加载数据
        mLoadingPager.loadData();


    }

    public View onCreateSuccessPager() {
        //加载app信息详情页布局
        View view = UIUtils.influte(R.layout.framlayout_app_detail);
        //填充app信息详情数据
        FrameLayout fl_app_detail = (FrameLayout) view.findViewById(R.id.fl_app_detail);
        //在holder中定义布局文件
        AppDetailHolder appDetailHolder = new AppDetailHolder();
        appDetailHolder.setData(data);
        //将内容添加到对应的帧布局内
        fl_app_detail.addView(appDetailHolder.getView());

        FrameLayout fl_app_safeinfo = (FrameLayout) view.findViewById(R.id.fl_app_safeinfo);
        AppSafeinfoHolder appSafeinfoHolder = new AppSafeinfoHolder();
        appSafeinfoHolder.setData(data);
        fl_app_safeinfo.addView(appSafeinfoHolder.getView());

        return view;
    }

    public LoadingPager.ResultState initData() {
        HomeDetailPortocol portocol = new HomeDetailPortocol(packagename);
        //加载数据并作出判断，根据数据加载默认的页面
        data = portocol.getData(0);
        if (data != null) {
            return LoadingPager.ResultState.STATE_SUCCESS;
        } else {
            return LoadingPager.ResultState.STATE_ERROR;
        }
    }
}
