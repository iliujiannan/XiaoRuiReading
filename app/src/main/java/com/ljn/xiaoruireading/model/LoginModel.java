package com.ljn.xiaoruireading.model;

import android.os.Handler;
import com.google.gson.Gson;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.base.ICallback;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 12390 on 2018/8/10.
 */
public class LoginModel extends BaseModel{

    private Integer mUserId;
    private String mSecretKey;

    public Integer getmUserId() {
        return mUserId;
    }

    public void setmUserId(Integer mUserId) {
        this.mUserId = mUserId;
    }

    public String getmSecretKey() {
        return mSecretKey;
    }

    public void setmSecretKey(String mSecretKey) {
        this.mSecretKey = mSecretKey;
    }



    public static void mDoLogin(final Map user, final ICallback<LoginModel> callback){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
                FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体

                formBody.add("userPhone", (String) user.get("userPhone"));//传递键值对参数

                formBody.add("psw", (String) user.get("psw"));

                Request request = new Request.Builder()//创建Request 对象。
                        .url(baseUri + "login")
                        .post(formBody.build())//传递请求体
                        .build();


                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson gson = new Gson();
                        LoginModel loginModel = gson.fromJson(response.body().string(), LoginModel.class);
//                        System.out.println("****还没爆"+response.body().string());
                        callback.onSuccess(loginModel);
                    }
                });//回调方法的使用与get异步请求相同，此时略。
            }
        });
    }
}
