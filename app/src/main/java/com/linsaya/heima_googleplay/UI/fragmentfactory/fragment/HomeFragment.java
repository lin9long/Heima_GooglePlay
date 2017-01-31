package com.linsaya.heima_googleplay.UI.fragmentfactory.fragment;

import android.graphics.Color;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.linsaya.heima_googleplay.UI.adapter.MyBaseAdapter;
import com.linsaya.heima_googleplay.UI.hodler.BaseHolder;
import com.linsaya.heima_googleplay.UI.hodler.HomeHolder;
import com.linsaya.heima_googleplay.UI.view.LoadingPager;
import com.linsaya.heima_googleplay.UI.fragmentfactory.BaseFragment;
import com.linsaya.heima_googleplay.UI.view.MyListView;
import com.linsaya.heima_googleplay.domain.AppInfo;
import com.linsaya.heima_googleplay.http.protocol.HomePortocol;
import com.linsaya.heima_googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/26.
 */
public class HomeFragment extends BaseFragment {
    private List<AppInfo> data;

    @Override
    public View onCreateSuccessPager() {
        ListView listView = new MyListView(UIUtils.getContext());
        HomeFragment.HomeAdapter HomeAdapter = new HomeFragment.HomeAdapter(data);
        listView.setAdapter(HomeAdapter);
        return listView;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
//        data = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            data.add("测试数据：" + i);
//        }
        HomePortocol homePortocol = new HomePortocol();
        data = homePortocol.getData(0);


        return check(data);
    }

    class HomeAdapter extends MyBaseAdapter<AppInfo> {
        public HomeAdapter(List<AppInfo> list) {
            super(list);
        }

        @Override
        public BaseHolder getHolder(int position) {
            return new HomeHolder();
        }

        @Override
        public List<AppInfo> onLoadMore() {

//            List<String> data = new ArrayList<>();
//            for (int i = 0; i < 20; i++) {
//                data.add("这是加载更多数据了" + i);
//            }
//            SystemClock.sleep(2000);
            HomePortocol homePortocol = new HomePortocol();
            //此处传入分页大小，为上一次请求数据的集合大小
            List<AppInfo> data = homePortocol.getData(getListSize());
            return data;
        }
    }
}
