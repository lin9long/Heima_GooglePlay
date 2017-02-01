package com.linsaya.heima_googleplay.UI.fragmentfactory.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.provider.Contacts;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.linsaya.heima_googleplay.UI.activity.HomeDetailActivity;
import com.linsaya.heima_googleplay.UI.adapter.MyBaseAdapter;
import com.linsaya.heima_googleplay.UI.hodler.BaseHolder;
import com.linsaya.heima_googleplay.UI.hodler.HomeHeaderHolder;
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
    private List<String> mPicList;

    @Override
    public View onCreateSuccessPager() {
        ListView listView = new MyListView(UIUtils.getContext());
        HomeFragment.HomeAdapter HomeAdapter = new HomeFragment.HomeAdapter(data);

        //在此处添加顶部viewpager广告轮播条，代码全部在HomeHeaderHolder内实现，做到与HomeFragment的代码解耦
        HomeHeaderHolder headerHolder = new HomeHeaderHolder();
        listView.addHeaderView(headerHolder.getView());
        //此处需要传入顶部viewpager广告的url数组，需要在HomePortocol中定义一个方法，获取网络请求数据
        headerHolder.setData(mPicList);
        //Adapter必须要添加头布局后设置
        listView.setAdapter(HomeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //因为添加了头布局，所以当前位置要减去头布局
                AppInfo appInfo = data.get(position - 1);
                System.out.println("被点中的应用为：" + appInfo.packageName);
                String packageName = appInfo.packageName;
                Intent intent = new Intent(UIUtils.getContext(), HomeDetailActivity.class);
                intent.putExtra("packageName", packageName);
                startActivity(intent);
            }
        });
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
        //HomePortocol中定义一个方法，获取图片的url列表
        mPicList = homePortocol.getPictures();


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
