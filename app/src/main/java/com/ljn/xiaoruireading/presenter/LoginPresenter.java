package com.ljn.xiaoruireading.presenter;

import com.ljn.xiaoruireading.base.BasePresenter;
import com.ljn.xiaoruireading.base.IBaseView;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.model.LoginModel;

import java.util.Map;

/**
 * Created by 12390 on 2018/8/10.
 */
public class LoginPresenter extends BasePresenter<IBaseView> {

    public void login(Map user) {
        if (!isViewAttached()) {
            return;
        }

        getView().showLoading();

        LoginModel.mDoLogin(user, new ICallback<LoginModel>() {

            @Override
            public void onSuccess(LoginModel data) {

                getView().onActionSucc(data);
                getView().hideLoading();
            }

            @Override
            public void onFailure(LoginModel data) {
                getView().hideLoading();
            }

        });
    }
}
