package com.linsaya.heima_googleplay.UI.fragmentfactory.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import com.linsaya.heima_googleplay.UI.adapter.MyBaseAdapter;
import com.linsaya.heima_googleplay.UI.hodler.AppHolder;
import com.linsaya.heima_googleplay.UI.hodler.BaseHolder;
import com.linsaya.heima_googleplay.UI.view.LoadingPager;
import com.linsaya.heima_googleplay.UI.fragmentfactory.BaseFragment;
import com.linsaya.heima_googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/26.
 */

public class AppFragment extends BaseFragment {
    private List<String> data;

    @Override
    public View onCreateSuccessPager() {
        ListView listView = new ListView(UIUtils.getContext());
        HomeAdapter HomeAdapter = new HomeAdapter(data);
        listView.setAdapter(HomeAdapter);
        return listView;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("测试数据：" + i);
        }
        return LoadingPager.ResultState.STATE_SUCCESS;
    }


    class HomeAdapter extends MyBaseAdapter<String> {


        public HomeAdapter(List<String> list) {
            super(list);
        }

        @Override
        public BaseHolder getHolder() {
            return new AppHolder();
        }

        @Override
        public List<String> onLoadMore() {
            List<String> data = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                data.add("这是加载更多数据了" + i);
            }
            SystemClock.sleep(2000);
            return data;
        }

        @Override
        public boolean hasMore() {
            return true;
        }
        //        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            ViewHolder holder = null;
//            if (convertView == null) {
//                //1.获取当前布局的view对象
//                convertView = UIUtils.influte(R.layout.listview_app_item);
//                holder = new ViewHolder();
//                //2.查找布局内部控件
//                holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
        //3.给布局设置标签
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//            holder.tv_content.setText(getItem(position));
//
//            return convertView;
//        }
    }

//    static class ViewHolder {
//        public TextView tv_content;
//    }
}
