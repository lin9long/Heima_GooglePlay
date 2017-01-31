package com.linsaya.heima_googleplay.UI.fragmentfactory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linsaya.heima_googleplay.UI.view.LoadingPager;
import com.linsaya.heima_googleplay.utils.UIUtils;

import java.util.List;

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

    //抽象方法，子页面加载页面布局
    public abstract LoadingPager.ResultState onLoad();

    //开始加载数据，给MainActivity内的viewpager切换事件调用
    public void loadData() {
        if (mLoadingPager != null) {

            mLoadingPager.loadData();
        }
    }

    //校验缓存数据是否有效，返回对应的结果码
    public LoadingPager.ResultState check(Object obj) {
        if (obj != null) {
            if (obj instanceof List) {//判断当前对象是否为集合
                List list = (List) obj;
                if (list.isEmpty()) {//判断当前集合是否为空
                    return LoadingPager.ResultState.STATE_EMPTY;
                } else {
                    return LoadingPager.ResultState.STATE_SUCCESS;
                }

            }
        }
        return LoadingPager.ResultState.STATE_ERROR;
    }
}
