package com.linsaya.heima_googleplay.fragmentfactory;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linsaya.heima_googleplay.utils.UIUtils;

/**
 * Created by Administrator on 2017/1/26.
 */

public class BaseFragment extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView view = new TextView(UIUtils.getContext());
        view.setTextColor(Color.BLACK);
        view.setText(getClass().getSimpleName());
        return view;

    }
}
