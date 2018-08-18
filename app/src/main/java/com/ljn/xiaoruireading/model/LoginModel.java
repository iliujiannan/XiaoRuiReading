package com.ljn.xiaoruireading.model;

import android.os.Handler;
import com.google.gson.Gson;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;

/**
 * Created by 12390 on 2018/8/10.
 */
public class LoginModel extends BaseModel {

    private Integer userId;
    private String secretKey;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public static void mDoLogin(final Map user, final ICallback<LoginModel> callback) {
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体

        formBody.add("userPhone", (String) user.get("userPhone"));//传递键值对参数

        formBody.add("psw", (String) user.get("psw"));

        HttpUtil httpUtil = new HttpUtil();
        httpUtil.mDoPost(formBody, "login", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                LoginModel model = new LoginModel();
                model.setStatus(0);
                model.setMsg("server error");
                callback.onFailure(model);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                LoginModel loginModel = gson.fromJson(response.body().string(), LoginModel.class);
//                        System.out.println("****还没爆"+response.body().string());
                callback.onSuccess(loginModel);
            }
        });

    }
}