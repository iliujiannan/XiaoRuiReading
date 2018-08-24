package com.ljn.xiaoruireading.util;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by 12390 on 2018/8/17.
 */
public class HttpUtil{
    public static String baseUri = "http://10.25.42.19:8080/";
    private OkHttpClient client;//创建OkHttpClient对象。

    public HttpUtil(){
        client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }
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
