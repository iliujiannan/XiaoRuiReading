package com.ljn.xiaoruireading.presenter;

import com.ljn.xiaoruireading.base.BasePresenter;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.model.LoginModel;
import com.ljn.xiaoruireading.view.abstract_views.ILoginView;

import java.util.Map;

/**
 * Created by 12390 on 2018/8/10.
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    public void login(Map user) {
        if (!isViewAttached()) {
            return;
        }

        getView().showLoading();

        LoginModel.mDoLogin(user, new ICallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                getView().mOnLoginSuccess();
            }

            @Override
            public void onFailure(Integer data) {
                getView().mOnLoginFailure();
            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {
                getView().hideLoading();
            }
        });
    }
}
