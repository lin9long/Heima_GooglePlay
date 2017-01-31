package com.linsaya.heima_googleplay.UI.fragmentfactory.fragment;

import android.view.View;
import android.widget.ListView;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.UI.adapter.MyBaseAdapter;
import com.linsaya.heima_googleplay.UI.hodler.BaseHolder;
import com.linsaya.heima_googleplay.UI.hodler.SubjectHolder;
import com.linsaya.heima_googleplay.UI.view.LoadingPager;
import com.linsaya.heima_googleplay.UI.fragmentfactory.BaseFragment;
import com.linsaya.heima_googleplay.UI.view.MyListView;
import com.linsaya.heima_googleplay.domain.SubjectInfo;
import com.linsaya.heima_googleplay.http.protocol.SubjectPortocol;
import com.linsaya.heima_googleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/1/26.
 */

public class SubjectFragment extends BaseFragment {
    List<SubjectInfo> data;

    @Override
    public View onCreateSuccessPager() {
        ListView listView = new MyListView(UIUtils.getContext());
        SubjectAdapter adapter = new SubjectAdapter(data);
        listView.setAdapter(adapter);
        return listView;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        SubjectPortocol portocol = new SubjectPortocol();
        data = portocol.getData(0);
        return check(data);
    }

    class SubjectAdapter extends MyBaseAdapter<SubjectInfo> {
        public SubjectAdapter(List<SubjectInfo> list) {
            super(list);
        }

        @Override
        public BaseHolder getHolder(int position) {
            return new SubjectHolder();
        }

        @Override
        public List<SubjectInfo> onLoadMore() {
            SubjectPortocol portocol = new SubjectPortocol();
            List<SubjectInfo> data = portocol.getData(getListSize());
            return data;
        }
    }
}
