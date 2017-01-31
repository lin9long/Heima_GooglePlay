package com.linsaya.heima_googleplay.http.protocol;

import com.linsaya.heima_googleplay.domain.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/29.
 */

public class AppPortocol extends BaseProtocol<List<AppInfo>> {
    @Override
    public List<AppInfo> processData(String json) {
        List<AppInfo> appInfoList = new ArrayList<>();


        try {
            JSONArray ja = new JSONArray(json);
            for (int i = 0; i < ja.length(); i++) {
                AppInfo appInfo = new AppInfo();
                JSONObject jo = ja.getJSONObject(i);
                appInfo.name = jo.getString("name");
                appInfo.id = jo.getString("id");
                appInfo.packageName = jo.getString("packageName");
                appInfo.iconUrl = jo.getString("iconUrl");
                appInfo.downloadUrl = jo.getString("downloadUrl");
                appInfo.des = jo.getString("des");
                appInfo.size = jo.getLong("size");
                appInfo.stars = (float) jo.getDouble("stars");
                appInfoList.add(appInfo);
            }
            return appInfoList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getKey() {
        return "app";
    }

    @Override
    public String getParams() {
        return "";
    }
}
