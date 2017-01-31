package com.linsaya.heima_googleplay.UI.fragmentfactory.fragment;

import android.view.View;
import android.widget.ListView;

import com.linsaya.heima_googleplay.UI.adapter.MyBaseAdapter;
import com.linsaya.heima_googleplay.UI.hodler.BaseHolder;
import com.linsaya.heima_googleplay.UI.hodler.CategoryHolder;
import com.linsaya.heima_googleplay.UI.hodler.CategoryTitleHolder;
import com.linsaya.heima_googleplay.UI.view.LoadingPager;
import com.linsaya.heima_googleplay.UI.fragmentfactory.BaseFragment;
import com.linsaya.heima_googleplay.UI.view.MyListView;
import com.linsaya.heima_googleplay.domain.CategoryInfo;
import com.linsaya.heima_googleplay.http.protocol.CategoryPortocol;
import com.linsaya.heima_googleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/1/26.
 */

public class CategoryFragment extends BaseFragment {

    private List<CategoryInfo> data;

    @Override
    public View onCreateSuccessPager() {
        ListView listView = new MyListView(UIUtils.getContext());
        listView.setAdapter(new CategoryAdapter(data));
        return listView;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        CategoryPortocol categoryPortocol = new CategoryPortocol();
        data = categoryPortocol.getData(0);
        return check(data);
    }

    class CategoryAdapter extends MyBaseAdapter<CategoryInfo> {

        public CategoryAdapter(List<CategoryInfo> list) {
            super(list);
        }

        /**
         * 此处需要修改构造方法，传入position，重新自定义listview的显示样式
         * 将TYPE_NORMA的值修改为1，当现实标题时，现实类型的返回值为2；
         *
         * @param position
         * @return
         */
        @Override
        public int getInnerType(int position) {
            CategoryInfo info = data.get(position);
            if (info.istitle) {
                return super.getInnerType(position) + 1;
            } else {
                return super.getInnerType(position);
            }
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1;
        }

        /**
         * 重写hasMore的方法，传入false值，表示不存在更多数据
         *
         * @return
         */
        @Override
        public boolean hasMore() {
            return false;
        }

        @Override
        public BaseHolder getHolder(int position) {
            CategoryInfo info = data.get(position);
            if (info.istitle) {
                return new CategoryTitleHolder();
            } else {
                return new CategoryHolder();
            }


        }

        @Override
        public List<CategoryInfo> onLoadMore() {
            return null;
        }
    }
}
