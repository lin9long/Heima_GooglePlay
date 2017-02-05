package com.linsaya.heima_googleplay.manager;


import android.content.Intent;
import android.net.Uri;

import com.linsaya.heima_googleplay.domain.AppInfo;
import com.linsaya.heima_googleplay.domain.DownLoadInfo;
import com.linsaya.heima_googleplay.http.HttpHelper;
import com.linsaya.heima_googleplay.utils.IOUtils;
import com.linsaya.heima_googleplay.utils.UIUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 下载的6种状态：未下载、等待下载、下载中、下载暂停、下载失败、下载成功
 * Created by Administrator on 2017/2/3.
 */

public class DownLoadManager {

    public List<DownLoadObserver> observerList = new ArrayList<>();
    public ConcurrentHashMap<String, DownLoadInfo> mDownLoadInfoMap = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, DownLoadTask> mDownLoadTaskMap = new ConcurrentHashMap<>();

    public static final int STATE_UNDO = 1;
    public static final int STATE_WAITING = 2;
    public static final int STATE_DOWNLOADING = 3;
    public static final int STATE_PAUSE = 4;
    public static final int STATE_FAIL = 5;
    public static final int STATE_SUCCESS = 6;

    public DownLoadManager() {
    }

    public static DownLoadManager getInstance() {
        return mDm;
    }

    public void registerObserver(DownLoadObserver observer) {
        if (observer != null && !observerList.contains(observer)) {
            observerList.add(observer);
        }
    }

    public void unRegisterObserver(DownLoadObserver observer) {
        if (observer != null && observerList.contains(observer)) {
            observerList.remove(observer);
        }
    }

    /**
     * 调用接口内的onDownLoadStateChange方法
     */
    public void notifDownLoadStateChange(DownLoadInfo downLoadInfo) {
        for (DownLoadObserver observer : observerList) {
            observer.onDownLoadStateChange(downLoadInfo);
        }
    }

    /**
     * 调用接口内的onDownLoadProgressChange方法
     */
    public void notifDownLoadProgressChange(DownLoadInfo downLoadInfo) {
        for (DownLoadObserver observer : observerList) {
            observer.onDownLoadProgressChange(downLoadInfo);
        }
    }

    public synchronized void downLoad(AppInfo info) {
        DownLoadInfo downLoadInfo = mDownLoadInfoMap.get(info.id);
        if (downLoadInfo == null) {
            downLoadInfo = DownLoadInfo.copy(info);
        }
        //将当前应用信息状态设置为等候（准备下载），通知观察者更新状态
        downLoadInfo.currentStates = STATE_WAITING;
        notifDownLoadStateChange(downLoadInfo);
        //将已开始下载的应用信息放如DownLoadInfo的hashmap集合中
        mDownLoadInfoMap.put(downLoadInfo.id, downLoadInfo);
        //将下载的方法写到DownLoadTask的类中，新建一个类
        DownLoadTask task = new DownLoadTask(downLoadInfo);
        if (task != null) {
            //将run对象放入线程池中执行
            ThreadManager.getmThreadPool().execute(task);
            //将run对象放入DownLoadTask的hashmap集合中
            mDownLoadTaskMap.put(downLoadInfo.id, task);
        }

    }

    //此处实现下载业务逻辑
    class DownLoadTask implements Runnable {
        public DownLoadInfo info;

        public DownLoadTask(DownLoadInfo info) {
            this.info = info;
        }

        @Override
        public void run() {
            System.out.println(info.packageName + "开始下载啦！");
            System.out.println("下载链接为：" + info.downloadUrl);
            //当前状态切换为正在下载
            info.currentStates = STATE_DOWNLOADING;
            notifDownLoadStateChange(info);
            //获取文件路径下的文件
            File file = new File(info.path);
            HttpHelper.HttpResult result;
            //需要重新下载的情况有三种：1.当前文件长度与记录的不符，2.当前文件不存在，3.当前文件下载记录位置为零
            if (file.length() != info.currentPos || !file.exists() || info.currentPos == 0) {
                //重新下载
                //删除错误文件
                file.delete();
                info.currentPos = 0;
                result = HttpHelper.download(HttpHelper.URL + "download?name=" + info.downloadUrl);

            } else {
                //断点续传
                result = HttpHelper.download(HttpHelper.URL + "download?name=" + info.downloadUrl + "&range=" + file.length());
            }
            if (result != null && result.getInputStream() != null) {
                //开始下载
                InputStream is = result.getInputStream();
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file, true);
                    int len;
                    byte[] buffer = new byte[1024];
                    while ((len = is.read(buffer)) != -1 && info.currentStates == STATE_DOWNLOADING) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                        //更新当前下载位置的长度
                        info.currentPos += len;
                        //通知系统更新下载进度
                        notifDownLoadProgressChange(info);
                    }
                    //判断下载是否成功，文件长度等于服务器记录的文件大小，判断为成功
                    if (file.length() == info.size) {
                        info.currentStates = STATE_SUCCESS;
                        notifDownLoadStateChange(info);
                        //当前状态为暂停是，通知观察者更新状态
                    } else if (info.currentStates == STATE_PAUSE) {
                        notifDownLoadStateChange(info);
                        //剩余情况为下载失败
                    } else {
                        file.delete();
                        info.currentStates = STATE_FAIL;
                        info.currentPos = 0;
                        notifDownLoadStateChange(info);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    IOUtils.close(is);
                    IOUtils.close(fos);
                }
            } else {
                //下载失败
                file.delete();
                info.currentStates = STATE_FAIL;
                info.currentPos = 0;
                notifDownLoadStateChange(info);
            }
            //下载完成或者失败，都从集合中移除下载任务
            mDownLoadTaskMap.remove(info.id);

        }
    }

    /**
     * 暂停下载
     *
     * @param info
     */
    public synchronized void pause(AppInfo info) {
        DownLoadInfo downLoadInfo = mDownLoadInfoMap.get(info.id);

        if (downLoadInfo != null) {
            if (downLoadInfo.currentStates == STATE_DOWNLOADING || downLoadInfo.currentStates == STATE_WAITING) {
                //将当前应用信息状态设置为暂停，通知观察者更新状态
                downLoadInfo.currentStates = STATE_PAUSE;
                notifDownLoadStateChange(downLoadInfo);

                DownLoadTask task = mDownLoadTaskMap.get(downLoadInfo.id);
                if (task != null) {
                    //移除下载任务
                    ThreadManager.getmThreadPool().cancel(task);
                }
            }
        }
    }

    /**
     * 安装已下载程序的业务逻辑
     *
     * @param info
     */
    public synchronized void install(AppInfo info) {
        DownLoadInfo downLoadInfo = mDownLoadInfoMap.get(info.id);
        if (downLoadInfo != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + downLoadInfo.path), "application/vnd.android.package-archive");
            UIUtils.getContext().startActivity(intent);
        }
    }

    public static DownLoadManager mDm = new DownLoadManager();


    /**
     * 声明一个DownLoadObserver的接口
     */
    public interface DownLoadObserver {

        public void onDownLoadStateChange(DownLoadInfo downLoadInfo);

        public void onDownLoadProgressChange(DownLoadInfo downLoadInfo);

    }

    public DownLoadInfo getDownLoadInfo(AppInfo info) {
        return mDownLoadInfoMap.get(info.id);

    }
}
