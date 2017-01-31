package com.linsaya.heima_googleplay.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/30.
 */

public class RecommendPortocol extends BaseProtocol<List<String>> {
    @Override
    public List<String> processData(String json) {
        try {
            JSONArray ja = new JSONArray(json);
            List<String> applist = new ArrayList<>();
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
        return "recommend";
    }

    @Override
    public String getParams() {
        return "";
    }
}
