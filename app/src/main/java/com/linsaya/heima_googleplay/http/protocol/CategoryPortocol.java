package com.linsaya.heima_googleplay.http.protocol;

import com.linsaya.heima_googleplay.domain.CategoryInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/31.
 */

public class CategoryPortocol extends BaseProtocol<List<CategoryInfo>> {
    @Override
    public List<CategoryInfo> processData(String json) {
        List<CategoryInfo> list = new ArrayList<>();
        try {
            JSONArray ja = new JSONArray(json);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                //先对JSONObject进行非空判断
                if (jo.has("title")) {
                    CategoryInfo info = new CategoryInfo();
                    info.title = jo.getString("title");
                    info.istitle = true;
                    list.add(info);
                }
                //再次对JSONObject进行非空判断

                    JSONArray ja2 = jo.getJSONArray("infos");

                    for (int j = 0; j < ja2.length(); j++) {
                        JSONObject jo2 = ja2.getJSONObject(j);
                        CategoryInfo info2 = new CategoryInfo();
                        info2.name1 = jo2.getString("name1");
                        info2.name2 = jo2.getString("name2");
                        info2.name3 = jo2.getString("name3");
                        info2.url1 = jo2.getString("url1");
                        info2.url2 = jo2.getString("url2");
                        info2.url3 = jo2.getString("url3");
                        info2.istitle = false;
                        list.add(info2);

                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getKey() {
        return "category";
    }

    @Override
    public String getParams() {
        return "";
    }
}
