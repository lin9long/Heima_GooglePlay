package com.linsaya.heima_googleplay.domain;

import java.util.List;

/**
 * Created by Administrator on 2017/1/29.
 */

public class AppInfo {
    public String name;
    public String id;
    public String packageName;
    public String iconUrl;
    public float stars;
    public long size;
    public String downloadUrl;
    public String des;

    //添加homedetail的字段，共用一个javabean
    public String author;
    public String date;
    public String downloadNum;
    public String version;
    public List<Safeinfo> safe;
    public List<String> screen;

    public static class Safeinfo {
        public String safeDes;
        public String safeDesUrl;
        public String safeUrl;
    }

}
