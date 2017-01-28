package com.linsaya.heima_googleplay.UI.hodler;

import android.view.View;
import android.widget.TextView;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.utils.UIUtils;

/**
 * Created by Administrator on 2017/1/27.
 */

public class AppHolder extends BaseHolder<String> {

    private TextView tv_content;


    //初始化布局，加载view，查找内部控件
    @Override
    public View initView() {
        View view = UIUtils.influte(R.layout.listview_app_item);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        return view;
    }

    //使用传入的data刷新界面
    @Override
    public void refreshView(String data) {
        tv_content.setText(data);
    }

}
