package com.linsaya.heima_googleplay.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Process;
import android.view.View;

import com.linsaya.heima_googleplay.global.GooglePlayApplication;

/**
 * Created by Administrator on 2017/1/26.
 */

public class UIUtils {
    //获取context
    public static Context getContext() {
        return GooglePlayApplication.getContext();
    }

    //获取handler
    public static Handler getHandler() {
        return GooglePlayApplication.getHandler();
    }

    //获取主线程id
    public static int getUiThreadId() {
        return GooglePlayApplication.getMyTid();
    }

    //获取字符串
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    //加载布局
    public static View influte(int id) {
        return View.inflate(getContext(), id, null);
    }

    //获取字符数组
    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    //获取drawable
    public static Drawable getDrawable(int id) {
        return getContext().getResources().getDrawable(id);
    }

    //获取颜色
    public static Drawable getColor(int id) {
        return getContext().getResources().getDrawable(id);
    }

    public static ColorStateList getColorStateList(int id) {
        return getContext().getResources().getColorStateList(id);
    }

    //dp值转换为像素
    public static int dip2px(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    //像素转换为dp
    public static float px2dip(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density;
    }

    //判断是否运行在主线程
    public static boolean isRunInUiThread() {
        int myTid = Process.myTid();
        if (getUiThreadId() == myTid) {
            return true;
        } else {
            return false;
        }
    }

    //让当前任务运行在主线程
    public static void runOnUiThread(Runnable r) {
        if (isRunInUiThread()) {
            r.run();
        } else {
            //如果是子线程，让其运行在主线程
            getHandler().post(r);
        }
    }

}

