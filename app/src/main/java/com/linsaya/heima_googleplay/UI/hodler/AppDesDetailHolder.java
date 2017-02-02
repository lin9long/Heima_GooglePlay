package com.linsaya.heima_googleplay.UI.hodler;

import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.domain.AppInfo;
import com.linsaya.heima_googleplay.utils.UIUtils;

/**
 * Created by Administrator on 2017/2/1.
 */

public class AppDesDetailHolder extends BaseHolder<AppInfo> {

    private TextView tv_detail_des;
    private TextView tv_detail_author;
    private ImageView iv_arrow;

    @Override
    public View initView() {
        View view = UIUtils.influte(R.layout.framlayout_detail_desinfo);
        tv_detail_des = (TextView) view.findViewById(R.id.tv_detail_des);
        tv_detail_author = (TextView) view.findViewById(R.id.tv_detail_author);
        iv_arrow = (ImageView) view.findViewById(R.id.iv_arrow);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        tv_detail_author.setText(data.author);
        tv_detail_des.setText(data.des);
    }

    /**
     * 此方法模拟获取只显示7行描述时，TextView控件的高度
     */
    public int getShortHeight() {
        //新建一个TextView，里面按控件布局的文字大小添加7行描述，获取其控件高度
        TextView tv_measure = new TextView(UIUtils.getContext());
        int width = tv_detail_des.getMeasuredWidth();
        tv_measure.setMaxLines(7);
        tv_measure.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv_measure.setText(getData().des);
        //获取测量的宽高参数,宽度已知为match_parent，可以传入原控件宽度，参数类型为确定值
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        //高度不确定为warp_content，可以传入一个允许的最大值，参数类型为最大
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);
        //开始测量，这个TextView为模拟测量控件，需要制定测量参数，才能正确测量
        tv_measure.measure(widthMeasureSpec, heightMeasureSpec);
        return tv_measure.getHeight();
    }
}
