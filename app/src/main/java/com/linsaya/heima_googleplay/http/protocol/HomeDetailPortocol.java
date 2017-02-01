package com.linsaya.heima_googleplay.http.protocol;

import com.linsaya.heima_googleplay.UI.hodler.HomeHeaderHolder;
import com.linsaya.heima_googleplay.domain.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/1.
 */

public class HomeDetailPortocol extends BaseProtocol<AppInfo> {

    private String packagename;

    public HomeDetailPortocol(String packagename) {
        this.packagename = packagename;
    }

    @Override
    public AppInfo processData(String json) {

        try {
            JSONObject jo = new JSONObject(json);
            AppInfo appInfo = new AppInfo();

            appInfo.name = jo.getString("name");
            appInfo.id = jo.getString("id");
            appInfo.packageName = jo.getString("packageName");
            appInfo.iconUrl = jo.getString("iconUrl");
            appInfo.downloadUrl = jo.getString("downloadUrl");
            appInfo.des = jo.getString("des");
            appInfo.size = jo.getLong("size");
            appInfo.stars = (float) jo.getDouble("stars");
            appInfo.author = jo.getString("author");
            appInfo.date = jo.getString("date");
            appInfo.downloadNum = jo.getString("downloadNum");
            appInfo.version = jo.getString("version");

            JSONArray ja = jo.getJSONArray("safe");
            List<AppInfo.Safeinfo> safeinfos = new ArrayList<>();
            for (int j = 0; j < ja.length(); j++) {
                JSONObject jo1 = ja.getJSONObject(j);
                AppInfo.Safeinfo safeinfo = new AppInfo.Safeinfo();
                safeinfo.safeDes = jo1.getString("safeDes");
                safeinfo.safeDesUrl = jo1.getString("safeDesUrl");
                safeinfo.safeUrl = jo1.getString("safeUrl");
                safeinfos.add(safeinfo);

            }
            appInfo.safe = safeinfos;

            JSONArray ja1 = jo.getJSONArray("screen");
            List<String> screen = new ArrayList<>();
            for (int k = 0; k < ja1.length(); k++) {
                String string = ja1.getString(k);
                screen.add(string);
            }
            appInfo.screen = screen;

            return appInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getKey() {
        return "detail";
    }

    @Override
    public String getParams() {
        return "&packageName=" + packagename;
    }
}
