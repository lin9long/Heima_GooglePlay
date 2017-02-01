package com.linsaya.heima_googleplay.UI.hodler;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.http.HttpHelper;
import com.linsaya.heima_googleplay.utils.UIUtils;

import org.xutils.x;

import java.util.List;

/**
 * 头条viewpager的holder文件
 * <p>
 * Created by Administrator on 2017/1/31.
 */

public class HomeHeaderHolder extends BaseHolder<List<String>> {
    private List<String> data;
    private ViewPager mViewPager;
    private LinearLayout llContainer;
    private int previousPoint;

    @Override
    public View initView() {
        RelativeLayout rootView = new RelativeLayout(UIUtils.getContext());
        //因为该布局的父控件为Listview，所以需要使用listview的LayoutParams
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, UIUtils.dip2px(180));
        rootView.setLayoutParams(params);
        //使用代码创建viewpager
        mViewPager = new ViewPager(UIUtils.getContext());
        //设置viewpager的布局参数，注意要使用RelativeLayout的布局参数
        RelativeLayout.LayoutParams vpParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        //将viewpager添加到RelativeLayout内
        rootView.addView(mViewPager, vpParams);
        //添加底部指示器
        llContainer = new LinearLayout(UIUtils.getContext());

        RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置下部及右部的padding值
        llContainer.setPadding(0, 0, UIUtils.dip2px(10), UIUtils.dip2px(10));
        //设置布局位置,相对夫布局的右下部
        llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        rootView.addView(llContainer, llParams);


        return rootView;
    }

    @Override
    public void refreshView(final List<String> data) {
        this.data = data;
        //填充viewpager的数据
        mViewPager.setAdapter(new HeaderAdapter());
        //viewpager无限滑动：3.设置当前显示位置为中间第一页，这样开始也可以是实现左右无限滑动
        mViewPager.setCurrentItem(data.size() * 10000);
        //调用方法实现页面的自动跳转
        ViewPagerTask task = new ViewPagerTask();
        task.start();
        //添加小圆点到LinearLayout
        for (int i = 0; i < data.size(); i++) {
            ImageView point = new ImageView(UIUtils.getContext());
            //给每一个小圆点设置间距
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i == 0) {
                point.setBackgroundResource(R.drawable.indicator_selected);
            } else {
                point.setBackgroundResource(R.drawable.indicator_normal);
                params.leftMargin = UIUtils.dip2px(4);

            }
            //给每一个小圆点设置间距
            point.setLayoutParams(params);
            llContainer.addView(point);
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //获取当前的显示位置
                position = position % data.size();
                //通过位置获取当前显示的小圆点，将其背景换成被选中的小圆点
                ImageView point = (ImageView) llContainer.getChildAt(position);
                point.setBackgroundResource(R.drawable.indicator_selected);
                //通过位置获取前一个显示的小圆点，将其背景换成未被选中的小圆点
                ImageView prePoint = (ImageView) llContainer.getChildAt(previousPoint);
                prePoint.setBackgroundResource(R.drawable.indicator_normal);

                previousPoint = position;


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class ViewPagerTask implements Runnable {
        public void start() {
            //因为handler是在appcation中调用，每次循环必须要清理handler发送消息，否则会发送多个消息
            UIUtils.getHandler().removeCallbacksAndMessages(null);
            UIUtils.getHandler().postDelayed(this, 2000);
        }

        @Override
        public void run() {
            int currentitem = mViewPager.getCurrentItem();
            currentitem++;
            mViewPager.setCurrentItem(currentitem);
            //内部死循环，继续发送延迟消息，实现自动跳转页面
            UIUtils.getHandler().postDelayed(this, 2000);
        }
    }

    class HeaderAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //viewpager无限滑动：2.设置当前位置的position，与页面数量除余，防止出现越界
            position = position % data.size();
            String url = HttpHelper.URL + "image?name=" + data.get(position);
            ImageView imageView = new ImageView(UIUtils.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            x.image().bind(imageView, url);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            //viewpager无限滑动：1.设置pageradapter的页面数量，设置为无限大
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
