package com.ljn.xiaoruireading.model;


import android.os.Handler;
import com.ljn.xiaoruireading.base.ICallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 12390 on 2018/8/14.
 */
public class BookShelfModel {
    public static void mDoGetBookShelf(final Map user, final ICallback<Map> callback){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(new HashMap());
            }
        });
    }
}
