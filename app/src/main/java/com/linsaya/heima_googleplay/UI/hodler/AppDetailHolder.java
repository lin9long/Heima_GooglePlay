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

import java.text.Format;

/**
 * Created by Administrator on 2017/2/1.
 */

public class AppDetailHolder extends BaseHolder<AppInfo> {

    private TextView tv_name;
    private TextView tv_date;
    private TextView tv_download_num;
    private TextView tv_size;
    private TextView tv_version;
    private RatingBar rb_star;
    private ImageView iv_icon;

    @Override
    public View initView() {
        View view = UIUtils.influte(R.layout.framlayout_detail_appinfo);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_download_num = (TextView) view.findViewById(R.id.tv_download_num);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_size = (TextView) view.findViewById(R.id.tv_size);
        tv_version = (TextView) view.findViewById(R.id.tv_version);
        rb_star = (RatingBar) view.findViewById(R.id.rb_star);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        tv_name.setText(data.name);
        tv_download_num.setText("下载量：" + data.downloadNum);
        tv_date.setText(data.date);
        tv_version.setText("版本：" + data.version);
        tv_size.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
        x.image().bind(iv_icon, HttpHelper.URL + "image?name=" + data.iconUrl);
        rb_star.setRating(data.stars);
    }
}
