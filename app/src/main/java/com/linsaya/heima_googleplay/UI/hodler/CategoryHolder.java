package com.linsaya.heima_googleplay.UI.hodler;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.domain.CategoryInfo;
import com.linsaya.heima_googleplay.http.HttpHelper;
import com.linsaya.heima_googleplay.utils.UIUtils;

import org.xutils.x;

/**
 * Created by Administrator on 2017/1/31.
 */

public class CategoryHolder extends BaseHolder<CategoryInfo> {

    private TextView tv_name1;
    private TextView tv_name2;
    private TextView tv_name3;
    private ImageView iv_icon1;
    private ImageView iv_icon2;
    private ImageView iv_icon3;
    private LinearLayout ll_grid1;
    private LinearLayout ll_grid3;
    private LinearLayout ll_grid2;

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.list_item_category, null);
        tv_name1 = (TextView) view.findViewById(R.id.tv_name1);
        tv_name2 = (TextView) view.findViewById(R.id.tv_name2);
        tv_name3 = (TextView) view.findViewById(R.id.tv_name3);
        iv_icon1 = (ImageView) view.findViewById(R.id.iv_icon1);
        iv_icon2 = (ImageView) view.findViewById(R.id.iv_icon2);
        iv_icon3 = (ImageView) view.findViewById(R.id.iv_icon3);
        ll_grid1 = (LinearLayout) view.findViewById(R.id.ll_grid1);
        ll_grid2 = (LinearLayout) view.findViewById(R.id.ll_grid2);
        ll_grid3 = (LinearLayout) view.findViewById(R.id.ll_grid3);

        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        tv_name1.setText(data.name1);
        tv_name2.setText(data.name2);
        tv_name3.setText(data.name3);

        x.image().bind(iv_icon1, HttpHelper.URL + "image?name=" + data.url1);
        x.image().bind(iv_icon2, HttpHelper.URL + "image?name=" + data.url2);
        x.image().bind(iv_icon3, HttpHelper.URL + "image?name=" + data.url3);

    }
}
