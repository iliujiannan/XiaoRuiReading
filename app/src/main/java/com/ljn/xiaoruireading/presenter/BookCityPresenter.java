package com.ljn.xiaoruireading.presenter;

import android.os.Handler;
import android.os.Looper;
import com.ljn.xiaoruireading.base.BasePresenter;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.model.BookCityModel;
import com.ljn.xiaoruireading.model.LoginModel;

/**
 * Created by 12390 on 2018/8/18.
 */
public class BookCityPresenter extends BasePresenter {
    public void mGetBooks(final String label){
        if (!isViewAttached()) {
            return;
        }

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                BookCityModel.mDoGetBooks(label, new ICallback<BookCityModel>() {
                    @Override
                    public void onSuccess(BookCityModel data) {
                        if(data.getStatus()==1) {
                            getView().onActionSucc(data);
                        }else{
                            onFailure(data);
                        }
                    }

                    @Override
                    public void onFailure(BookCityModel data) {
                        getView().onActionFailed(data.getMsg());
                    }
                });
            }
        });
    }
}
