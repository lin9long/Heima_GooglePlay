package com.linsaya.heima_googleplay.UI.hodler;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.domain.AppInfo;
import com.linsaya.heima_googleplay.http.HttpHelper;
import com.linsaya.heima_googleplay.utils.UIUtils;

import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/2/1.
 */

public class AppSafeinfoHolder extends BaseHolder<AppInfo> {

    private ImageView[] mSafepic;//安全标示图片
    private TextView[] mSafedes;//安全描述文字
    private LinearLayout[] mDesbar;//安全描述图标+文字
    private ImageView[] mSafeinfo;//安全描述图标
    private LinearLayout ll_des_root;
    private RelativeLayout rl_des_root;
    private int mHeight;
    private LinearLayout.LayoutParams mParams;
    private ImageView iv_arrow;

    @Override
    public View initView() {
        View view = UIUtils.influte(R.layout.framlayout_detail_safeinfo);
        mSafepic = new ImageView[4];
        mSafepic[0] = (ImageView) view.findViewById(R.id.iv_safe1);
        mSafepic[1] = (ImageView) view.findViewById(R.id.iv_safe2);
        mSafepic[2] = (ImageView) view.findViewById(R.id.iv_safe3);
        mSafepic[3] = (ImageView) view.findViewById(R.id.iv_safe4);

        mSafedes = new TextView[4];
        mSafedes[0] = (TextView) view.findViewById(R.id.tv_des1);
        mSafedes[1] = (TextView) view.findViewById(R.id.tv_des2);
        mSafedes[2] = (TextView) view.findViewById(R.id.tv_des3);
        mSafedes[3] = (TextView) view.findViewById(R.id.tv_des4);

        mSafeinfo = new ImageView[4];
        mSafeinfo[0] = (ImageView) view.findViewById(R.id.iv_des1);
        mSafeinfo[1] = (ImageView) view.findViewById(R.id.iv_des2);
        mSafeinfo[2] = (ImageView) view.findViewById(R.id.iv_des3);
        mSafeinfo[3] = (ImageView) view.findViewById(R.id.iv_des4);

        mDesbar = new LinearLayout[4];
        mDesbar[0] = (LinearLayout) view.findViewById(R.id.ll_des1);
        mDesbar[1] = (LinearLayout) view.findViewById(R.id.ll_des2);
        mDesbar[2] = (LinearLayout) view.findViewById(R.id.ll_des3);
        mDesbar[3] = (LinearLayout) view.findViewById(R.id.ll_des4);

        ll_des_root = (LinearLayout) view.findViewById(R.id.ll_des_root);
        rl_des_root = (RelativeLayout) view.findViewById(R.id.rl_des_root);
        iv_arrow = (ImageView) view.findViewById(R.id.iv_arrow);
        rl_des_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //触发动画效果
                toggle();
            }
        });

        return view;
    }

    private boolean isOpen = false;

    private void toggle() {
        //布局显示
        ValueAnimator animator = null;
        if (isOpen) {
            isOpen = false;
            animator = ValueAnimator.ofInt(mHeight, 0);
            //布局隐藏
        } else {
            isOpen = true;
            animator = ValueAnimator.ofInt(0, mHeight);
        }
        //设置动画更新监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取最新的高度
                Integer height = (int) animation.getAnimatedValue();
                //将最新高度设置给布局参数的高度
                mParams.height = height;
                //设置布局参数，实现动画效果
                ll_des_root.setLayoutParams(mParams);
            }
        });
        //设置动画状态监听
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (isOpen) {
                    iv_arrow.setImageResource(R.drawable.arrow_up);
                } else {
                    iv_arrow.setImageResource(R.drawable.arrow_down);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(200);
        animator.start();
    }

    @Override
    public void refreshView(AppInfo data) {
        List<AppInfo.Safeinfo> safe = data.safe;
        for (int i = 0; i < 4; i++) {
            if (i < safe.size()) {
                //获取当前位置的Safeinfo集合
                AppInfo.Safeinfo info = safe.get(i);
                //设置安全标示图片
                String url1 = HttpHelper.URL + "image?name=" + info.safeUrl;
                //设置安全描述图片
                String url2 = HttpHelper.URL + "image?name=" + info.safeDesUrl;
                x.image().bind(mSafepic[i], url1);
                mSafedes[i].setText(info.safeDes);
                x.image().bind(mSafeinfo[i], url2);

            } else {
                //隐藏不需要显示的安全标示图片
                mSafepic[i].setVisibility(View.GONE);
                //隐藏没有的安全描述文字+图标
                mDesbar[i].setVisibility(View.GONE);
            }

        }
        //获取LinearLayout的布局高度
        ll_des_root.measure(0, 0);
        mHeight = ll_des_root.getMeasuredHeight();
        //获取其中的布局参数
        mParams = (LinearLayout.LayoutParams) ll_des_root.getLayoutParams();
        //将高度设置为0，隐藏布局
        mParams.height = 0;
        ll_des_root.setLayoutParams(mParams);

    }
}
