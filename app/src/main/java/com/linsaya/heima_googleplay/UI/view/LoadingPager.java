package com.linsaya.heima_googleplay.UI.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.utils.UIUtils;

/**
 * Created by Administrator on 2017/1/26.
 */

public abstract class LoadingPager extends FrameLayout {
    private static int STATE_LOAD_UNDO = 1;
    private static int STATE_LOAD_LOADING = 2;
    private static int STATE_LOAD_ERROR = 3;
    private static int STATE_LOAD_EMPTY = 4;
    private static int STATE_LOAD_SUCCESS = 5;
    private int mCurrentState = -1;
    private View pager_undo;
    private View pager_error;
    private View pager_empty;
    private View pager_success;


    public LoadingPager(Context context) {
        super(context);
        initView();
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    public LoadingPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if (pager_undo == null) {
            pager_undo = View.inflate(UIUtils.getContext(), R.layout.pager_undo, null);
            addView(pager_undo);
        }

        if (pager_error == null) {
            pager_error = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
            addView(pager_error);
        }
        if (pager_empty == null) {
            pager_empty = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
            addView(pager_empty);
        }
        showCurrentPager();
    }

    private void showCurrentPager() {
        pager_undo.setVisibility((mCurrentState == STATE_LOAD_UNDO || mCurrentState == STATE_LOAD_LOADING) ? VISIBLE : GONE);
        pager_error.setVisibility((mCurrentState == STATE_LOAD_ERROR) ? VISIBLE : GONE);
        pager_empty.setVisibility((mCurrentState == STATE_LOAD_EMPTY) ? VISIBLE : GONE);

        if (pager_success == null && mCurrentState == STATE_LOAD_SUCCESS) {
            pager_success = onCreateSuccessPager();
            //此处要进行非空判断，因为该方法为抽象方法，由子类负责实现
            if (pager_success != null) {
                addView(pager_success);
            }

        }
        if (pager_success != null) {
            //设置成功页面的可见
            pager_success.setVisibility((mCurrentState == STATE_LOAD_SUCCESS) ? VISIBLE : GONE);
        }
    }

    /**
     * 请求网络数据，获取返回码赋值给mCurrentState，刷新界面
     */
    public void loadData() {
        System.out.println("方法执行啦！！！！！");
        if (mCurrentState != STATE_LOAD_LOADING) {
            mCurrentState = STATE_LOAD_LOADING;
            new Thread() {
                @Override
                public void run() {
                    final ResultState resultState = initData();
                    //运行在主线程内，将请求数据返回码赋值给mCurrentState，刷新界面
                    UIUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultState != null) {
                                mCurrentState = resultState.getState();
                                System.out.println("当前状态为："+resultState.getState());
                                showCurrentPager();
                            }
                        }
                    });
                }
            }.start();
        }
    }

    //枚举定义三个对象，重写构造方法
    public enum ResultState {
        STATE_SUCCESS(STATE_LOAD_SUCCESS),
        STATE_EMPTY(STATE_LOAD_EMPTY),
        STATE_ERROR(STATE_LOAD_ERROR);
        private int state;

        ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }

    public abstract View onCreateSuccessPager();

    public abstract ResultState initData();
}
