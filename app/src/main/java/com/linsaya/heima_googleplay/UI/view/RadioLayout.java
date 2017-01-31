package com.linsaya.heima_googleplay.UI.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.linsaya.heima_googleplay.R;

/**
 * 自定义使用图片宽高的帧布局，不留白边
 * Created by Administrator on 2017/1/30.
 */

public class RadioLayout extends FrameLayout {

    private float radio;

    public RadioLayout(Context context) {
        super(context);
    }

    public RadioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //如果用户已经在attrs中声明属性，并在xml中配置参数，系统会在R文件问自动生成对应命名的数组。
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadioLayout);
        //采用对应数字+“_”+属性命名的方式，找到该属性获取其中设置的参数
        radio = typedArray.getFloat(R.styleable.RadioLayout_radio, -1);
        System.out.println("radio:" + radio);
    }


    public RadioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重写空间的onMeasure方法，定义控件的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        //MeasureSpec.AT_MOST 至多模式，控件有多大显示多大，类似wrap_content
        //MeasureSpec.EXACTLY 确定模式，指定控件的宽高，类似match_parent
        //MeasureSpec.UNSPECIFIED 未指定模式


        //获取当前控件的宽高及显示模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY && radio > 0) {
            //根据屏幕宽度，减去两边的padding值，获取图片宽度
            int imagewidth = widthSize - getPaddingLeft() - getPaddingRight();
            //图片高度为宽度/比例值
            int imageheight = (int) (imagewidth / radio);
            //控件高度为图片高度加上上下两边的padding值
            heightSize = imageheight + getPaddingTop() + getPaddingBottom();
            //根据最新的高度，生成heightMeasureSpec（高度模式为确定模式）
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
