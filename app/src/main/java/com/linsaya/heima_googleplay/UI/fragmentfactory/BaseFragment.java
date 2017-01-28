package com.linsaya.heima_googleplay.UI.fragmentfactory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linsaya.heima_googleplay.UI.view.LoadingPager;
import com.linsaya.heima_googleplay.utils.UIUtils;

/**
 * Created by Administrator on 2017/1/26.
 */

public abstract class BaseFragment extends android.support.v4.app.Fragment {

    private LoadingPager mLoadingPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        TextView view = new TextView(UIUtils.getContext());
//        view.setTextColor(Color.BLACK);
//        view.setText(getClass().getSimpleName());
        //此处实现LoadingPager的两个抽象方法，在内部再次创建抽象方法，交由子类实现
        mLoadingPager = new LoadingPager(UIUtils.getContext()) {
            @Override
            //创建获取数据成功的界面
            public View onCreateSuccessPager() {
                return BaseFragment.this.onCreateSuccessPager();
            }

            @Override
            //请求获取网络成功
            public ResultState initData() {
                return BaseFragment.this.onLoad();
            }
        };
        return mLoadingPager;
    }

    //加载网络数据成功后的页面
    public abstract View onCreateSuccessPager();

    public abstract LoadingPager.ResultState onLoad();

    //开始加载数据，给MainActivity内的viewpager切换事件调用
    public void loadData() {
        if (mLoadingPager != null) {
            System.out.println("当前状态为：");
            mLoadingPager.loadData();
        }
    }
}
