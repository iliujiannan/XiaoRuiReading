package com.ljn.xiaoruireading.presenter;

import android.os.Handler;
import android.os.Looper;
import com.ljn.xiaoruireading.base.BasePresenter;
import com.ljn.xiaoruireading.base.IBaseView;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.model.LoginModel;

import java.util.Map;

/**
 * Created by 12390 on 2018/8/10.
 */
public class LoginPresenter extends BasePresenter<IBaseView> {

    public void login(final Map user) {
        if (!isViewAttached()) {
            return;
        }

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                LoginModel.mDoLogin(user, new ICallback<LoginModel>() {

                    @Override
                    public void onSuccess(LoginModel data) {

                        if(data.getStatus()==1) {
                            System.out.println("*********1sec:"+data.getSecretKey());
                            getView().onActionSucc(data);
                        }else{
                            Looper.prepare();
                            getView().onActionFailed(data);
                            Looper.loop();
                        }
//                        getView().hideLoading();
                    }

                    @Override
                    public void onFailure(LoginModel data) {
//                        getView().hideLoading();
                    }

                });
            }
        });

    }
}
