package com.linsaya.heima_googleplay.UI.hodler;

import android.view.View;
import android.widget.ImageView;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.domain.AppInfo;
import com.linsaya.heima_googleplay.http.HttpHelper;
import com.linsaya.heima_googleplay.utils.UIUtils;

import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/2/1.
 */

public class AppPicDetailHolder extends BaseHolder<AppInfo> {
    private ImageView[] mDetailPic;

    @Override
    public View initView() {
        View view = UIUtils.influte(R.layout.hsv_detail_picinfo);
        mDetailPic = new ImageView[5];
        mDetailPic[0] = (ImageView) view.findViewById(R.id.iv_pic1);
        mDetailPic[1] = (ImageView) view.findViewById(R.id.iv_pic2);
        mDetailPic[2] = (ImageView) view.findViewById(R.id.iv_pic3);
        mDetailPic[3] = (ImageView) view.findViewById(R.id.iv_pic4);
        mDetailPic[4] = (ImageView) view.findViewById(R.id.iv_pic5);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        List<String> screen = data.screen;
        for (int i = 0; i < 5; i++) {
            if (i < screen.size()) {
                String picUrl = screen.get(i);
                String url = HttpHelper.URL + "image?name=" + picUrl;
                x.image().bind(mDetailPic[i], url);
            } else {
                mDetailPic[i].setVisibility(View.GONE);
            }
        }
    }
}
