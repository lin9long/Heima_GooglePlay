package com.linsaya.heima_googleplay.UI.hodler;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.domain.AppInfo;
import com.linsaya.heima_googleplay.http.HttpHelper;
import com.linsaya.heima_googleplay.utils.UIUtils;

import org.xutils.x;

/**
 * Created by Administrator on 2017/1/27.
 */

public class AppHolder extends BaseHolder<AppInfo> {

    private TextView tv_content;
    private ImageView iv_download;
    private TextView tv_name;
    private RatingBar rb_stars;
    private TextView tv_size;
    private ImageView iv_pic;
    private TextView tv_des;


    //初始化布局，加载view，查找内部控件
    @Override
    public View initView() {
        View view = UIUtils.influte(R.layout.listview_app_item);
        //  tv_content = (TextView) view.findViewById(R.id.tv_content);
        iv_download = (ImageView) view.findViewById(R.id.iv_download);
        iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        rb_stars = (RatingBar) view.findViewById(R.id.rb_stars);
        tv_size = (TextView) view.findViewById(R.id.tv_size);
        tv_des = (TextView) view.findViewById(R.id.tv_des);
        return view;
    }

    //使用传入的data刷新界面
    @Override
    public void refreshView(AppInfo data) {
        tv_name.setText(data.name);
        tv_des.setText(data.des);
        rb_stars.setRating(data.stars);
        tv_size.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
        x.image().bind(iv_pic, HttpHelper.URL + "image?name=" + data.iconUrl);
    }

}
