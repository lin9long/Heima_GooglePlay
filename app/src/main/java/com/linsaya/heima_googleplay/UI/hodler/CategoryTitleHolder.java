package com.linsaya.heima_googleplay.UI.hodler;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.linsaya.heima_googleplay.domain.CategoryInfo;
import com.linsaya.heima_googleplay.utils.UIUtils;

/**
 * Created by Administrator on 2017/1/31.
 */

public class CategoryTitleHolder extends BaseHolder<CategoryInfo> {

    private TextView textView;

    @Override
    public View initView() {
        textView = new TextView(UIUtils.getContext());
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        textView.setText(data.title);
    }
}
