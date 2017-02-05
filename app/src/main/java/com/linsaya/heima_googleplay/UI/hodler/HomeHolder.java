package com.linsaya.heima_googleplay.UI.hodler;

import android.text.format.Formatter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.UI.view.ProgressArc;
import com.linsaya.heima_googleplay.domain.AppInfo;
import com.linsaya.heima_googleplay.domain.DownLoadInfo;
import com.linsaya.heima_googleplay.http.HttpHelper;
import com.linsaya.heima_googleplay.manager.DownLoadManager;
import com.linsaya.heima_googleplay.utils.UIUtils;

import org.xutils.x;


import static com.linsaya.heima_googleplay.R.id.tv_name;

/**
 * Created by Administrator on 2017/1/29.
 */

public class HomeHolder extends BaseHolder<AppInfo> implements DownLoadManager.DownLoadObserver, View.OnClickListener {

    private TextView tv_content;
    private ImageView iv_download;
    private TextView tv_name;
    private RatingBar rb_stars;
    private TextView tv_size;
    private ImageView iv_pic;
    private TextView tv_des;
    private FrameLayout fl_download;
    private DownLoadManager mDm;
    private DownLoadInfo downLoadInfo;
    private int mCurrentStates;
    private float mCurrentPos;
    private long mSize;
    private ProgressArc pbProgress;
    private TextView tv_download;
    private AppInfo mAppInfo;


    @Override
    public View initView() {
        View view = UIUtils.influte(R.layout.listview_home_item);
        //  tv_content = (TextView) view.findViewById(R.id.tv_content);
        iv_download = (ImageView) view.findViewById(R.id.iv_download);
        iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        rb_stars = (RatingBar) view.findViewById(R.id.rb_stars);
        tv_size = (TextView) view.findViewById(R.id.tv_size);
        tv_des = (TextView) view.findViewById(R.id.tv_des);
        tv_download = (TextView) view.findViewById(R.id.tv_download);
        fl_download = (FrameLayout) view.findViewById(R.id.fl_download);

        fl_download.setOnClickListener(this);

        pbProgress = new ProgressArc(UIUtils.getContext());
        pbProgress.setArcDiameter(UIUtils.dip2px(30));
        pbProgress.setProgressColor(R.color.progress);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(UIUtils.dip2px(32), UIUtils.dip2px(32));
        fl_download.addView(pbProgress, params);

        mDm = DownLoadManager.getInstance();
        mDm.registerObserver(this);
        return view;
    }


    @Override
    public void refreshView(AppInfo data) {
        // tv_content.setText(data.name);
        tv_name.setText(data.name);
        tv_des.setText(data.des);
        rb_stars.setRating(data.stars);
        tv_size.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
        x.image().bind(iv_pic, HttpHelper.URL + "image?name=" + data.iconUrl);
        System.out.println(data.id);

        mAppInfo = data;

        downLoadInfo = mDm.getDownLoadInfo(data);
        if (downLoadInfo != null) {
            mCurrentStates = downLoadInfo.currentStates;
            mCurrentPos = downLoadInfo.getProgress();
            mSize = downLoadInfo.size;

        } else {
            mCurrentStates = DownLoadManager.STATE_UNDO;
            mCurrentPos = 0;
        }
        refreshUI(mCurrentPos, mCurrentStates, mSize, getData().id);
    }

    /**
     * 刷新界面
     *
     * @param progress
     * @param state
     * @param id
     */
    private void refreshUI(float progress, int state, long size, String id) {
        //因为listview的重用机制，此处需要判断当前的进度更新条目是否为选中的条目，如果匹配则不更新进度
        if (!getData().id.equals(id)) {
            return;
        }
        mCurrentStates = state;
        mCurrentPos = progress;
        mSize = size;
        switch (state) {
            case DownLoadManager.STATE_UNDO:
                pbProgress.setBackgroundResource(R.drawable.ic_download);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tv_download.setText(UIUtils.getString(R.string.app_state_download));
                break;
            case DownLoadManager.STATE_WAITING:
                pbProgress.setBackgroundResource(R.drawable.ic_download);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_WAITING);
                tv_download.setText(UIUtils.getString(R.string.app_state_waiting));
                break;
            case DownLoadManager.STATE_DOWNLOADING:
                pbProgress.setBackgroundResource(R.drawable.ic_pause);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
                pbProgress.setProgress(progress, true);
                tv_download.setText((int) (progress * 100) + "%");
                break;
            case DownLoadManager.STATE_PAUSE:
                pbProgress.setBackgroundResource(R.drawable.ic_resume);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tv_download.setVisibility(View.VISIBLE);
                break;
            case DownLoadManager.STATE_FAIL:
                pbProgress.setBackgroundResource(R.drawable.ic_redownload);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tv_download.setText(UIUtils.getString(R.string.app_state_error));
                break;
            case DownLoadManager.STATE_SUCCESS:
                pbProgress.setBackgroundResource(R.drawable.ic_install);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tv_download.setText(UIUtils.getString(R.string.app_state_downloaded));
                break;

            default:
                break;
        }
    }


    // 主线程刷新ui
    private void refreshOnMainThread(final DownLoadInfo info) {
        // 判断要刷新的下载对象是否是当前的应用
        final AppInfo mAppInfo = getData();
        if (info.id.equals(mAppInfo.id)) {
            UIUtils.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    refreshUI(info.getProgress(), info.currentStates, info.size, mAppInfo.id);
                }
            });
        }
    }

    @Override
    public void onDownLoadStateChange(DownLoadInfo downLoadInfo) {
        refreshOnMainThread(downLoadInfo);
    }

    @Override
    public void onDownLoadProgressChange(DownLoadInfo downLoadInfo) {
        refreshOnMainThread(downLoadInfo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_download:
                // 根据当前状态来决定相关操作
                if (mCurrentStates == DownLoadManager.STATE_FAIL
                        || mCurrentStates == DownLoadManager.STATE_PAUSE
                        || mCurrentStates == DownLoadManager.STATE_UNDO) {
                    // 开始下载
                    mDm.downLoad(mAppInfo);
                } else if (mCurrentStates == DownLoadManager.STATE_DOWNLOADING
                        || mCurrentStates == DownLoadManager.STATE_WAITING) {
                    // 暂停下载
                    mDm.pause(mAppInfo);
                } else if (mCurrentStates == DownLoadManager.STATE_SUCCESS) {
                    // 开始安装
                    mDm.install(mAppInfo);
                }
                break;

            default:
                break;
        }
    }
}
