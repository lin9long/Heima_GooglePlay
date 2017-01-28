package com.linsaya.heima_googleplay.UI.hodler;

import android.view.View;

/**
 * Created by Administrator on 2017/1/27.
 */

public abstract class BaseHolder<T> {

    private final View mRootView;
    private T data;


    public BaseHolder() {
        //1.获取当前布局的view对象
        mRootView = initView();
        mRootView.setTag(this);
    }

    public View getView() {
        return mRootView;
    }

    //外部穿入一个data
    public void setData(T data) {
        this.data = data;
        //立即使用data刷新界面
        refreshView(data);
    }

    public T getData() {
        return data;
    }

    public abstract View initView();

    public abstract void refreshView(T data);
}
