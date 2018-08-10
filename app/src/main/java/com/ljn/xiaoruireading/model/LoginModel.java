package com.ljn.xiaoruireading.model;

import android.os.Handler;
import com.ljn.xiaoruireading.base.ICallback;

import java.util.Map;

/**
 * Created by 12390 on 2018/8/10.
 */
public class LoginModel {
    public static void mDoLogin(final Map user, final ICallback<Integer> callback){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user.get("username").equals("15248113901")&&user.get("psw").equals("88888888")) {
                    callback.onSuccess(1);
                }else {
                    callback.onFailure(0);
                }
                callback.onComplete();
            }
        },2000);
    }
}
