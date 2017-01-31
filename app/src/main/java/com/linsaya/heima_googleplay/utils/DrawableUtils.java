package com.linsaya.heima_googleplay.utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import org.xutils.common.task.AbsTask;

/**
 * Created by Administrator on 2017/1/30.
 */

public class DrawableUtils {
    public GradientDrawable getGradientDrawable(int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setCornerRadius(radius);
        return drawable;
    }

    public StateListDrawable getSelector(Drawable press, Drawable normal) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, press);
        drawable.addState(new int[]{}, normal);
        return drawable;
    }

    public StateListDrawable getSelector(int press, int normal, int radius) {
        GradientDrawable normalDrawable = getGradientDrawable(normal, radius);
        GradientDrawable pressDrawable = getGradientDrawable(press, radius);
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressDrawable);
        drawable.addState(new int[]{}, normalDrawable);

        return drawable;
    }
}
