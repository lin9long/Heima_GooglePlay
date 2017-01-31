package com.linsaya.heima_googleplay.UI.fragmentfactory.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.linsaya.heima_googleplay.UI.view.LoadingPager;
import com.linsaya.heima_googleplay.UI.fragmentfactory.BaseFragment;
import com.linsaya.heima_googleplay.UI.view.fly.ShakeListener;
import com.linsaya.heima_googleplay.UI.view.fly.StellarMap;
import com.linsaya.heima_googleplay.http.protocol.RecommendPortocol;
import com.linsaya.heima_googleplay.utils.UIUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/1/26.
 */

public class RecommendFragment extends BaseFragment {

    private List<String> data;

    @Override
    public View onCreateSuccessPager() {
        final StellarMap stellarMap = new StellarMap(UIUtils.getContext());
        stellarMap.setAdapter(new RecommengAdapter());
        //随机方式，设置显示方式为6行9列
        stellarMap.setRegularity(5, 6);
        //设置文字显示内边距
        int padding = 20;
        stellarMap.setPadding(padding, padding, padding, padding);
        //初始化显示布局
        stellarMap.setGroup(0, true);
        //实现摇一摇换页的功能
        ShakeListener shakeListener = new ShakeListener(UIUtils.getContext());
        shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                //摇一摇手机，切换到下一页
                stellarMap.zoomIn();
            }
        });
        return stellarMap;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        RecommendPortocol portocol = new RecommendPortocol();
        data = portocol.getData(0);
        return check(data);
    }

    class RecommengAdapter implements StellarMap.Adapter {

        @Override
        public int getGroupCount() {
            //返回总页数为2页
            return 2;
        }

        @Override
        public int getCount(int group) {
            //根据页数摆放控件个数
            int count = data.size() / getGroupCount();
            if (group == getGroupCount() - 1) {
                count += data.size() % getGroupCount();
            }
            return count;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            //当前位置为当前的页面*页面控件个数，
            //因为data是一个数组，控件将页面分成两页，直接传入position不能显示所有的控件，
            position += group * getCount(group - 1);
            TextView view = new TextView(UIUtils.getContext());
            view.setText(data.get(position));
            Random random = new Random();
            //随机设置一个字体大小，16-25之间
            int textSize = 16 + random.nextInt(10);
            view.setTextSize(textSize);
            //随机设置一个字体颜色，通过随机rgb颜色实现
            int r = 20 + random.nextInt(200);
            int g = 20 + random.nextInt(200);
            int b = 20 + random.nextInt(200);
            view.setTextColor(Color.rgb(r, g, b));

            return view;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            System.out.println("isZoomIn:" + isZoomIn);
            if (isZoomIn) {
                //往下滑动，切换到上一页
                //当前页面页码大于0时
                if (group > 0) {
                    group--;
                } else {
                    group = getGroupCount() - 1; //当前页面页码小于0时，切换到上一页
                }
            } else {
                //往上滑动，切换到下一页
                //当前页面页码小于总页数时
                if (group < getGroupCount() - 1) {
                    group++;
                } else { //当前页面页码大于总页数时，返回第一页，实现循环
                    group = 0;
                }
            }
            return group;
        }
    }
}
