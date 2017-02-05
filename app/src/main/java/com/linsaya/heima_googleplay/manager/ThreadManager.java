package com.linsaya.heima_googleplay.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/2/3.
 */

public class ThreadManager {

    public static ThreadPool mThreadPool;

    public static ThreadPool getmThreadPool() {
        if (mThreadPool == null) {
            synchronized (ThreadPool.class) {
                if (mThreadPool == null) {
                    int coreCount = Runtime.getRuntime().availableProcessors();
                    int corePoolSize = coreCount * 2 + 1;
                    int maximumPoolSize = 10;
                    long keepAliveTime = 1;
                    mThreadPool = new ThreadPool(corePoolSize, maximumPoolSize, keepAliveTime);
                }
            }
        }
        return mThreadPool;
    }


    public static class ThreadPool {
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;
        private ThreadPoolExecutor executor;

        public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        public void execute(Runnable r) {
            if (executor == null) {
                executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
                        new LinkedBlockingDeque<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
            }
            executor.execute(r);
        }
        public void cancel(Runnable r){
            executor.getQueue().remove(r);
        }
    }
}
