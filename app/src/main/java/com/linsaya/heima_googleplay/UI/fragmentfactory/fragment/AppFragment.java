package com.linsaya.heima_googleplay.UI.fragmentfactory.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import com.linsaya.heima_googleplay.UI.adapter.MyBaseAdapter;
import com.linsaya.heima_googleplay.UI.hodler.AppHolder;
import com.linsaya.heima_googleplay.UI.hodler.BaseHolder;
import com.linsaya.heima_googleplay.UI.view.LoadingPager;
import com.linsaya.heima_googleplay.UI.fragmentfactory.BaseFragment;
import com.linsaya.heima_googleplay.UI.view.MyListView;
import com.linsaya.heima_googleplay.domain.AppInfo;
import com.linsaya.heima_googleplay.http.protocol.AppPortocol;
import com.linsaya.heima_googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/26.
 */

public class AppFragment extends BaseFragment {
    private List<AppInfo> data;

    @Override
    public View onCreateSuccessPager() {
        ListView listView = new MyListView(UIUtils.getContext());
        AppAdapter HomeAdapter = new AppAdapter(data);
        listView.setAdapter(HomeAdapter);
        return listView;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        AppPortocol appPortocol = new AppPortocol();
        data = appPortocol.getData(0);
        return check(data);
    }


    class AppAdapter extends MyBaseAdapter<AppInfo> {


        public AppAdapter(List<AppInfo> list) {
            super(list);
        }


        @Override
        public List<AppInfo> onLoadMore() {
//            List<String> data = new ArrayList<>();
//            for (int i = 0; i < 20; i++) {
//                data.add("这是加载更多数据了" + i);
//            }
//            SystemClock.sleep(2000);
            AppPortocol appPortocol = new AppPortocol();
            List<AppInfo> data = appPortocol.getData(getListSize());
            return data;
        }

        @Override
        public boolean hasMore() {
            return true;
        }

        @Override
        public BaseHolder getHolder(int position) {
            return new AppHolder();
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
