package com.linsaya.heima_googleplay.http.protocol;

import com.linsaya.heima_googleplay.domain.SubjectInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/29.
 */

public class SubjectPortocol extends BaseProtocol<List<SubjectInfo>> {

    @Override
    public List<SubjectInfo> processData(String json) {

        try {
            JSONArray ja = new JSONArray(json);
            List<SubjectInfo> list = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                SubjectInfo subjectinfo = new SubjectInfo();
                JSONObject jo = ja.getJSONObject(i);
                subjectinfo.des = jo.getString("des");
                subjectinfo.url = jo.getString("url");
                list.add(subjectinfo);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getKey() {
        return "subject";
    }

    @Override
    public String getParams() {
        return "";
    }
}
