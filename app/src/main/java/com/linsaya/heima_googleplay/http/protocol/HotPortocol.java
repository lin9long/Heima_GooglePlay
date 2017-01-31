package com.linsaya.heima_googleplay.http.protocol;

import com.linsaya.heima_googleplay.UI.view.fly.StellarMap;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/30.
 */

public class HotPortocol extends BaseProtocol<List<String>> {
    @Override
    public List<String> processData(String json) {
        try {
            List<String> applist = new ArrayList<>();
            JSONArray ja = new JSONArray(json);
            for (int i = 0; i < ja.length(); i++) {
                String appName = ja.getString(i);
                applist.add(appName);
            }
            return applist;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getKey() {
        return "hot";
    }

    @Override
    public String getParams() {
        return "";
    }
}
