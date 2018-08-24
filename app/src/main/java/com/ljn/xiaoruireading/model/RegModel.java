package com.ljn.xiaoruireading.model;

import com.google.gson.Gson;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 12390 on 2018/8/10.
 */
public class RegModel extends BaseModel{
    public static void mDoReg(final List<String> keys, final List<String> values, final ICallback<BaseModel> callback){
        HttpUtil httpUtil = new HttpUtil();

        httpUtil.mDoPost(keys, values, "register", new Callback() {
            RegModel model;
            @Override
            public void onFailure(Call call, IOException e) {
                model.setStatus(0);
                model.setMsg("server error");
                callback.onFailure(model);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                RegModel regModel = gson.fromJson(response.body().string(), RegModel.class);
//                        System.out.println("****还没爆"+response.body().string());
                callback.onSuccess(regModel);
            }
        });


    }

    public static void mDoGetCheckCode(final String phone, final ICallback<BaseModel> callback){
        HttpUtil httpUtil = new HttpUtil();

        FormBody.Builder form = new FormBody.Builder();
        form.add("userPhone", phone);
        httpUtil.mDoPost(form, "get_check_code", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                RegModel model = new RegModel();
                model.setStatus(0);
                model.setMsg("server error");
                callback.onFailure(model);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                RegModel regModel = gson.fromJson(response.body().string(), RegModel.class);
//                        System.out.println("****还没爆"+response.body().string());
                callback.onSuccess(regModel);
            }
        });

    }

}
