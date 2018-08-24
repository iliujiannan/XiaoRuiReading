package com.ljn.xiaoruireading.base;

import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by 12390 on 2018/8/18.
 */
public class BaseModelCallBack implements Callback {

    private ICallback callback;

    public BaseModelCallBack(ICallback _callback) {
        callback = _callback;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        BaseModel model = new BaseModel();
        model.setStatus(0);
        model.setMsg("网络异常，请稍后重试");
        callback.onFailure(model);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
//                System.out.println("***************调用前7");
//                System.out.println(response.body().string());
        System.out.println(response.code());
        if(response.code()==200) {
            String s = response.body().string();
            System.out.println(s);
            BaseModel baseModel = new Gson().fromJson(s, BaseModel.class);
            callback.onSuccess(baseModel);
        }else{
            onFailure(call, new IOException());
        }
    }
}

