package com.linsaya.heima_googleplay.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import org.xutils.x;

/**
 * Created by Administrator on 2017/1/25.
 */

public class GooglePlayApplication extends Application {

    public static int myTid;
    public static Handler handler;
    public static Context context;

    public static int getMyTid() {
        return myTid;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        myTid = Process.myTid();
        x.Ext.init(this);
    }
}
