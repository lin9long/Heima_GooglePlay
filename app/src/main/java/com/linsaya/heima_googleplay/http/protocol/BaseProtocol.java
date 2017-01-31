package com.linsaya.heima_googleplay.http.protocol;

import com.linsaya.heima_googleplay.http.HttpHelper;
import com.linsaya.heima_googleplay.utils.IOUtils;
import com.linsaya.heima_googleplay.utils.StringUtils;
import com.linsaya.heima_googleplay.utils.UIUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Administrator on 2017/1/28.
 */

public abstract class BaseProtocol<T> {

    public T getData(int index) {
        String result = getCache(index);
        if (StringUtils.isEmpty(result)) {

            result = getDataFromServer(index);
        }
        if (!StringUtils.isEmpty(result)) {
            System.out.println("读取换成数据啦");
            T data = processData(result);
            return data;
        }
        return null;
    }

    public abstract T processData(String json);

    //index代表分页数据
    public String getDataFromServer(int index) {
        HttpHelper.HttpResult result = HttpHelper.get(HttpHelper.URL + getKey() + "?index=" + index + getParams());
        if (result != null) {
            String string = result.getString();
            System.out.println("返回结果为：" + string);
            if (!StringUtils.isEmpty(string)) {
                System.out.println("开始设置缓存了！");
                setCache(index, string);
            }
            return string;
        }

        return null;
    }

    //写入缓存
    public void setCache(int index, String json) {
        System.out.println("设置缓存方法执行了！");
        //获取缓存文件路径
        File cacheDir = UIUtils.getContext().getCacheDir();
        //在制定路径创建文件及名称
        File cacheFile = new File(cacheDir, getKey() + "?index=" + index + getParams());
        FileWriter writer = null;
        try {
            writer = new FileWriter(cacheFile);
            //设置缓存文件的有效时间
            long deadline = System.currentTimeMillis() + 30 * 60 * 1000;
            writer.write(deadline + "\n");
            writer.write(json);
            writer.flush();
            System.out.println("设置缓存结束啦！！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
        }
    }

    public String getCache(int index) {
        File cacheDir = UIUtils.getContext().getCacheDir();
        File cacheFile = new File(cacheDir, getKey() + "?index=" + index + getParams());
        if (cacheFile.exists()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(cacheFile));
                String deadline = reader.readLine();
                long deadtime = Long.parseLong(deadline);
                if (System.currentTimeMillis() < deadtime) {
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    return sb.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(reader);
            }
        }
        return null;
    }

    //获取网络数据的文件名
    public abstract String getKey();

    public abstract String getParams();
}
