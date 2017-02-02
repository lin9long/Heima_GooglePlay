package com.linsaya.heima_googleplay.UI.fragmentfactory.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.linsaya.heima_googleplay.UI.view.FlowLayout;
import com.linsaya.heima_googleplay.UI.view.LoadingPager;
import com.linsaya.heima_googleplay.UI.fragmentfactory.BaseFragment;
import com.linsaya.heima_googleplay.UI.view.MyFlowLayout;
import com.linsaya.heima_googleplay.http.protocol.HotPortocol;
import com.linsaya.heima_googleplay.utils.DrawableUtils;
import com.linsaya.heima_googleplay.utils.UIUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/1/26.
 */

public class HotFragment extends BaseFragment {

    private List<String> data;

    @Override
    public View onCreateSuccessPager() {
        //生成一个flowLayout自定义控件
        //FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());
        MyFlowLayout flowLayout = new MyFlowLayout(UIUtils.getContext());
        //将flowLayout放入scrollView内可以使控件自由滑动
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        int size = UIUtils.dip2px(10);
        flowLayout.setPadding(size, size, size, size);
        //flowLayout.setVerticalSpacing(size);
        //flowLayout.setHorizontalSpacing(size);
        for (int i = 0; i < data.size(); i++) {
            //往flowLayout内填充textView控件
            TextView textView = new TextView(UIUtils.getContext());
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(size,size,size,size);
            final String name = data.get(i);
            textView.setText(name);
            //自定义DrawableUtils，在工具类内定义一个方法，获取圆角矩形及selector按压选择器
            DrawableUtils utils = new DrawableUtils();
            //随机设置一个字体颜色，通过随机rgb颜色实现
            Random random = new Random();
            int r = 20 + random.nextInt(200);
            int g = 20 + random.nextInt(200);
            int b = 20 + random.nextInt(200);
            int color = Color.rgb(r, g, b);
            int pressColor = 0xffcecece;
            //GradientDrawable gradientDrawable = utils.getGradientDrawable(color, UIUtils.dip2px(6));
            StateListDrawable selector = utils.getSelector(pressColor, color, UIUtils.dip2px(6));

            textView.setBackgroundDrawable(selector);
            flowLayout.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), name, Toast.LENGTH_SHORT).show();
                }
            });
        }

        scrollView.addView(flowLayout);

        return scrollView;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        HotPortocol hotPortocol = new HotPortocol();
        data = hotPortocol.getData(0);
        return check(data);
    }
}
