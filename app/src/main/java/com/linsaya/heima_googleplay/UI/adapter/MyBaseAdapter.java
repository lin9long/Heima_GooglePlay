package com.linsaya.heima_googleplay.UI.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.linsaya.heima_googleplay.UI.hodler.BaseHolder;
import com.linsaya.heima_googleplay.UI.hodler.MoreHolder;
import com.linsaya.heima_googleplay.manager.ThreadManager;
import com.linsaya.heima_googleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/1/27.
 */

public abstract class MyBaseAdapter<T> extends android.widget.BaseAdapter {

    private List<T> list;
    private static final int TYPE_MORE = 0;
    //此处必须要返回0，因为系统默认显示正常数据时，返回0
    private static final int TYPE_NORMAL = 1;

    public MyBaseAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        //显示加载更多布局
        return list.size() + 1;
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //返回显示类型数量
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    //返回显示类型种类
    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return TYPE_MORE;
        } else {
            return getInnerType(position);
        }
    }

    //提供一个方法，供子类调用传入参数
    public int getInnerType(int position) {
        return TYPE_NORMAL;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        //1.获取当前布局的view对象
        //2.查找布局内部控件
        //3.给布局设置标签
        if (convertView == null) {
            //判断加载数据逻辑，因为存在两种布局，需要先做判断
            if (getItemViewType(position) == TYPE_MORE) {
                holder = new MoreHolder(hasMore());
            } else {
                holder = getHolder(position);
            }

        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        //判断加载数据逻辑，因为存在两种布局需要先做判断
        if (getItemViewType(position) != TYPE_MORE) {
            holder.setData(getItem(position));
        } else {
            //当前位置在数据列表底部，则加载更多布局的数据
            MoreHolder moreHolder = (MoreHolder) holder;
            //先判断当前状态是否为加载更多
            if (moreHolder.getData() == MoreHolder.STATE_MORE_MORE) {
                loadMore((MoreHolder) holder);
            }

        }

        return holder.getView();
    }

    //因为加载更多存在三种状态：
    //1.存在更多数据
    //2.加载更多数据失败
    //3.没有更多数据了
    public boolean hasMore() {
        return true;
    }

    public boolean isLoadMore = false;

    public void loadMore(final MoreHolder moreHolder) {
        if (!isLoadMore) {
            isLoadMore = true;
//            new Thread() {
//                @Override
//                public void run() {
//                    final List<T> moreData = onLoadMore();
//                    UIUtils.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (moreData != null) {
//                                //如果当前数据少于20条，则判断为没有更多数据
//                                if (moreData.size() < 20) {
//                                    moreHolder.setData(moreHolder.STATE_MORE_NONE);
//                                    Toast.makeText(UIUtils.getContext(), "没有更多数据了！", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    //如果当前数据大于20条，则判断为还有更多数据
//                                    moreHolder.setData(moreHolder.STATE_MORE_MORE);
//                                }
//                                //统一在此将数据添加到布局内，刷新界面显示
//                                list.addAll(moreData);
//                                MyBaseAdapter.this.notifyDataSetChanged();
//
//                            } else {
//                                //如果返回无数据，则证明是获取数据错误
//                                moreHolder.setData(moreHolder.STATE_MORE_ERROR);
//                            }
//                            isLoadMore = false;
//                        }
//                    });
//                }
//            }.start();
            ThreadManager.getmThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    final List<T> moreData = onLoadMore();
                    UIUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (moreData != null) {
                                //如果当前数据少于20条，则判断为没有更多数据
                                if (moreData.size() < 20) {
                                    moreHolder.setData(moreHolder.STATE_MORE_NONE);
                                    Toast.makeText(UIUtils.getContext(), "没有更多数据了！", Toast.LENGTH_SHORT).show();
                                } else {
                                    //如果当前数据大于20条，则判断为还有更多数据
                                    moreHolder.setData(moreHolder.STATE_MORE_MORE);
                                }
                                //统一在此将数据添加到布局内，刷新界面显示
                                list.addAll(moreData);
                                MyBaseAdapter.this.notifyDataSetChanged();

                            } else {
                                //如果返回无数据，则证明是获取数据错误
                                moreHolder.setData(moreHolder.STATE_MORE_ERROR);
                            }
                            isLoadMore = false;
                        }
                    });
                }
            });
        }
    }

    //获取当前数组大小，同时作为index返回给，作为数据的分页符
    public int getListSize() {
        return list.size();
    }


    //子类重写此方法,获取对应的viewholder
    public abstract BaseHolder getHolder(int position);

    //加载更多数据
    public abstract List<T> onLoadMore();
}
