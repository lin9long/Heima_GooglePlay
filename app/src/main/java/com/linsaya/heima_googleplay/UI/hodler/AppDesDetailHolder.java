package com.linsaya.heima_googleplay.UI.hodler;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.domain.AppInfo;
import com.linsaya.heima_googleplay.utils.UIUtils;

/**
 * Created by Administrator on 2017/2/1.
 */

public class AppDesDetailHolder extends BaseHolder<AppInfo> {

    private TextView tv_detail_des;
    private TextView tv_detail_author;
    private ImageView iv_arrow;
    private LinearLayout.LayoutParams mParams;

    @Override
    public View initView() {
        View view = UIUtils.influte(R.layout.framlayout_detail_desinfo);
        tv_detail_des = (TextView) view.findViewById(R.id.tv_detail_des);
        tv_detail_author = (TextView) view.findViewById(R.id.tv_detail_author);
        iv_arrow = (ImageView) view.findViewById(R.id.iv_arrow);
        //点击控件，执行动画
        tv_detail_des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toggle();
            }
        });
        return view;
    }

    private boolean isOpen = false;

    private void toggle() {

        int longHeight = getLongHeight();
        int shortHeight = getShortHeight();
        ValueAnimator animator = null;
        if (isOpen) {
            isOpen = false;
            //当前控件全部描述所占高度大于7行时，才执行动画
            if (longHeight > shortHeight) {
                animator = ValueAnimator.ofInt(longHeight, shortHeight);
            }
        } else {
            isOpen = true;
            //当前控件全部描述所占高度大于7行时，才执行动画
            if (longHeight > shortHeight) {
                animator = ValueAnimator.ofInt(shortHeight, longHeight);
            }
        }
        //当前控件全部描述所占高度大于7行时，才执行动画
        if (animator != null) {
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int height = (int) animation.getAnimatedValue();
                    mParams.height = height;
                    tv_detail_des.setLayoutParams(mParams);
                }
            });
        }
        //当前控件全部描述所占高度大于7行时，才执行动画
        if (animator != null) {
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //获取当前动画执行控件的父控件
                    final ScrollView scrollView = getScrollView();
                    //此处需要将滑动操作放置在消息队列中执行，防止出现错误，runnable也是运行在主线程
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            //动画执行后自动滚动到最底部
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
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
            animator.setDuration(100);
            animator.start();
        }
    }

    @Override
    public void refreshView(AppInfo data) {
        tv_detail_author.setText(data.author);
        tv_detail_des.setText(data.des);
        //将初始化7行布局的方法防止在run方法内执行
        tv_detail_des.post(new Runnable() {
            @Override
            public void run() {
                //将描述控件的高度初始化设置为7行
                int shortHeight = getShortHeight();
                System.out.println("shortHeight:" + shortHeight);
                mParams = (LinearLayout.LayoutParams) tv_detail_des.getLayoutParams();
                mParams.height = shortHeight;
                tv_detail_des.setLayoutParams(mParams);
            }
        });

    }

    //查找
    public ScrollView getScrollView() {
        ViewParent parent = tv_detail_des.getParent();
        while (!(parent instanceof ScrollView)) {
            parent = parent.getParent();
        }
        return (ScrollView) parent;
    }

    /**
     * 此方法模拟获取只显示7行描述时，TextView控件的高度
     */
    public int getShortHeight() {
        //新建一个TextView，里面按控件布局的文字大小添加7行描述，获取其控件高度
        TextView tv_measure = new TextView(UIUtils.getContext());
        int width = tv_detail_des.getMeasuredWidth();
        tv_measure.setMaxLines(7);
        tv_measure.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv_measure.setText(getData().des);
        //获取测量的宽高参数,宽度已知为match_parent，可以传入原控件宽度，参数类型为确定值
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        //高度不确定为warp_content，可以传入一个允许的最大值，参数类型为最大
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);
        //开始测量，这个TextView为模拟测量控件，需要制定测量参数，才能正确测量
        tv_measure.measure(widthMeasureSpec, heightMeasureSpec);
        return tv_measure.getMeasuredHeight();
    }

    /**
     * 此方法模拟获取只显示全部描述时，TextView控件的高度
     */
    public int getLongHeight() {
        //新建一个TextView，里面按控件布局的文字大小添加7行描述，获取其控件高度
        TextView tv_measure = new TextView(UIUtils.getContext());
        int width = tv_detail_des.getMeasuredWidth();
        //tv_measure.setMaxLines(7);
        tv_measure.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv_measure.setText(getData().des);
        //获取测量的宽高参数,宽度已知为match_parent，可以传入原控件宽度，参数类型为确定值
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        //高度不确定为warp_content，可以传入一个允许的最大值，参数类型为最大
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);
        //开始测量，这个TextView为模拟测量控件，需要制定测量参数，才能正确测量
        tv_measure.measure(widthMeasureSpec, heightMeasureSpec);
        //此处获取测量的高度
        return tv_measure.getMeasuredHeight();
    }


}
