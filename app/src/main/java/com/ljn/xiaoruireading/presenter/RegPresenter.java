package com.ljn.xiaoruireading.presenter;

import com.ljn.xiaoruireading.base.BasePresenter;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.model.RegModel;
import com.ljn.xiaoruireading.view.abstract_views.IRegView;

import java.util.Map;

/**
 * Created by 12390 on 2018/8/10.
 */
public class RegPresenter extends BasePresenter<IRegView> {


    public void registe(Map user){
        RegModel.mDoReg(user, new ICallback<Map>() {
            @Override
            public void onSuccess(Map data) {
                getView().mOnRegSuccess(data);
            }

            @Override
            public void onFailure(Map data) {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
