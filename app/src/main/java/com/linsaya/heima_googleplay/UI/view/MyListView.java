package com.linsaya.heima_googleplay.UI.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/1/29.
 */

public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        //将背景设置为全透明
        this.setSelector(new ColorDrawable());
        //去掉listview的分割线
        this.setDivider(null);
        //有时候滑动过程中会显示黑色，去掉滑动过程颜色
        this.setCacheColorHint(Color.TRANSPARENT);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
