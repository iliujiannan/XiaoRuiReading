package com.ljn.xiaoruireading.presenter;

import android.os.Handler;
import com.ljn.xiaoruireading.base.BaseModel;
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
                LoginModel.mDoLogin(user, new ICallback<BaseModel>() {

                    @Override
                    public void onSuccess(BaseModel data) {

                        if(data.getStatus()==1) {
                            getView().onActionSucc(data);
                        }else{
                            getView().onActionFailed(data.getMsg());
                        }
//                        getView().hideLoading();
                    }

                    @Override
                    public void onFailure(BaseModel data) {
//                        getView().hideLoading();
                    }

                });
            }
        });

    }
}
