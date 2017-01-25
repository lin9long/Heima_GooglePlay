package com.linsaya.heima_googleplay.utils;

import android.content.Context;
import android.os.Handler;

import com.linsaya.heima_googleplay.global.GooglePlayApplication;

/**
 * Created by Administrator on 2017/1/25.
 */

public class UiUtils {
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
}
