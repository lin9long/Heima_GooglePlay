package com.linsaya.heima_googleplay.UI.hodler;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.UI.view.ProgressHorizontal;
import com.linsaya.heima_googleplay.domain.AppInfo;
import com.linsaya.heima_googleplay.domain.DownLoadInfo;
import com.linsaya.heima_googleplay.manager.DownLoadManager;
import com.linsaya.heima_googleplay.utils.UIUtils;

/**
 * Created by Administrator on 2017/2/3.
 */

public class AppDownLoadHolder extends BaseHolder<AppInfo> implements DownLoadManager.DownLoadObserver, View.OnClickListener {

    private DownLoadManager mDm;
    private int mCurrentStates;
    private float mCurrentPos;
    private Button btn_download;
    private FrameLayout fl_download;
    private ProgressHorizontal mProgress;
    private long mSize;
    private DownLoadInfo downLoadInfo;

    @Override
    public View initView() {
        View view = UIUtils.influte(R.layout.framlayout_detail_download);
        btn_download = (Button) view.findViewById(R.id.btn_download);
        fl_download = (FrameLayout) view.findViewById(R.id.fl_download);
        mDm = DownLoadManager.getInstance();
        mDm.registerObserver(this);
        btn_download.setOnClickListener(this);
        fl_download.setOnClickListener(this);
        mProgress = new ProgressHorizontal(UIUtils.getContext());
        mProgress.setProgressBackgroundResource(R.drawable.progress_bg);
        mProgress.setProgressResource(R.drawable.progress_normal);
        mProgress.setProgressTextSize(30);
        mProgress.setProgressTextColor(Color.WHITE);
        fl_download.addView(mProgress);

        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        downLoadInfo = mDm.getDownLoadInfo(data);
        if (downLoadInfo != null) {
            mCurrentStates = downLoadInfo.currentStates;
            mCurrentPos = downLoadInfo.getProgress();
            mSize = downLoadInfo.size;
            System.out.println("当前状态为：" + mCurrentStates);
            System.out.println("当前进度为：" + mCurrentPos);
        } else {
            mCurrentStates = DownLoadManager.STATE_UNDO;
            mCurrentPos = 0;
        }
        refreshUI(mCurrentStates, mCurrentPos, mSize);
    }

    private void refreshUI(int currentStates, float currentPos, long size) {
        mCurrentStates = currentStates;
        mCurrentPos = currentPos;
        mSize = size;
        switch (mCurrentStates) {
            case DownLoadManager.STATE_UNDO:
                btn_download.setVisibility(View.VISIBLE);
                fl_download.setVisibility(View.GONE);
                btn_download.setText("下载");
                break;
            case DownLoadManager.STATE_PAUSE:
                btn_download.setVisibility(View.GONE);
                fl_download.setVisibility(View.VISIBLE);
                mProgress.setCenterText("暂停");
                mProgress.setProgress(mCurrentPos / mSize);

                break;
            case DownLoadManager.STATE_DOWNLOADING:
             //   System.out.println("下载进度为：" + mSize);
                btn_download.setVisibility(View.GONE);
                fl_download.setVisibility(View.VISIBLE);
                mProgress.setCenterText("");
                mProgress.setProgress(mCurrentPos / mSize);

                break;
            case DownLoadManager.STATE_FAIL:
                btn_download.setVisibility(View.VISIBLE);
                fl_download.setVisibility(View.GONE);
                mProgress.setProgress(mCurrentPos);
                btn_download.setText("下载失败，请重试");
                break;
            case DownLoadManager.STATE_WAITING:
                btn_download.setVisibility(View.GONE);
                fl_download.setVisibility(View.VISIBLE);
                btn_download.setText("请稍后...");
                break;
            case DownLoadManager.STATE_SUCCESS:
                btn_download.setVisibility(View.VISIBLE);
                fl_download.setVisibility(View.GONE);
                btn_download.setText("安装");
                break;
        }
    }

    public void refreshUIOnMainThread(final DownLoadInfo info) {
        AppInfo appinfo = getData();
        if (appinfo.id.equals(info.id)) {
            UIUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    refreshUI(info.currentStates, info.currentPos, info.size);
                }
            });
        }
    }

    @Override
    public void onDownLoadStateChange(DownLoadInfo downLoadInfo) {
//        AppInfo info = getData();
//        if (info.id.equals(downLoadInfo.id)) {
        refreshUIOnMainThread(downLoadInfo);
//        }
    }

    @Override
    public void onDownLoadProgressChange(DownLoadInfo downLoadInfo) {
        AppInfo info = getData();
        if (info.id.equals(downLoadInfo.id)) {
            refreshUIOnMainThread(downLoadInfo);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_download:
            case R.id.btn_download:

                if (mCurrentStates == DownLoadManager.STATE_UNDO || mCurrentStates == DownLoadManager.STATE_PAUSE
                        || mCurrentStates == DownLoadManager.STATE_FAIL) {
                    mDm.downLoad(getData());
                } else if (mCurrentStates == DownLoadManager.STATE_DOWNLOADING || mCurrentStates == DownLoadManager.STATE_WAITING) {
                    mDm.pause(getData());
                } else if (mCurrentStates == DownLoadManager.STATE_SUCCESS) {
                    mDm.install(getData());
                }
                break;
        }
    }
}
