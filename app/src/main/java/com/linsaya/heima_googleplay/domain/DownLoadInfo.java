package com.linsaya.heima_googleplay.domain;

import android.app.DownloadManager;
import android.os.Environment;

import com.linsaya.heima_googleplay.manager.DownLoadManager;

import java.io.File;

/**
 * Created by Administrator on 2017/2/3.
 */

public class DownLoadInfo {
    public long size;
    public String downloadUrl;
    public String name;
    public String id;
    public String packageName;

    public long currentPos;
    public int currentStates;
    public String path;

    public static final String GOOGLE_MACKET = "google_macket";
    public static final String DOWNLOAD = "download";

    public float getProgress() {
        if (size == 0) {
            return 0;
        }
        float progress = currentPos / (float) size;
        return progress;
    }

    public static DownLoadInfo copy(AppInfo info) {
        DownLoadInfo downLoadInfo = new DownLoadInfo();
        downLoadInfo.size = info.size;
        downLoadInfo.name = info.name;
        downLoadInfo.id = info.id;
        downLoadInfo.packageName = info.packageName;
        downLoadInfo.downloadUrl = info.downloadUrl;

        downLoadInfo.currentPos = 0;
        downLoadInfo.currentStates = DownLoadManager.STATE_UNDO;
        downLoadInfo.path = downLoadInfo.getPath();
        return downLoadInfo;

    }

    public String getPath() {
        StringBuffer sb = new StringBuffer();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        sb.append(path);
        sb.append(File.separator);
        sb.append(GOOGLE_MACKET);
        sb.append(File.separator);
        sb.append(DOWNLOAD);

        if (creatFilePath(sb.toString())) {
            return sb.toString() + File.separator + name + ".apk";
        }
        return null;
    }

    public boolean creatFilePath(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }
}
