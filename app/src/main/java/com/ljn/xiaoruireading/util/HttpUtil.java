package com.ljn.xiaoruireading.util;

import com.google.gson.Gson;
import com.ljn.xiaoruireading.model.LoginModel;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by 12390 on 2018/8/17.
 */
public class HttpUtil{
    public static String baseUri = "http://10.25.42.19:8080/";
    private OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
    public void mDoPost(List<String> keys, List<String> values, String url, Callback callback) {
        FormBody.Builder form = new FormBody.Builder();
        for (int i = 0; i < keys.size(); i++) {
            form.add(keys.get(i), values.get(i));
        }

        Request request = new Request.Builder()//创建Request 对象。
                .url(baseUri+url)
                .post(form.build())//传递请求体
                .build();
        client.newCall(request).enqueue(callback);
    }
    public void mDoPost(FormBody.Builder form, String url, Callback callback) {
        Request request = new Request.Builder()//创建Request 对象。
                .url(baseUri+url)
                .post(form.build())//传递请求体
                .build();
        client.newCall(request).enqueue(callback);
    }

}
