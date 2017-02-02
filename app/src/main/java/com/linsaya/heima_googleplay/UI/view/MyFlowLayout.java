package com.linsaya.heima_googleplay.UI.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.linsaya.heima_googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/2.
 */

public class MyFlowLayout extends ViewGroup {


    private int mUsedWidth;
    private static final int MaxLine = 100;
    private Line mLine;
    private List<Line> mLineList = new ArrayList<>();

    private int mHorizontalSpacing = UIUtils.dip2px(6);
    private int mVerticalSpacing = UIUtils.dip2px(8);


    public MyFlowLayout(Context context) {
        super(context);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取控件的宽高
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //孩子的宽度最大为父控件窗体宽度，如果父控件的模式为精确模式，子控件为至多模式（wrap_content），如果父控件的模式不确定，子控件也不确定
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, (widthMode == MeasureSpec.EXACTLY) ? MeasureSpec.AT_MOST : widthMode);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(width, (heightMode == MeasureSpec.EXACTLY) ? MeasureSpec.AT_MOST : heightMode);
            //对子控件的宽高进行测量
            childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            if (mLine == null) {
                mLine = new Line();
            }
            //获取子控件的宽度
            int childWidth = childView.getMeasuredWidth();
            //定义一个变量，累加子控件的宽度
            mUsedWidth += childWidth;
            //当前子控件累加的宽度小于父控件宽度时

            if (mUsedWidth < width) {
                mLine.addView(childView);  //继续添加子控件
                mUsedWidth += mHorizontalSpacing;   //为子控件设置一个横向间距
                //当前子控件累加的宽度大于父控件宽度时，需要另起一行
                if (mUsedWidth > width) {
                    if (!newLine()) {
                        //如果创建新行失败，直接跳出for循环，不再添加孩子
                        break;
                    }
                }
            } else {
                //已超出边界：1.当前控件很长，只添加一条就超出边界了；
                if (mLine.getChildCount() == 0) {
                    mLine.addView(childView);
                    if (!newLine()) {
                        //如果创建新行失败，直接跳出for循环，不再添加孩子
                        break;
                    }
                } else {
                    // 2.当前行内已经存在控件，继续添加超出边界
                    if (!newLine()) {
                        //如果创建新行失败，直接跳出for循环，不再添加孩子
                        break;
                    }
                    //换行后继续添加控件，初始化已使用的宽度
                    mLine.addView(childView);
                    mUsedWidth += childWidth + mHorizontalSpacing;
                }
            }
        }
        //保存最后一行数据
        if (mLine != null && mLine.getChildCount() != 0 && !mLineList.contains(mLine)) {
            mLineList.add(mLine);
        }
        //拿到当前控件的总宽度
        int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
        //计算当前控件的总高度
        int totalHeight = 0;
        for (int j = 0; j < mLineList.size(); j++) {
            Line line = mLineList.get(j);
            int mMaxHeight = line.mMaxHeight;
            totalHeight += mMaxHeight;
        }
        totalHeight += getPaddingBottom() + getPaddingTop() + (mLineList.size() - 1) * mVerticalSpacing;
        setMeasuredDimension(totalWidth, totalHeight);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //创建一个新行的方法，添加成功后返回ture
    private boolean newLine() {
        //每次创建前需要保存上一行的数据
        mLineList.add(mLine);
        //当行数不超过最大值时，可以继续添加行
        if (mLineList.size() < MaxLine) {
            mLine = new Line();
            mUsedWidth = 0;
            return true;
        }
        return false;
    }


    class Line {
        private List<View> mChildList = new ArrayList<>();
        private int mLineWidth;
        public int mMaxHeight;

        //给行添加view对象
        public void addView(View view) {

            mChildList.add(view);
            //当前行的宽度
            mLineWidth += view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            mMaxHeight = mMaxHeight > measuredHeight ? mMaxHeight : measuredHeight;

        }

        //获取行内view的个数
        public int getChildCount() {
            return mChildList.size();
        }

        private void onLayout(int left, int top) {
            //计算剩余空间的大小
            int childCount = getChildCount();
            //当前屏幕的有效宽度为总宽度减去两边的padding值
            int validWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            //剩余宽度为有效宽度-控件孩子总宽度-孩子之间的间距
            int surplusWidth = validWidth - mLineWidth - (childCount - 1) * mHorizontalSpacing;
            //进行判断，当剩余空间大于0时
            if (surplusWidth >= 0) {
                //平均等分剩余空间
                int space = (int) ((float)surplusWidth / childCount + 0.5f);
                for (int i = 0; i < childCount; i++) {
                    View view = mChildList.get(i);
                    int measuredHeight = view.getMeasuredHeight();
                    int measuredWidth = view.getMeasuredWidth();
                    //将平均等分分配给每一个孩子
                    measuredWidth += space;
                    //因为宽度改变，需要重新measure孩子
                    int widthMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY);
                    int heightMeasureSpec = MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY);
                    view.measure(widthMeasureSpec, heightMeasureSpec);
                    //当控件比较矮的时候，取一个距离顶部的位移，使空间居中显示
                    int topoffset = (mMaxHeight - measuredHeight) / 2;
                    if (topoffset < 0) {
                        topoffset = 0;
                    }
                    //摆放布局
                    view.layout(left, top + topoffset, left + measuredWidth, top + topoffset + measuredHeight);
                    //因为下一个控件的左边距需要加上上一个控件的宽度，此处需要对left进行处理
                    left += measuredWidth + mHorizontalSpacing;
                }
            } else {
                View view = mChildList.get(0);
                view.layout(left, top, left + view.getMeasuredWidth(), top + view.getMeasuredHeight());
            }

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = l + getPaddingLeft();
        int top = t + getPaddingTop();

        for (int i = 0; i < mLineList.size(); i++) {
            Line line = mLineList.get(i);
            line.onLayout(left, top);
            top += line.mMaxHeight + mVerticalSpacing;
        }
    }
}
