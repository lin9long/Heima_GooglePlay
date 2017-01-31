package com.linsaya.heima_googleplay.UI.hodler;

import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.domain.SubjectInfo;
import com.linsaya.heima_googleplay.http.HttpHelper;
import com.linsaya.heima_googleplay.utils.UIUtils;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by Administrator on 2017/1/29.
 */

public class SubjectHolder extends BaseHolder<SubjectInfo> {
    private ImageView iv_subject;
    private TextView tv_des;

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.listview_subject_item, null);
        iv_subject = (ImageView) view.findViewById(R.id.iv_subject);
        tv_des = (TextView) view.findViewById(R.id.tv_des);
        return view;
    }

    @Override
    public void refreshView(SubjectInfo data) {
        tv_des.setText(data.des);
        ImageOptions imageOptions = new ImageOptions.Builder().setUseMemCache(false).build();
        x.image().bind(iv_subject, HttpHelper.URL + "image?name=" + data.url,imageOptions);
    }
}
