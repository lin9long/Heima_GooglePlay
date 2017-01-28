package com.linsaya.heima_googleplay.UI.hodler;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.utils.UIUtils;


/**
 * Created by Administrator on 2017/1/27.
 */

public class MoreHolder extends BaseHolder<Integer> {
    public static final int STATE_MORE_MORE = 1;//显示加载更多
    public static final int STATE_MORE_ERROR = 2;//显示加载错误
    public static final int STATE_MORE_NONE = 3;//显示没有更多
    private LinearLayout ll_load_more;
    private TextView tv_load_error;


    public MoreHolder(boolean hasMore) {
        //调用setdata的方法，穿入整形数据，同时调用基类的refreshView方法，在子类中实现此方法
        setData(hasMore ? STATE_MORE_MORE : STATE_MORE_NONE);
    }

    @Override
    public View initView() {
        View view = UIUtils.influte(R.layout.listview_more_item);
        ll_load_more = (LinearLayout) view.findViewById(R.id.ll_load_more);
        tv_load_error = (TextView) view.findViewById(R.id.tv_load_error);
        return view;
    }

    @Override
    public void refreshView(Integer data) {

        switch (data) {
            case STATE_MORE_MORE:
                ll_load_more.setVisibility(View.VISIBLE);
                tv_load_error.setVisibility(View.GONE);
                break;
            case STATE_MORE_NONE:
                ll_load_more.setVisibility(View.GONE);
                tv_load_error.setVisibility(View.GONE);
                break;
            case STATE_MORE_ERROR:
                ll_load_more.setVisibility(View.GONE);
                tv_load_error.setVisibility(View.VISIBLE);
                break;


        }
    }

}
