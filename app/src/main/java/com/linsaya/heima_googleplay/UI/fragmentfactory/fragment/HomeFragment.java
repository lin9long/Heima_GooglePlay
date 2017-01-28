package com.linsaya.heima_googleplay.UI.fragmentfactory.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.linsaya.heima_googleplay.UI.view.LoadingPager;
import com.linsaya.heima_googleplay.UI.fragmentfactory.BaseFragment;
import com.linsaya.heima_googleplay.utils.UIUtils;

/**
 * Created by Administrator on 2017/1/26.
 */
public class HomeFragment extends BaseFragment {
    @Override
    public View onCreateSuccessPager() {
        TextView view = new TextView(UIUtils.getContext());
        view.setTextColor(Color.BLACK);
        view.setText(getClass().getSimpleName());
        return view;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        System.out.println("-----------" + LoadingPager.ResultState.STATE_SUCCESS);
        return LoadingPager.ResultState.STATE_SUCCESS;
    }
}
